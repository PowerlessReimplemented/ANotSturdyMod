package powerlessri.anotsturdymod.blocks.base;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/** Used to force subclasses to implement certain methods to make tile entity work. */
public interface ITileIntergratedBlock {
    
    Class<? extends TileEntity> getTileEntityClass();
    
    /** Force to implement the method given by {@link net.minecraft.block.Block} */
    TileEntity createTileEntity(World world, IBlockState state);
    
}
