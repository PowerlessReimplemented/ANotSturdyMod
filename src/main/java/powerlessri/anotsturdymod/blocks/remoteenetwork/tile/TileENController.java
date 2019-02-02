package powerlessri.anotsturdymod.blocks.remoteenetwork.tile;

import net.minecraft.nbt.NBTTagCompound;
import powerlessri.anotsturdymod.handlers.init.RegistryTileEntity;
import powerlessri.anotsturdymod.library.tile.base.TileEntityBase;
import powerlessri.anotsturdymod.handlers.init.RegistryHandler;
import powerlessri.anotsturdymod.blocks.remoteenetwork.IENetworkController;
import powerlessri.anotsturdymod.blocks.remoteenetwork.data.ControllerNetworkData;
import powerlessri.anotsturdymod.world.data.AnsmSavedData;

/**
 * This block does not meant to be a direct part of forge energy system. Use {@link TileENComponentBase}
 * for any access to the power storage managed by this block.
 */
@RegistryTileEntity("energy_network_controller")
public class TileENController extends TileEntityBase implements IENetworkController {

    public static class FakeTE extends TileENController {

        public FakeTE() {
        }


        @Override
        public int getChannel() {
            return 0;
        }

        @Override
        public int getOrAllocChannel() {
            return 0;
        }


        @Override
        public int getAmountStorageUpgrades() {
            return 0;
        }

        @Override
        public int installStorageUpgrade(int attempt) {
            return 0;
        }


        @Override
        public long getCapacity() {
            return 0;
        }


        @Override
        public long getEnergyStored() {
            return 0;
        }

        @Override
        public void setEnergyStored(long energy) {
        }


        @Override
        public void decreaseEnergy(long increment) {
        }

        @Override
        public void increaseEnergy(long decrement) {
        }


        @Override
        public long extractEnergy(long attempt, boolean simulate) {
            return 0;
        }

        @Override
        public long receiveEnergy(long attempt, boolean simulate) {
            return 0;
        }


        @Override
        public void onLoadServer() {
        }

        @Override
        public void onChunkUnloadServer() {
        }

        @Override
        public void onRemoved() {
        }

    }


    public static final long DEFAULT_CAPACITY = 1000000000L;
    public static final long STORAGE_UPGRADE_INCREMENT = 10000000L;
    public static final int MAX_STORAGE_UPGRADES = 64;


    private ControllerNetworkData data;


    /**
     * A unique network id (in the save). An allocated (non-default) network that is at least {@code 1}.
     */
    public int channel;

    public int amountStorageUpgrades;
    public long energyStored = 0;


    @Override
    public int getOrAllocChannel() {
        if (!data.isChannelInitialized(channel)) {
            allocateChannel();
        }

        return this.getChannel();
    }

    @Override
    public int getChannel() {
        return channel;
    }


    private void allocateChannel() {
        channel = data.getNextChannel();
        markDirty();
        // Now it has a network, put itself into the reference list.
        onLoadServer();
    }


    @Override
    public int getAmountStorageUpgrades() {
        return amountStorageUpgrades;
    }

    @Override
    public int installStorageUpgrade(int attempt) {
        int availableSlots = MAX_STORAGE_UPGRADES - amountStorageUpgrades;
        int actualInsertion = Math.min(availableSlots, attempt);
        amountStorageUpgrades += actualInsertion;
        return actualInsertion;
    }


    @Override
    public long getEnergyStored() {
        return energyStored;
    }

    @Override
    public void setEnergyStored(long energy) {
        energyStored = energy;
    }


    @Override
    public void decreaseEnergy(long increment) {
        energyStored -= increment;
    }

    @Override
    public long extractEnergy(long attempt, boolean simulate) {
        long actualExtraction = Math.min(energyStored, attempt);
        if (!simulate) {
            decreaseEnergy(actualExtraction);
        }
        return actualExtraction;
    }


    @Override
    public void increaseEnergy(long decrement) {
        energyStored += decrement;
    }

    @Override
    public long receiveEnergy(long attempt, boolean simulate) {
        long actualInsertion = Math.min(getCapacityLeft(), attempt);
        if (!simulate) {
            increaseEnergy(actualInsertion);
        }
        return actualInsertion;
    }


    @Override
    public long getCapacity() {
        return DEFAULT_CAPACITY + (amountStorageUpgrades * STORAGE_UPGRADE_INCREMENT);
    }


    @Override
    public void onLoadServer() {
        if (data == null) {
            data = AnsmSavedData.fromWorld(world).controllerEN;
        }

        if (data.isChannelAllocated(channel)) {
            data.setControllerReference(this);
        }
    }

    @Override
    public void onChunkUnloadServer() {
        data.deleteControllerReference(channel);
    }

    @Override
    public void onRemoved() {
        this.onChunkUnloadServer();
    }


    @Override
    public void restoreFromNBT(NBTTagCompound tag) {
        this.channel = tag.getInteger("channel");
        this.energyStored = tag.getLong("energy");

        this.amountStorageUpgrades = tag.getInteger("storageUpgrades");
    }

    @Override
    public void writeRestorableNBT(NBTTagCompound tag) {
        tag.setInteger("channel", channel);
        tag.setLong("energy", energyStored);

        tag.setInteger("storageUpgrades", amountStorageUpgrades);
    }

}