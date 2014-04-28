package com.xlogisticzz.woodGenerator.utils.gui;

import net.minecraft.client.gui.FontRenderer;

/**
 * @author xLoGisTicZz
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class GuiUtils {

    public static void drawCenteredString(FontRenderer fontRenderer, String string, int x, int y, int colour) {
        fontRenderer.drawString(string, x - fontRenderer.getStringWidth(string) / 2, y, colour);
    }
}

