package com.xlogisticzz.woodGenerator.client.sounds;
/*
* @author xLoGisTicZz
* @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
*/

import com.xlogisticzz.woodGenerator.lib.Constants;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class SoundHandler {

    public static void playOnEntity(Entity entity, String soundName, float volume, float pitch) {
        entity.worldObj.playSoundAtEntity(entity, (Constants.MODID + ":" + soundName), volume, pitch);
    }

    public static void playSoundInWorld(World world, double x, double y, double z, String soundName, float volume, float pitch) {
        world.playSoundEffect(x, y, z, (Constants.MODID + ":" + soundName), volume, pitch);
    }
}
