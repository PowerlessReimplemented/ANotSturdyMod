package powerlessri.anotsturdymod.library.block.base;

import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.library.tile.base.TileEntityBase;

import javax.annotation.Nullable;

public abstract class TileBlockBase extends SimpleBlockBase implements ITileEntityProvider {

    public TileBlockBase(String name, Material material) {
        super(name, material);
    }


    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, BlockState state) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityBase) {
            ((TileEntityBase) tile).onRemoved();
        }
    }

}
