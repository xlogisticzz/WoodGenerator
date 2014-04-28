package com.xlogisticzz.woodGenerator.client.interfaces.container;

import com.xlogisticzz.woodGenerator.tileEntities.TileWoodGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;

/**
 * @author xLoGisTicZz
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class ContainerWoodGenerator extends Container {

    TileWoodGenerator woodGenerator;

    public ContainerWoodGenerator(InventoryPlayer inventoryPlayer, TileWoodGenerator woodGenerator) {
        this.woodGenerator = woodGenerator;

    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return woodGenerator.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        return super.transferStackInSlot(par1EntityPlayer, par2);
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
    }

    @Override
    public void updateProgressBar(int par1, int par2) {
        super.updateProgressBar(par1, par2);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    }
}
