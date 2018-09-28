package powerlessri.anotsturdymod.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.blocks.base.TileBlockBase;
import powerlessri.anotsturdymod.tile.TileControllerEnergyNetworkAccessPort;
import powerlessri.anotsturdymod.tile.TileControllerEnergyNetworkOutput;

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
    public TileEntity createTileEntity(World world, IBlockState state) {
        if(!isPlug) {
            return new TileControllerEnergyNetworkOutput(1, ioLimit);
        }
        return new TileControllerEnergyNetworkAccessPort(1, ioLimit);
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileControllerEnergyNetworkAccessPort.class;
    }

}
