package com.xlogisticzz.woodGenerator;

import com.xlogisticzz.woodGenerator.blocks.ModBlocks;
import com.xlogisticzz.woodGenerator.handler.GuiHandler;
import com.xlogisticzz.woodGenerator.items.ModItems;
import com.xlogisticzz.woodGenerator.lib.Constants;
import com.xlogisticzz.woodGenerator.network.PacketPipeline;
import com.xlogisticzz.woodGenerator.proxies.CommonProxy;
import com.xlogisticzz.woodGenerator.utils.LogHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

/**
 * @author xLoGisTicZz
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

@Mod(modid = Constants.MODID, name = Constants.MODID, version = Constants.VERSION)
public class WoodGenerator {

    PacketPipeline packetPipeline = new PacketPipeline();

    @Mod.Instance(Constants.MODID)
    public static WoodGenerator instance;

    @SidedProxy(clientSide = Constants.CLIENT, serverSide = Constants.COMMON)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void PreInt(FMLPreInitializationEvent event) {

        ModItems.init();
        ModBlocks.init();

        proxy.initSounds();
        proxy.initRenders();

    }

    /* Initialisation */
    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {

        packetPipeline.initialize();
        packetPipeline.registerPackets();

        new GuiHandler();

    }

    /* PostInitialization */
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        packetPipeline.postInitialise();
        LogHelper.info("Wood Generator loaded");
    }

    /* When the server starts */
    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {

    }

}
