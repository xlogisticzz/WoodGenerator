package com.xlogisticzz.woodGenerator.client.interfaces.gui;

import com.xlogisticzz.woodGenerator.utils.gui.GuiUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

/**
 * @author xLoGisTicZz
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public abstract class GuiTabBase extends GuiTab {

    public EntityItem sapling;
    public EntityFallingBlock block;
    public float rollSapling;
    public float yawSapling;
    public boolean rollDownSapling;
    public float rollBlock = 25;
    public float yawBlock;
    public boolean rollDownBlock;
    public Block currentBlock;
    public int currentMeta;
    public int id;

    public GuiTabBase(String name, int id, World world) {
        super(name, id);
        sapling = new EntityItem(world, 0, 0, 0, new ItemStack(Blocks.sapling, 1, id));
        if (id > 3) {
            currentBlock = Blocks.log2;
            currentMeta = id - 4;
        } else {
            currentBlock = Blocks.log;
            currentMeta = id;
        }
        block = new EntityFallingBlock(world, 0, 0, 0, currentBlock, currentMeta);
        this.id = id;
    }

    public GuiTabBase(String name, int id, int x, int y, World world) {
        super(name, id, x, y);
        sapling = new EntityItem(world, 0, 0, 0, new ItemStack(Blocks.sapling, 1, id));
        if (id > 3) {
            currentBlock = Blocks.log2;
            currentMeta = id - 4;
        } else {
            currentBlock = Blocks.log;
            currentMeta = id;
        }
        block = new EntityFallingBlock(world, 0, 0, 0, currentBlock, currentMeta);
        this.id = id;
    }

    @Override
    public void drawBackground(GuiWoodGenerator gui, int x, int y) {
        if (Minecraft.isFancyGraphicsEnabled()) {
            sapling.lifespan = 6000;
            sapling.posX = 0;
            sapling.posY = 0;
            sapling.posZ = 0;
            sapling.hoverStart = 0;

            GL11.glPushMatrix();

            GL11.glTranslatef(gui.getLeft() + 90, gui.getTop() + 120, 100);

            float scaleSapling = 80F;
            GL11.glScalef(-scaleSapling, scaleSapling, scaleSapling);

            RenderHelper.enableStandardItemLighting();

            GL11.glRotatef(180, 0, 0, 1);
            GL11.glRotatef(rollSapling, 1, 0, 0);
            GL11.glRotatef(yawSapling, 0, 1, 0);

            RenderManager.instance.renderEntityWithPosYaw(sapling, 0, 0, 0, 0, 0);

            RenderHelper.disableStandardItemLighting();
            GL11.glPopMatrix();

            yawSapling += 0.5F;
            if (rollDownSapling) {
                rollSapling -= 0.05F;
                if (rollSapling < -5) {
                    rollDownSapling = false;
                    rollSapling = -5;
                }
            } else {
                rollSapling += 0.05F;
                if (rollSapling > 25) {
                    rollDownSapling = true;
                    rollSapling = 25;
                }
            }
        } else {
            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            gui.drawTexturedModelRectFromIcon(gui.getLeft() + 70, gui.getTop() + 90, Blocks.sapling.getIcon(1, id), 32, 32);
        }
        GL11.glPushMatrix();

        GL11.glTranslatef(gui.getLeft() + 136, gui.getTop() + 106, 100);

        float scaleBlock = 30F;
        GL11.glScalef(-scaleBlock, scaleBlock, scaleBlock);

        RenderHelper.enableStandardItemLighting();

        GL11.glRotatef(180, 0, 0, 1);
        GL11.glRotatef(rollBlock, 1, 0, 0);
        GL11.glRotatef(yawBlock, 0, 1, 0);

        RenderManager.instance.renderEntityWithPosYaw(block, 0, 0, 0, 0, 0);

        RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();

        yawBlock += 0.5F;
        if (rollDownBlock) {
            rollBlock -= 0.05F;
            if (rollBlock < -5) {
                rollDownBlock = false;
                rollBlock = -5;
            }
        } else {
            rollBlock += 0.05F;
            if (rollBlock > 25) {
                rollDownBlock = true;
                rollBlock = 25;
            }
        }

    }


    @Override
    public void drawForeground(GuiWoodGenerator gui, int x, int y){
        if(!Minecraft.isFancyGraphicsEnabled()){
            GuiUtils.drawCenteredString(gui.getFontRenderer(), "Turn on fancy graphics", 112, 74, 0x404040);
        }
    }
}
