package powerlessri.anotsturdymod.tile.base;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityBase extends TileEntity {
    
    // TODO figure out how to make this thing work
    /** Called when tile entity is added to world. Does not include chunk reloading. */
    public void onAdded() {
    }
    
    // Implementation Note: 
    //     This method gets invoked at TileBlockBase#breakBlock(World, BlockPos, IBlockState)
    /** Called when tile entity is removed from world. Does not include chunk unloading. */
    public void onRemoved() {
    }
    
    
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
