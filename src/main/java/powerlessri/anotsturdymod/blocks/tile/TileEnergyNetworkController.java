package powerlessri.anotsturdymod.blocks.tile;

import net.minecraft.crash.CrashReport;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ReportedException;
import powerlessri.anotsturdymod.blocks.tile.base.TileEntityBase;
import powerlessri.anotsturdymod.world.AnsmSavedData;

/**
 * This block does not meant to be a direct part of forge energy system. Use BlockEnergyAccessPort
 * for any access to the power storage managed by this block.
 */
public class TileEnergyNetworkController extends TileEntityBase {

    public static class TileFakeEnergyNetworkController extends TileEnergyNetworkController {

        public TileFakeEnergyNetworkController() {
            this.isAlive = true;
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
        public boolean isInitialized() {
            return true;
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
        public void onLoad() {
        }

        @Override
        public void onRemoved() {
        }

        @Override
        public void onChunkUnload() {
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
    public static final long STORAGE_UPGRADE_INCREMENT = 1000000L;
    public static final int MAX_STORAGE_UPGRADES = 64;


    private AnsmSavedData data;

    /** {@code true} when is loaded, {@code false} when is not loaded. */
    public boolean isAlive = false;


    /** A unique channel id (in the save). An allocated (non-default) channel is at least {@code 1}. */
    public int channel;

    /** Amount of storage upgrades installed. Used to calculate total capacity. */
    public long amountStorageUpgrades = 0;
    /** Energy stored inside controller. */
    public long energyStored = 0;


    public TileEnergyNetworkController() {
    }



    public int getOrAllocChannel() {
        if(!isInitialized()) {
            channel = data.controllerNextChannel;
            data.controllerNextChannel++;
            data.markDirty();

            // Increase the capacity, so ArrayList#set won't throw exception
            data.controllerTiles.add(null);
            // Now it has a channel, put itself into the reference list.
            this.onLoad();
        }

        return this.getChannel();
    }

    public int getChannel() {
        return channel;
    }

    public boolean isInitialized() {
        return this.channel != 0;
    }


    /**
     * Install storage upgrades with capacity check.
     *
     * @return The amount of storage upgrades accepted.
     */
    public int installStorageUpgrade(int attempt) {
        int availableSlots = (int) (MAX_STORAGE_UPGRADES - amountStorageUpgrades);
        int actualInsert = Math.min(availableSlots, attempt);
        amountStorageUpgrades += actualInsert;
        return actualInsert;
    }



    public long getCapacity() {
        return DEFAULT_CAPACITY + (amountStorageUpgrades * STORAGE_UPGRADE_INCREMENT);
    }

    public long getCapacityLeft() {
        return this.getCapacity() - this.energyStored;
    }



    @Override
    public void onLoad() {
        if(data == null) {
            data = AnsmSavedData.fromWorld(getWorld());
        }

        // Wait until player activate this block (wait until it has a channel)
        if(isInitialized()) {
            if(channel < data.controllerTiles.size()) {
                updateReferenceList();
            }
        }

        isAlive = true;
    }

    private void updateReferenceList() {
        // This step might cause an IndexOutOfBoundsException, if channel is not allocated by #getOrAllocChannel()
        // but you're not suppose to do it other than #getOrAllocChannel(), so it's fine.
        //
        // BlockEnergyController#tiles actually has the appropriate size && This tile entity with the channel does not exist
        // When channel == DEFAULT_CHANNEL, #tiles[c] will always be a FakeEnergyNetworkController (non-null)
        if (data.controllerTiles.get(this.channel) == null) {
            data.controllerTiles.set(this.channel, this);
        } else {
            String description = "Unexpected repeating channel from BlockEnergyController";
            IllegalAccessException e = new IllegalAccessException(description);
            CrashReport crashReport = CrashReport.makeCrashReport(e, description);

            throw new ReportedException(crashReport);
        }
    }

    @Override
    public void onChunkUnload() {
        data.controllerTiles.set(this.channel, null);
        this.isAlive = false;
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
        tag.setInteger(CHANNEL, this.channel);

        tag.setLong(STORAGE_UPGRADES, this.amountStorageUpgrades);
        tag.setLong(STORAGE_ENERGY_REMAIN, this.energyStored);

        return super.writeToNBT(tag);
    }

}