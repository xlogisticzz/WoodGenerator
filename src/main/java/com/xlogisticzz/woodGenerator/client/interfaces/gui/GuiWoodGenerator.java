package com.xlogisticzz.woodGenerator.client.interfaces.gui;

import com.xlogisticzz.woodGenerator.client.interfaces.container.ContainerWoodGenerator;
import com.xlogisticzz.woodGenerator.lib.Constants;
import com.xlogisticzz.woodGenerator.tileEntities.TileWoodGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * @author xLoGisTicZz
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class GuiWoodGenerator extends GuiContainer {

    private static final ResourceLocation texture = new ResourceLocation(Constants.MODID.toLowerCase(), "textures/gui/woodGenerator.png");
    private TileWoodGenerator woodGenerator;

    public GuiWoodGenerator(InventoryPlayer inventoryPlayer, TileWoodGenerator woodGenerator) {
        super(new ContainerWoodGenerator(inventoryPlayer, woodGenerator));
        this.woodGenerator = woodGenerator;

        xSize = 176;
        ySize = 218;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1, 1, 1, 1);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        super.actionPerformed(p_146284_1_);
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
    }

    @Override
    protected void mouseClickMove(int p_146273_1_, int p_146273_2_, int p_146273_3_, long p_146273_4_) {
        super.mouseClickMove(p_146273_1_, p_146273_2_, p_146273_3_, p_146273_4_);
    }

    @Override
    protected void mouseMovedOrUp(int p_146286_1_, int p_146286_2_, int p_146286_3_) {
        super.mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
    }
}
