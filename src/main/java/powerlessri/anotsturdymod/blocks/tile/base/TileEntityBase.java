package powerlessri.anotsturdymod.blocks.tile.base;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import powerlessri.anotsturdymod.library.utils.Utils;

import javax.annotation.Nullable;

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


    protected void sendUpdates() {
//        world.markBlockRangeForRenderUpdate(pos, pos);
        world.notifyBlockUpdate(pos, getWorldBlockState(), getWorldBlockState(), 3);
//        world.scheduleBlockUpdate(pos, getWorldBlockType(), 0, 0);
        markDirty();
    }


    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 3, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pckt) {
        super.onDataPacket(net, pckt);
        if(world.isRemote) {
            handleUpdateTag(pckt.getNbtCompound());
            return;
        }
        // TODO add server side updates
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        // Tile id, coordinates, etc.
        NBTTagCompound tag = super.getUpdateTag();
        // Custom data
        writeToNBT(tag);

        return tag;
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
