package com.xlogisticzz.woodGenerator.tileEntities;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

/**
 * @author xLoGisTicZz
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class TileWoodGenerator extends TileEntity implements IInventory {

    private ItemStack[] items;
    private String customName;
    private int delay;
    private int timer;
    private int timerMax;
    private Block currentBlock;
    private int currentMeta;

    public TileWoodGenerator() {
        items = new ItemStack[10];
        currentBlock = Blocks.log;
        currentMeta = 0;
        timerMax = 48;
        timer = 0;

    }

    public int getType() {
        return getBlockMetadata();
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            setTimerMax();
            updateWoodType();
            if (++delay == 5) {
                if (++timer >= timerMax) {
                    generateWood();
                    timer = 0;
                }
                delay = 0;
            }
        }
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getTimerMax() {
        return timerMax;
    }

    public void setTimerMax(int timerMax) {
        this.timerMax = timerMax;
    }

    private void generateWood() {
        updateWoodType();
        for (int i = 0; i < items.length - 1; i++) {
            if (canInsert(items[i], currentBlock, currentMeta)) {
                if (items[i] != null) {
                    items[i].stackSize++;
                    timerMax = 48;
                    return;
                }
                items[i] = new ItemStack(currentBlock, 1, currentMeta);
                timerMax = 48;
                return;
            }
        }
    }

    public void updateWoodType() {
        int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        if (meta > 3) {
            currentBlock = Blocks.log2;
            currentMeta = meta - 4;
        } else {
            currentBlock = Blocks.log;
            currentMeta = meta;
        }
    }

    public void setTimerMax() {
        if (items[9] != null && timerMax != 6) {
            if (items[9].getItem() == Items.dye || items[9].getItemDamage() == 15) {
                if (items[9].stackSize < 2) {
                    items[9] = null;
                    timerMax = 6;
                    return;
                } else {
                    items[9].stackSize--;
                    timerMax = 6;
                }
            } else {
                timerMax = 48;
            }
        }
    }

    private boolean canInsert(ItemStack item, Block block, int meta) {
        if (item == null) {
            return true;
        }
        if (item.getItem() == Item.getItemFromBlock(block) && item.getItemDamage() == meta && item.stackSize < item.getMaxStackSize()) {
            return true;
        }
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        NBTTagList items = compound.getTagList("Items", 10);

        for (int i = 0; i < items.tagCount(); i++) {
            NBTTagCompound item = items.getCompoundTagAt(i);
            int slot = item.getByte("Slot");

            if (slot >= 0 && slot < getSizeInventory()) {
                setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
            }
        }

        if (compound.hasKey("CustomName", 8)) {
            customName = compound.getString("CustomName");
        }

        timer = compound.getByte("Timer");
        timerMax = compound.getByte("TimerMax");
        currentBlock = Block.getBlockById(compound.getInteger("CurrentBlock"));
        currentMeta = compound.getByte("CurrentMeta");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        NBTTagList items = new NBTTagList();

        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack stack = getStackInSlot(i);
            if (stack != null) {
                NBTTagCompound item = new NBTTagCompound();
                item.setByte("Slot", (byte) i);
                stack.writeToNBT(item);
                items.appendTag(item);
            }
        }
        compound.setTag("Items", items);
        compound.setByte("Timer", (byte) timer);
        compound.setByte("TimerMax", (byte) timerMax);
        compound.setInteger("CurrentBlock", Block.getIdFromBlock(currentBlock));
        compound.setByte("CurrentMeta", (byte) currentMeta);


        if (hasCustomInventoryName()) {
            compound.setString("CustomName", this.customName);
        }

    }

    @Override
    public int getSizeInventory() {
        return items.length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return items[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        ItemStack stack = getStackInSlot(i);
        if (stack != null) {
            if (stack.stackSize <= j) {
                setInventorySlotContents(i, null);
            } else {
                stack = stack.splitStack(i);
                markDirty();
            }
        }
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        ItemStack stack = getStackInSlot(i);
        setInventorySlotContents(i, null);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack) {
        items[i] = itemStack;
        if (itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
            itemStack.stackSize = getInventoryStackLimit();
        }
        markDirty();
    }

    @Override
    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.customName : "container.cakeStorage";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return this.customName != null && this.customName.length() > 0;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        if (i == 0) {
            return itemStack.getItem() == Items.dye && itemStack.getItemDamage() == 15;
        } else {
            return Block.getBlockFromItem(itemStack.getItem()) == Blocks.log || Block.getBlockFromItem(itemStack.getItem()) == Blocks.log2;
        }
    }
}
