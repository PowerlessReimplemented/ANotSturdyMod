package powerlessri.anotsturdymod.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import powerlessri.anotsturdymod.library.utils.Utils;
import powerlessri.anotsturdymod.world.AnsmSaveData;
import powerlessri.anotsturdymod.world.handler.LinkedEnergyStorage;

public class TileRemoteEnergyCell extends TileEnergyStorage {

    private static final int DEFAULT_CAPACITY = 1000000;
    private static final int DEFAULT_MAX_RECEIVE = 5000;
    private static final int DEFAULT_MAX_EXTRACT = 5000;

    private static final String CHANNEL = "channel";


    private final int capacity;
    private final int maxReceive;
    private final int maxExtract;
    
    public int channel;

    private AnsmSaveData data;
    /** Reference to level-wide multiStorage */
    private LinkedEnergyStorage multiStorage;

    public TileRemoteEnergyCell() {
        // Use default status
        this(-1, -1, -1);
    }

    public TileRemoteEnergyCell(int capacity, int maxReceive, int maxExtract) {
        super();

        this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
        this.maxReceive = Math.max(DEFAULT_MAX_RECEIVE, maxReceive);
        this.maxExtract = Math.max(DEFAULT_MAX_EXTRACT, maxExtract);

        this.channel = -1;
    }

    @Override
    public void onLoad() {
        if(multiStorage == null) {
            this.data = AnsmSaveData.fromWorld(this.getWorld());
            this.multiStorage = this.data.linkedEnergyNet.get(0);
        }
    }



    public EnergyStorage getStorage() {
        return multiStorage.getStorage(this.channel);
    }

    public void setChannel() {
        this.setChannel(this.multiStorage.nextChannel());
    }

    public void setChannel(int channel) {
        if(this.channel == channel) {
            Utils.getLogger().info("Same target channel with current channel when excuting TileRemoteEnergyCell#setChannel(int)");
            return;
        }

        // Update old storage capacity
        if(this.channel != -1) this.updateParentStorage(-capacity);

        this.channel = channel;

        // Increase new storage's capacity
        if(this.channel != -1) this.updateParentStorage(capacity);
    }

    /** Modify the capacity & stored energy of parent storage after setting channel. */
    private void updateParentStorage(int capacityIncreament) {
        EnergyStorage last = multiStorage.getStorage(this.channel);
        // Take out TileRemoteEnergyCell#capacity amount of storage capacity
        // This step will dispose the energy storage 'last'
        multiStorage.setStorageTraits(this.channel, last.getMaxEnergyStored() + capacityIncreament, maxReceive, maxExtract);
        // Put rest of energy into the new energy storage
        multiStorage.getStorage(this.channel).receiveEnergy(last.getEnergyStored(), false);
        this.data.markDirty();
    }



    @Override
    public void onChunkUnload() {
        // Remove self's capacity & energy from all storages
        this.setChannel(-1);
        this.data.markDirty();
    }


    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.channel = tag.getByte(CHANNEL);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setByte(CHANNEL, (byte) this.channel);

        return super.writeToNBT(tag);
    }



    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing, boolean ignoreFacing) {
        // TODO add facing (replace true with facing condition)
        if(CapabilityEnergy.ENERGY == capability && (ignoreFacing || true)) {
            return true;
        }
        return super.hasCapability(capability, facing, ignoreFacing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(this.hasCapability(capability, facing, true)) {
            return CapabilityEnergy.ENERGY.cast(this.getStorage());
        }
        return super.getCapability(capability, facing);
    }

}
