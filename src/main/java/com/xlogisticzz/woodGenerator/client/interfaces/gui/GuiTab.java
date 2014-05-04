package com.xlogisticzz.woodGenerator.client.interfaces.gui;

/**
 * @author xLoGisTicZz
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public abstract class GuiTab extends GuiRectangle {

    private String name;

    public GuiTab(String name, int id) {
        super(8, 55 + id * 16, 44, 14);

        this.name = name;
    }

    public GuiTab(String name, int id, int x, int y) {
        super(x, y, 44, 14);

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void drawBackground(GuiWoodGenerator gui, int x, int y);

    public abstract void drawForeground(GuiWoodGenerator gui, int x, int y);

    public void mouseClick(GuiWoodGenerator gui, int x, int y, int button) {
    }

    public void mouseMovedClick(GuiWoodGenerator gui, int x, int y, int button, long timeSinceClicked) {
    }

    public void mouseReleased(GuiWoodGenerator gui, int x, int y, int button) {
    }
}
