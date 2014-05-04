package com.xlogisticzz.woodGenerator.network;
/*
* @author xLoGisTicZz
* @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
*/

import com.xlogisticzz.woodGenerator.lib.Constants;
import com.xlogisticzz.woodGenerator.utils.LogHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;

import java.util.*;

@ChannelHandler.Sharable
public class PacketPipeline extends MessageToMessageCodec<FMLProxyPacket, PacketWoodGenerator> {

    private static EnumMap<Side, FMLEmbeddedChannel> channels;
    private LinkedList<Class<? extends PacketWoodGenerator>> packets = new LinkedList<Class<? extends PacketWoodGenerator>>();
    private boolean isPostInit = false;

    /**
     * Send this message to the server.
     *
     * @param message The message to send
     */
    public static void sendToServer(PacketWoodGenerator message) {
        channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        channels.get(Side.CLIENT).writeAndFlush(message);
    }

    /**
     * Register your packet with the pipeline. Discriminators are automatically set.
     *
     * @param clas the class to register
     * @return whether registration was successful. Failure may occur if 256 packets have been registered or if the registry already contains this packet
     */
    public boolean registerPacket(Class<? extends PacketWoodGenerator> clas) {
        if (packets.size() > 256) {
            LogHelper.error("Learning modding is attempting to register more packets than the limit. Some things may not work. " + clas.getCanonicalName());
            return false;
        }

        if (packets.contains(clas)) {
            LogHelper.warn("Learning modding is attempting to register a packet twice. This should not cause any problems but report it to the author never the less. " + clas.getCanonicalName());
            return false;
        }

        if (isPostInit) {
            LogHelper.error("Learning Modding is attempting to register packets in the pre innit phase. Things will not work and report this to the mod author. " + clas.getCanonicalName());
            return false;
        }

        packets.add(clas);
        return true;
    }

    public void registerPackets() {
        registerPacket(PacketGuiWoodGenerator.class);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, PacketWoodGenerator msg, List<Object> out) throws Exception {
        ByteBuf buf = Unpooled.buffer();
        Class<? extends PacketWoodGenerator> clas = msg.getClass();
        if (!packets.contains(msg.getClass())) {
            throw new NullPointerException("No packet registered for " + msg.getClass().getCanonicalName());
        }

        byte discriminator = (byte) packets.indexOf(clas);
        buf.writeByte(discriminator);
        msg.encodeInto(ctx, buf);
        FMLProxyPacket proxyPacket = new FMLProxyPacket(buf.copy(), ctx.channel().attr(NetworkRegistry.FML_CHANNEL).get());
        out.add(proxyPacket);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, FMLProxyPacket msg, List<Object> out) throws Exception {
        ByteBuf payload = msg.payload();
        byte discriminator = payload.readByte();
        Class<? extends PacketWoodGenerator> clas = packets.get(discriminator);
        if (clas == null) {
            throw new NullPointerException("No packet registered for discriminator: " + discriminator);
        }

        PacketWoodGenerator pkt = clas.newInstance();
        pkt.decodeInto(ctx, payload.slice());

        EntityPlayer player;
        switch (FMLCommonHandler.instance().getEffectiveSide()) {
            case CLIENT:
                player = getClientPlayer();
                pkt.handleClient(player);
                break;

            case SERVER:
                INetHandler netHandler = ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
                player = ((NetHandlerPlayServer) netHandler).playerEntity;
                pkt.handleServer(player);
                break;

            default:
        }

        out.add(pkt);
    }

    @SideOnly(Side.CLIENT)
    private EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    public void initialize() {
        channels = NetworkRegistry.INSTANCE.newChannel(Constants.MODID, this);
    }

    public void postInitialise() {
        if (isPostInit) {
            return;
        }

        isPostInit = true;
        Collections.sort(packets, new Comparator<Class<? extends PacketWoodGenerator>>() {

            @Override
            public int compare(Class<? extends PacketWoodGenerator> clazz1, Class<? extends PacketWoodGenerator> clazz2) {
                int com = String.CASE_INSENSITIVE_ORDER.compare(clazz1.getCanonicalName(), clazz2.getCanonicalName());
                if (com == 0) {
                    com = clazz1.getCanonicalName().compareTo(clazz2.getCanonicalName());
                }
                return com;
            }
        });
    }

    /**
     * Send this message to everyone.
     *
     * @param message The message to send
     */

    public void sendToAll(PacketWoodGenerator message) {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
        channels.get(Side.SERVER).writeAndFlush(message);
    }

    /**
     * Send this message to the specified player.
     *
     * @param message The message to send
     * @param player  The player to send it to
     */
    public void sendTo(PacketWoodGenerator message, EntityPlayerMP player) {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
        channels.get(Side.SERVER).writeAndFlush(message);
    }

    /**
     * Send this message to everyone within a certain range of a point.
     *
     * @param message The message to send
     * @param point   The {@link cpw.mods.fml.common.network.NetworkRegistry.TargetPoint} around which to send
     */
    public void sendToAllAround(PacketWoodGenerator message, NetworkRegistry.TargetPoint point) {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
        channels.get(Side.SERVER).writeAndFlush(message);
    }

    /**
     * Send this message to everyone within the supplied dimension.
     *
     * @param message     The message to send
     * @param dimensionId The dimension id to target
     */
    public void sendToDimension(PacketWoodGenerator message, int dimensionId) {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimensionId);
        channels.get(Side.SERVER).writeAndFlush(message);
    }

}
