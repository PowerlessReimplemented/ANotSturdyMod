package powerlessri.anotsturdymod.blocks.base;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public abstract class TileBlockBase extends SimpleBlockBase {

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

}
