package powerlessri.anotsturdymod.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.blocks.base.TileBlockBase;

public class BlockEnergyAccessPort extends TileBlockBase {

    public BlockEnergyAccessPort(String name) {
        super(name, Material.ROCK);
    }

    
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return null;
    }
    
    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return null;
    }

}
