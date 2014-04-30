package com.xlogisticzz.woodGenerator.client.interfaces.slots;


import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author xLoGisTicZz
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class SlotBoneMeal extends Slot {

    public SlotBoneMeal(IInventory par1IInventory, int par2, int par3, int par4) {
        super(par1IInventory, par2, par3, par4);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        if (itemStack.getItem() == Items.dye && itemStack.getItemDamage() == 15) {
            return true;
        } else {
            return false;
        }
    }
}
