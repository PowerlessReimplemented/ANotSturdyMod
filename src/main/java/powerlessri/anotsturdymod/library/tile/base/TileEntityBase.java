package powerlessri.anotsturdymod.library.tile.base;

import net.minecraft.block.BlockState;
import net.minecraft.block.state.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.library.network.notification.ENotificationType;
import powerlessri.anotsturdymod.library.network.notification.INotificationProvider;
import powerlessri.anotsturdymod.library.network.notification.INotificationReceiver;
import powerlessri.anotsturdymod.library.network.notification.PacketNotification;
import powerlessri.anotsturdymod.varia.general.Utils;

import javax.annotation.Nullable;

public abstract class TileEntityBase extends TileEntity implements INotificationProvider, INotificationReceiver {

    // Implementation Note: 
    //     This method gets invoked at TileBlockBase#breakBlock(World, BlockPos, BlockState)

    /**
     * Called when tile entity is removed from world. Does <b>NOT</b> include chunk unloading.
     */
    public void onRemoved() {
    }


    public void onLoadServer() {
    }

    public void onLoadClient() {
    }

    @Override
    public void onLoad() {
        if (world.isRemote) {
            this.onLoadClient();
        } else {
            this.onLoadServer();
        }
    }

    public void onChunkUnloadServer() {
    }

    public void onChunkUnloadClient() {
    }

    @Override
    public void onChunkUnload() {
        if (world.isRemote) {
            this.onChunkUnloadClient();
        } else {
            this.onChunkUnloadServer();
        }
    }


    /**
     * Send data to client-side tile entity with the current {@link #pos position}.
     * <p>Data got from {@link #getUpdateTag()} will be sent.</p>
     */
    protected void sendUpdates() {
        world.markForRerender(pos, pos);
        world.notifyBlockUpdate(pos, getWorldBlockState(), getWorldBlockState(), 3);
        this.markDirty();
    }

    /**
     * Send notification with custom data got from {@link #getNotificationTag()}} to server-side tile entity with the
     * current {@link #pos position}.
     */
    public void sendNotification() {
        ANotSturdyMod.network.sendToServer(this.getNotificationPacket());
    }



    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        if (world.isRemote) {
            this.handleUpdatePacket(pkt);
        } else {
            Utils.getLogger().warn("Received a PacketNotification on the wrong side.");
        }
    }


    /**
     * Get an NBT compound to sync data to client side.
     *
     * <p>Calls {@link #writeToNBT(CompoundNBT)}, which then calls {@link #writeRestorable(CompoundNBT)} by default.</p>
     * <p>Called when <ol>
     * <li>Chunk loaded</li>
     * <li>{@link #sendUpdates()} was called</li>
     * </ol></p>
     *
     * <p>See parent Javadoc for more information. If the information given here conflicts with the information given in parent method, this is probably inaccurate.</p>
     */
    @Override
    public CompoundNBT getUpdateTag() {
        return this.writeToNBT(new CompoundNBT());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 3, getUpdateTag());
    }

    /**
     * Handles data sent from server side.
     * <p>Calls {@link #readFromNBT(CompoundNBT)}, which then calls {@link #readRestorable(CompoundNBT)} by default.</p>
     *
     * @param tag The received data in the form of NBT compound.
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleUpdateTag(CompoundNBT tag) {
        this.readFromNBT(tag);
    }

    @OnlyIn(Dist.CLIENT)
    public void handleUpdatePacket(SPacketUpdateTileEntity pkt) {
        this.handleUpdateTag(pkt.getNbtCompound());
    }


    /**
     * Get an NBT compound to sync custom data to server side.
     * <p>Calls {@link #writeRestorable(CompoundNBT)} by default.</p>
     *
     * @return The NBT compound to be synced to server side.
     */
    @OnlyIn(Dist.CLIENT)
    public CompoundNBT getNotificationTag() {
        CompoundNBT tag = new CompoundNBT();
        this.writeRestorable(tag);
        return tag;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public PacketNotification getNotificationPacket() {
        return new PacketNotification(world.provider.getDimension(), ENotificationType.TILE_ENTITY, this.getNotificationTag());
    }

    /**
     * @param tag The custom data sent from client
     * @param net The client who sends the data packet
     */
    public void handleNotificationTag(NetworkManager net, CompoundNBT tag) {
    }

    @Override
    public void handleNotificationPacket(NetworkManager net, PacketNotification notification) {
        this.handleNotificationTag(net, notification.getData());
    }


    @Override
    public void read(CompoundNBT tag) {
        super.read(tag);
        this.readRestorable(tag);
    }

    public abstract void readRestorable(CompoundNBT tag);

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        this.writeRestorable(tag);
        // Idiot behavior (try to write data with same key as vanilla stuff) protection
        // then vanilla will override them
        super.write(tag);
        return tag;
    }

    public abstract void writeRestorable(CompoundNBT tagCompound);


    public BlockState rgetWorldBlockState() {
        return world.getBlockState(pos);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, BlockState oldState, BlockState newSate) {
        return false;
    }

    @Override
    public boolean restrictNBTCopy() {
        return true;
    }

}
