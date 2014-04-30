package com.xlogisticzz.woodGenerator.handler;

import com.xlogisticzz.woodGenerator.WoodGenerator;
import com.xlogisticzz.woodGenerator.client.interfaces.container.ContainerWoodGenerator;
import com.xlogisticzz.woodGenerator.client.interfaces.gui.GuiWoodGenerator;
import com.xlogisticzz.woodGenerator.tileEntities.TileWoodGenerator;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author xLoGisTicZz
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class GuiHandler implements IGuiHandler {

    public GuiHandler() {
        NetworkRegistry.INSTANCE.registerGuiHandler(WoodGenerator.instance, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case 0:
                TileEntity tileEntity = world.getTileEntity(x, y, z);
                if (tileEntity != null && tileEntity instanceof TileWoodGenerator) {
                    return new ContainerWoodGenerator(player.inventory, (TileWoodGenerator) tileEntity);
                }
                break;
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case 0:
                TileEntity tileEntity = world.getTileEntity(x, y, z);
                if (tileEntity != null && tileEntity instanceof TileWoodGenerator) {
                    return new GuiWoodGenerator(player.inventory, (TileWoodGenerator) tileEntity);
                }
                break;
        }
        return null;
    }
}
