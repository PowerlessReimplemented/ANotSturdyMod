package powerlessri.anotsturdymod.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.blocks.base.TileBlockBase;
import powerlessri.anotsturdymod.gui.ModGuiHandler;
import powerlessri.anotsturdymod.library.utils.Utils;
import powerlessri.anotsturdymod.tile.TileRemoteEnergyCell;

public class BlockRemoteEnergyCell extends TileBlockBase {
    
    public BlockRemoteEnergyCell(String name) {
        super(name, Material.IRON);

        this.setHardness(2.0f);
        this.setResistance(15.0f);
        this.setHarvestLevel(EHarvestTool.PICKAXE, EHarvestLevel.IRON);

        // TODO add own creative tab
        this.setCreativeTab(CreativeTabs.MISC);
    }



    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if(world.isRemote) {
            return true;
        }

        TileRemoteEnergyCell tile = ((TileRemoteEnergyCell) world.getTileEntity(pos));
        Utils.getLogger().info("Energy cell id: " + tile.channel);
        if(tile.channel == -1) {
            tile.setChannel();
            Utils.getLogger().info("Updated energy cell id: " + tile.channel);
        }
        
        // TODO insert upgrades, open gui
        player.openGui(ANotSturdyMod.instance, ModGuiHandler.REMOTE_ENERGY_CELL, world, pos.getX(), pos.getY(), pos.getZ());
        
        return false;
    }



    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileRemoteEnergyCell.class;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        // Use default properties from TileRemoteEnergyCell
        return new TileRemoteEnergyCell(2000000, 5000, 5000);
    }

}
