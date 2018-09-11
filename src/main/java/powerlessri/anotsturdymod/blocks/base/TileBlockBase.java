package powerlessri.anotsturdymod.blocks.base;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public abstract class TileBlockBase extends BasicBlockBase {

    public TileBlockBase(String name, Material material) {
        super(name, material);
    }



    @Override
    public boolean hasTileEntity(IBlockState state) {
        return this.hasTileEntity;
    }

}
