package powerlessri.anotsturdymod.blocks.base;

import net.minecraft.tileentity.TileEntity;

public interface ITileIntergratedBlock {
    
    Class<? extends TileEntity> getTileEntityClass();
    
}
