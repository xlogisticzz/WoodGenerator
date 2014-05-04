package com.xlogisticzz.woodGenerator.client.interfaces.gui;

import net.minecraft.world.World;

/**
 * @author xLoGisTicZz
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class GuiTabSapling extends GuiTabBase {

    public GuiTabSapling(String name, int id, World world) {
        super(name, id, world);
    }

    public GuiTabSapling(String name, int id, int x, int y, World world) {
        super(name, id, x, y, world);
    }

    @Override
    public void drawBackground(GuiWoodGenerator gui, int x, int y) {
        super.drawBackground(gui, x, y);
    }

    @Override
    public void drawForeground(GuiWoodGenerator gui, int x, int y) {
        super.drawForeground(gui, x, y);
    }
}
