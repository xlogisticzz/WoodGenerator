package com.xlogisticzz.woodGenerator.client.interfaces.slots;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author xLoGisTicZz
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class SlotWood extends Slot {

    public SlotWood(IInventory par1IInventory, int par2, int par3, int par4) {
        super(par1IInventory, par2, par3, par4);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        if (Block.getBlockFromItem(itemStack.getItem()) == Blocks.log || Block.getBlockFromItem(itemStack.getItem()) == Blocks.log2) {
            return true;
        } else {
            return false;
        }
    }
}
