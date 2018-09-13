package powerlessri.anotsturdymod.tile.base;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityBase extends TileEntity {
    
    //TODO complete nbt storage
    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        return super.writeToNBT(tag);
    }
    
}
