package com.xlogisticzz.woodGenerator.network;

import com.xlogisticzz.woodGenerator.tileEntities.TileWoodGenerator;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author xLoGisTicZz
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class PacketGuiWoodGenerator extends PacketWoodGenerator {

    private int meta;
    private int x;
    private int y;
    private int z;

    public PacketGuiWoodGenerator(int meta, int x, int y, int z) {
        this.meta = meta;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PacketGuiWoodGenerator() {
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buf) {
        buf.writeByte(meta);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buf) {
        meta = buf.readByte();
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }

    @Override
    public void handleClient(EntityPlayer player) {

    }

    @Override
    public void handleServer(EntityPlayer player) {
        player.worldObj.setBlockMetadataWithNotify(x, y, z, meta, 2);
        if (player.worldObj.getTileEntity(x, y, z) != null) {
            ((TileWoodGenerator) (player.worldObj.getTileEntity(x, y, z))).updateWoodType();
        }
    }
}
