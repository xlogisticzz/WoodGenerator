package com.xlogisticzz.woodGenerator.utils;

import net.minecraft.util.StatCollector;

/**
 * @author xLoGisTicZz
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class StringUtils {

    public static String localize(String string) {
        return StatCollector.translateToLocal(string);
    }

    public static boolean canLocalize(String string) {
        return StatCollector.canTranslate(string);
    }
}
