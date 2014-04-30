package com.xlogisticzz.woodGenerator.blocks;

import com.xlogisticzz.woodGenerator.lib.Constants;
import com.xlogisticzz.woodGenerator.tileEntities.TileWoodGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

/**
 * @author xLoGisTicZz
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class ModBlocks {

    public static Block blockWoodGenerator;

    public static void init() {

        blockWoodGenerator = new BlockWoodGenerator();

        register();
    }

    public static void register() {

        GameRegistry.registerBlock(blockWoodGenerator, Constants.WOODGENERATOR);
        GameRegistry.registerTileEntity(TileWoodGenerator.class, "tile." + Constants.WOODGENERATOR);

    }
}
