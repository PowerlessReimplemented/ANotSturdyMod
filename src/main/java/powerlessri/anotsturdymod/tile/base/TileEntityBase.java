package powerlessri.anotsturdymod.tile.base;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityBase extends TileEntity {
    
    public IBlockState getWorldBlockState() {
        return this.getWorld().getBlockState( this.getPos() );
    }
    
    public Block getWorldBlockType() {
        return this.getWorldBlockState().getBlock();
    }
    
    
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
