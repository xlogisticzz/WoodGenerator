package com.xlogisticzz.woodGenerator.client.interfaces.container;

import com.xlogisticzz.woodGenerator.client.interfaces.slots.SlotBoneMeal;
import com.xlogisticzz.woodGenerator.client.interfaces.slots.SlotWood;
import com.xlogisticzz.woodGenerator.tileEntities.TileWoodGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author xLoGisTicZz
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class ContainerWoodGenerator extends Container {

    public TileWoodGenerator woodGenerator;

    public ContainerWoodGenerator(InventoryPlayer inventoryPlayer, TileWoodGenerator woodGenerator) {
        this.woodGenerator = woodGenerator;

        // Player Hotbar
        for (int x = 0; x < 9; x++) {
            addSlotToContainer(new Slot(inventoryPlayer, x, 8 + 18 * x, 194));
        }

        // Player Inventory
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                addSlotToContainer(new Slot(inventoryPlayer, x + y * 9 + 9, 8 + 18 * x, 136 + y * 18));
            }
        }

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                addSlotToContainer(new SlotWood(woodGenerator, x + y * 3, 115 + 18 * x, 17 + y * 18));
            }
        }

        addSlotToContainer(new SlotBoneMeal(woodGenerator, 9, 26, 35));

    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return woodGenerator.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        return null;
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, woodGenerator.getBlockMetadata());
    }

    @Override
    public void updateProgressBar(int id, int data) {
        if (id == 0) {
            woodGenerator.getWorldObj().setBlockMetadataWithNotify(woodGenerator.xCoord, woodGenerator.yCoord, woodGenerator.zCoord, data, 2);
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (Object player : crafters) {
            ((ICrafting) player).sendProgressBarUpdate(this, 0, woodGenerator.getBlockMetadata());
        }
    }
}
