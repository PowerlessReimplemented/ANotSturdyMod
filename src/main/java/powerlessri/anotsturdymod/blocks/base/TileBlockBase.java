package powerlessri.anotsturdymod.blocks.base;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.blocks.tile.base.TileEntityBase;

public abstract class TileBlockBase extends SimpleBlockBase implements ITileIntergratedBlock {

    public TileBlockBase(String name, Material material) {
        super(name, material);
        
        this.hasTileEntity = true;
    }



    /* (non-Javadoc)
     * Mojang have an official tile entity api, ITileEntityProvider, which uses meta value,
     * but Forge actually uses Block#hasTileEntity & Block#createTileEntity as the actual api.
     * 
     * This field #hasTileEntity hasn't been used for a long time since BlockContainer doesn't gets used.
     */
    @Override
    public boolean hasTileEntity(IBlockState state) {
        return this.hasTileEntity;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && tile instanceof TileEntityBase) {
            ((TileEntityBase) tile).onRemoved();
        }
    }



    @Override
    public boolean canEntitySpawn(IBlockState state, Entity entity) {
        return false;
    }

}
