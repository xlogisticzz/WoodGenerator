package com.xlogisticzz.woodGenerator.client.interfaces.gui;

import com.xlogisticzz.woodGenerator.client.interfaces.container.ContainerWoodGenerator;
import com.xlogisticzz.woodGenerator.lib.Constants;
import com.xlogisticzz.woodGenerator.network.PacketGuiWoodGenerator;
import com.xlogisticzz.woodGenerator.network.PacketPipeline;
import com.xlogisticzz.woodGenerator.tileEntities.TileWoodGenerator;
import com.xlogisticzz.woodGenerator.utils.StringUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * @author xLoGisTicZz
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class GuiWoodGenerator extends GuiContainer {

    private static final ResourceLocation texture = new ResourceLocation(Constants.MODID.toLowerCase(), "textures/gui/woodGenerator.png");
    private final GuiTabBase[] tabs;
    private TileWoodGenerator woodGenerator;
    private GuiTabBase activeTab;

    public GuiWoodGenerator(InventoryPlayer inventoryPlayer, TileWoodGenerator woodGenerator) {
        super(new ContainerWoodGenerator(inventoryPlayer, woodGenerator));
        this.woodGenerator = woodGenerator;

        xSize = 176;
        ySize = 218;
        tabs = new GuiTabBase[]{
                new GuiTabSapling("Oak", 0, woodGenerator.getWorldObj()),
                new GuiTabSapling("Spruce", 1, woodGenerator.getWorldObj()),
                new GuiTabSapling("Birch", 2, woodGenerator.getWorldObj()),
                new GuiTabSapling("Jungle", 3, woodGenerator.getWorldObj()),
                new GuiTabSapling("Acacia", 4, woodGenerator.getWorldObj()),
                new GuiTabSapling("Dark oak", 5, 58, 55, woodGenerator.getWorldObj())
        };
        woodGenerator.updateWoodType();
        activeTab = tabs[woodGenerator.getType()];
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        activeTab.drawForeground(this, x, y);

        String title = StringUtils.localize("tile.woodGenerator.name");
        fontRendererObj.drawString(title, 8, 6, 0x404040);

        for (GuiTab tab : tabs) {
            tab.drawHoverText(this, x, y, tab.getName());
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1, 1, 1, 1);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        double timerWidth = ((double) woodGenerator.getTimer() / (double) woodGenerator.getTimerMax()) * 51;
        if (timerWidth >= 0 && timerWidth <= 51) {
            drawTexturedModalRect(guiLeft + 53, guiTop + 34, xSize, 0, (int) timerWidth, 18);
        }

        for (GuiTab tab : tabs) {
            int srcY = 18;
            if (tab == activeTab) {
                srcY += 28;
            } else if (tab.inRect(this, x, y)) {
                srcY += 14;
            }
            tab.draw(this, xSize, srcY);
        }
        activeTab.drawBackground(this, x, y);


    }

    @Override
    public void initGui() {
        super.initGui();

        activeTab = tabs[woodGenerator.getType()];
    }

    @Override
    protected void mouseClicked(int x, int y, int button) {
        super.mouseClicked(x, y, button);

        for (GuiTabBase tab : tabs) {
            if (activeTab != tab) {
                if (tab.inRect(this, x, y)) {
                    activeTab = tab;
                    woodGenerator.getWorldObj().setBlockMetadataWithNotify(woodGenerator.xCoord, woodGenerator.yCoord, woodGenerator.zCoord, activeTab.id, 2);
                    PacketPipeline.sendToServer(new PacketGuiWoodGenerator(activeTab.id, woodGenerator.xCoord, woodGenerator.yCoord, woodGenerator.zCoord));
                }
            }
        }
        activeTab.mouseClick(this, x, y, button);

    }

    @Override
    protected void mouseClickMove(int x, int y, int button, long timeSinceClick) {
        super.mouseClickMove(x, y, button, timeSinceClick);

        activeTab.mouseMovedClick(this, x, y, button, timeSinceClick);
    }

    @Override
    protected void mouseMovedOrUp(int x, int y, int button) {
        super.mouseMovedOrUp(x, y, button);
        activeTab.mouseReleased(this, x, y, button);
    }


    public int getLeft() {
        return guiLeft;
    }

    public int getTop() {
        return guiTop;
    }

    public FontRenderer getFontRenderer() {
        return fontRendererObj;
    }

    public void drawHoverString(List list, int x, int y) {
        drawHoveringText(list, x, y, fontRendererObj);
    }
}
