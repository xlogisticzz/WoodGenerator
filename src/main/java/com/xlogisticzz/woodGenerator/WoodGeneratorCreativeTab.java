package com.xlogisticzz.woodGenerator;

import com.xlogisticzz.woodGenerator.blocks.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * @author xLoGisTicZz
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class WoodGeneratorCreativeTab {

    public static CreativeTabs tabWoodGenerator = new CreativeTabs("tabWoodGenerator") {

        @Override
        public ItemStack getIconItemStack() {

            return new ItemStack(ModBlocks.blockWoodGenerator, 1, 0);
        }

        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(ModBlocks.blockWoodGenerator);
        }

    };
}
