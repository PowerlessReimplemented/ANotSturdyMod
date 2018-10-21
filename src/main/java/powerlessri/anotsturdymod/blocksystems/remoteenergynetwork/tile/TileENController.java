package powerlessri.anotsturdymod.blocksystems.remoteenergynetwork.tile;

import net.minecraft.nbt.NBTTagCompound;
import powerlessri.anotsturdymod.blocks.tile.base.TileEntityBase;
import powerlessri.anotsturdymod.blocksystems.remoteenergynetwork.storage.ControllerNetworkData;
import powerlessri.anotsturdymod.world.AnsmSavedData;

/**
 * This block does not meant to be a direct part of forge energy system. Use {@link TileENComponentBase}
 * for any access to the power storage managed by this block.
 */
public class TileENController extends TileEntityBase {

    public static class FakeTE extends TileENController {

        public FakeTE() {
        }

        @Override
        public int getOrAllocChannel() {
            return 0;
        }

        @Override
        public int getChannel() {
            return 0;
        }

        @Override
        public long getCapacity() {
            return 0;
        }

        @Override
        public long getCapacityLeft() {
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

        @Override
        public void readFromNBT(NBTTagCompound tag) {
            super.readFromNBT(tag);
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound tag) {
            return super.writeToNBT(tag);
        }

    }


    // NBT tags
    public static final String CHANNEL = "storageChannel";
    public static final String STORAGE_UPGRADES = "storageUpgrades";
    public static final String STORAGE_ENERGY_REMAIN = "energyStored";

    public static final long DEFAULT_CAPACITY = 1000000000L;
    public static final long STORAGE_UPGRADE_INCREMENT = 10000000L;
    public static final int MAX_STORAGE_UPGRADES = 64;


    private ControllerNetworkData data;


    /**
     * A unique channel id (in the save). An allocated (non-default) channel that is at least {@code 1}.
     */
    public int channel;

    /**
     * Amount of storage upgrades installed. Used to calculate total capacity.
     */
    public long amountStorageUpgrades = 0;
    /**
     * Energy stored inside controller.
     */
    public long energyStored = 0;


    public int getOrAllocChannel() {
        if (!data.isChanelInitialized(channel)) {
            allocateChannel();
        }

        return this.getChannel();
    }

    public int getChannel() {
        return channel;
    }


    private void allocateChannel() {
        channel = data.getNextChannel();
        // Now it has a channel, put itself into the reference list.
        onLoadServer();
    }


    /**
     * Install storage upgrades with capacity check.
     *
     * @return The amount of storage upgrades accepted.
     */
    public int installStorageUpgrade(int attempt) {
        int availableSlots = (int) (MAX_STORAGE_UPGRADES - amountStorageUpgrades);
        int actualInsertion = Math.min(availableSlots, attempt);
        amountStorageUpgrades += actualInsertion;
        return actualInsertion;
    }


    public long getCapacity() {
        return DEFAULT_CAPACITY + (amountStorageUpgrades * STORAGE_UPGRADE_INCREMENT);
    }

    public long getCapacityLeft() {
        return getCapacity() - energyStored;
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
        onChunkUnloadServer();
    }


    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.channel = tag.getInteger(CHANNEL);

        this.amountStorageUpgrades = tag.getLong(STORAGE_UPGRADES);
        this.energyStored = tag.getLong(STORAGE_ENERGY_REMAIN);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setInteger(CHANNEL, channel);

        tag.setLong(STORAGE_UPGRADES, amountStorageUpgrades);
        tag.setLong(STORAGE_ENERGY_REMAIN, energyStored);

        return super.writeToNBT(tag);
    }

}