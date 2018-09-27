package powerlessri.anotsturdymod.tile;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import powerlessri.anotsturdymod.blocks.BlockEnergyController;
import powerlessri.anotsturdymod.tile.base.TileEntityBase;
import powerlessri.anotsturdymod.world.AnsmSavedData;

/**
 * No idea why is the name soooooo long.
 */
public class TileControllerEnergyNetworkAccessPort extends TileEntityBase implements ITickable, IEnergyStorage {

    /** Reference to (supposedly) the only instance of BlockEnergyController. Exists for compat issues. */
    public static final BlockEnergyController CONTROLLER_BLOCK = BlockEnergyController.INSTANCE;
    public static final String IS_PLUG = "plug";



    private AnsmSavedData data;

    private int channel;
    private int ioLimit;
    /**
     * When isPlug, this access is for inserting energy to controller.
     * When !isPlug, this access is used for extracting energy to the controller
     */
    private boolean isPlug;


    public TileControllerEnergyNetworkAccessPort() {
        this(0, 5000, false);
    }

    public TileControllerEnergyNetworkAccessPort(int channel, int ioLimit, boolean isPlug) {
        this.channel = channel;
        this.ioLimit = ioLimit;
        this.isPlug = isPlug;
    }


    public int getChannel() {
        return this.channel;
    }

    /** @return {@code true} for success. <br /> {@code false} for fail.*/
    public boolean setChannel(int channel) {
        int oldChannel = this.channel;
        this.channel = channel;
        if(getController() == null) {
            this.channel = oldChannel;
            return false;
        }
        return true;
    }


    // Note: even though this method might return null, TileControllerEnergyNetworkAccessPort#setChannel will ensure it won't happen by ensuring the input channel exists.
    // Except: started with a channel don't even exist.
    @Nullable
    public TileEnergyNetworkController getController() {
        // This channel has been allocated
        if(data.controllerTiles.size() > channel) {
            return data.controllerTiles.get(this.channel);
        }

        return null;
    }
    
    @Override
    public void onLoad() {
        super.onLoad();
        
        if(data == null) {
            data = AnsmSavedData.fromWorld(getWorld());
        }
    }



    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if(CapabilityEnergy.ENERGY == capability) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(CapabilityEnergy.ENERGY == capability) {
            return CapabilityEnergy.ENERGY.cast(this);
        }
        return super.getCapability(capability, facing);
    }



    @Override
    public void update() {
        for(EnumFacing facing : EnumFacing.VALUES) {
            BlockPos neighborPos = getPos().offset(facing);
            TileEntity tile = getWorld().getTileEntity(neighborPos);
            EnumFacing opposite = facing.getOpposite();
            
            // Don't insert to another access port, it might cause problems (e.g. loop)
            if(tile != null && !(tile instanceof TileControllerEnergyNetworkAccessPort)) {
                if(tile.hasCapability(CapabilityEnergy.ENERGY, opposite)) {
                    IEnergyStorage storage = tile.getCapability(CapabilityEnergy.ENERGY, opposite);

                    if(storage.canReceive()) {
                        int extractLimit = this.extractEnergy(ioLimit, true);
                        int accepted = storage.receiveEnergy(extractLimit, false);
                        getController().energyStored -= extractLimit - accepted;
                    }
                }
            }
        }
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if(this.canReceive()) {
            TileEnergyNetworkController controller = this.getController();
            
            int accepted = Math.min((int) controller.energyStored, maxReceive);
            if(!simulate)
                controller.energyStored += accepted;
            
            return accepted;
        }
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if(this.canExtract()) {
            TileEnergyNetworkController controller = this.getController();
            
            int removed = Math.min((int) controller.getCapacityLeft(), maxExtract);
            if(!simulate)
                controller.energyStored -= removed;
            
            return removed;
        }
        return 0;
    }

    @Override
    public int getEnergyStored() {
        return (int) this.getController().energyStored;
    }

    @Override
    public int getMaxEnergyStored() {
        return (int) this.getController().getCapacity();
    }


    @Override
    public boolean canExtract() {
        return !isPlug;
    }

    @Override
    public boolean canReceive() {
        return isPlug;
    }



    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.channel = tag.getInteger(TileEnergyNetworkController.CHANNEL);
        this.isPlug = tag.getBoolean(IS_PLUG);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setInteger(TileEnergyNetworkController.CHANNEL, channel);
        tag.setBoolean(IS_PLUG, isPlug);

        return super.writeToNBT(tag);
    }

}