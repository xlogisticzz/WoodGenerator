package com.xlogisticzz.woodGenerator.blocks;

import com.xlogisticzz.woodGenerator.WoodGeneratorCreativeTab;
import com.xlogisticzz.woodGenerator.lib.Constants;
import com.xlogisticzz.woodGenerator.tileEntities.TileWoodGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * @author xLoGisTicZz
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class BlockWoodGenerator extends BlockContainer {

    private IIcon[] topIcons;
    private IIcon[] sideIcons;

    public BlockWoodGenerator() {
        super(Material.wood);
        setCreativeTab(WoodGeneratorCreativeTab.tabWoodGenerator);
        setHardness(3F);
        setResistance(3F);
        setBlockName(Constants.WOODGENERATOR);
        topIcons = new IIcon[5];
        sideIcons = new IIcon[5];
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        for (int i = 0; i < topIcons.length; i++) {
            topIcons[i]= register.registerIcon(Constants.MODID + ":" + Constants.TOPICONS[i]);
        }
        for (int i = 0; i < sideIcons.length; i++) {
            sideIcons[i]= register.registerIcon(Constants.MODID + ":" + Constants.SIDEICONS[i]);
        }
    }

    /*
    *  0 - oak
    *  1 - birch
    *  2 - spruce
    *  3 - jungle
    *  4 - acacia
    */
    @Override
    public IIcon getIcon(int side, int meta) {
        if(side == 0 || side == 1){
            if(meta < topIcons.length){
                return topIcons[meta];
            }
        } else {
            if(meta < sideIcons.length){
                return sideIcons[meta];
            }
        }
        return blockIcon;
    }

    // TODO remove and load gui
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        int meta = world.getBlockMetadata(x, y, z);
        if(meta > 3){
            world.setBlockMetadataWithNotify(x , y, z , 0 , 3);
        } else {
            world.setBlockMetadataWithNotify(x , y, z , meta + 1 , 3);
        }

        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileWoodGenerator();
    }
}
