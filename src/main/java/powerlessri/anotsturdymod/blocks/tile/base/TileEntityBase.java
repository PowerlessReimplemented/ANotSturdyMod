package powerlessri.anotsturdymod.blocks.tile.base;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityBase extends TileEntity {

    // Implementation Note: 
    //     This method gets invoked at TileBlockBase#breakBlock(World, BlockPos, IBlockState)
    /** Called when tile entity is removed from world. Does not include chunk unloading. */
    public void onRemoved() {
    }


    public void onLoadServer() {}
    public void onLoadClient() {}

    @Override
    public void onLoad() {
        if(world.isRemote) {
            onLoadClient();
        } else {
            onLoadServer();
        }
    }

    public void onChunkUnloadServer() {}
    public void onChunkUnloadClient() {}

    @Override
    public void onChunkUnload() {
        if(world.isRemote) {
            onChunkUnloadClient();
        } else {
            onChunkUnloadServer();
        }
    }


    public IBlockState getWorldBlockState() {
        return world.getBlockState(pos);
    }

    public Block getWorldBlockType() {
        return getWorldBlockState().getBlock();
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
