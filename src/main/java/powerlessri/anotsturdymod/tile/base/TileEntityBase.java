package powerlessri.anotsturdymod.tile.base;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

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
    
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return this.hasCapability(capability, facing, false);
    }
    
    public boolean hasCapability(Capability<?> capability, EnumFacing facing, boolean ignoreFacing) {
        return super.hasCapability(capability, facing);
    }
    
}
