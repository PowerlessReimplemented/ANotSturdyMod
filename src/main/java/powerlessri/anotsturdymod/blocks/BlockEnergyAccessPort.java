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
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.blocks.base.TileBlockBase;
import powerlessri.anotsturdymod.blocks.tile.TileEnergyNetworkAccessPort;
import powerlessri.anotsturdymod.blocks.tile.TileEnergyNetworkOutput;
import powerlessri.anotsturdymod.handlers.ModGuiHandler;

public class BlockEnergyAccessPort extends TileBlockBase {

    private final int ioLimit;
    private final boolean isPlug;
    
    public BlockEnergyAccessPort(String name, int ioLimit, boolean isPlug) {
        super(name, Material.ROCK);
        this.ioLimit = ioLimit;
        this.isPlug = isPlug;

        setCreativeTab(CreativeTabs.MISC);
    }


    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(world.isRemote) {
            return true;
        }

        player.openGui(ANotSturdyMod.instance, ModGuiHandler.ENERGY_ACCESS_PORT, world, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        if(!isPlug) {
            return new TileEnergyNetworkOutput(0, ioLimit);
        }
        return new TileEnergyNetworkAccessPort(0, ioLimit);
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEnergyNetworkAccessPort.class;
    }

}
