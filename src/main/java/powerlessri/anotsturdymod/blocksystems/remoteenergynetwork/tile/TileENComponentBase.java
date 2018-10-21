package powerlessri.anotsturdymod.blocksystems.remoteenergynetwork.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.IEnergyStorage;
import powerlessri.anotsturdymod.blocks.tile.base.TileEntityBase;
import powerlessri.anotsturdymod.blocksystems.remoteenergynetwork.IENetworkController;
import powerlessri.anotsturdymod.blocksystems.remoteenergynetwork.storage.ControllerNetworkData;
import powerlessri.anotsturdymod.world.AnsmSavedData;

public class TileENComponentBase extends TileEntityBase {

    // NBT tag keys
    public static final String IO_LIMIT = "ioLm";
    public static final String IO_UPGRADES = "ioUpgs";


    protected ControllerNetworkData data;

    protected int channel;
    protected int ioLimit;
    protected int ioUpgrades;


    public TileENComponentBase() {
    }

    public TileENComponentBase(int channel, int ioLimit) {
        this.channel = channel;
        this.ioLimit = Math.max(0, ioLimit);
    }


    @Override
    public void onLoadServer() {
        if (data == null) {
            data = AnsmSavedData.fromWorld(world).controllerEN;
        }
    }


    public int getChannel() {
        return this.channel;
    }

    /**
     * @return {@code true} for success. <br /> {@code false} for fail.
     */
    public boolean setChannel(int newChannel) {
        if (newChannel > 0) {
            int oldChannel = channel;
            this.channel = newChannel;

            if (isControllerValid()) {
                sendUpdates();
                return true;
            }

            this.channel = oldChannel;
        }
        return false;
    }


    public boolean isControllerValid() {
        return channel < data.controllers.size();
    }

    public IENetworkController getController() {
        // This channel has been allocated
        if (isControllerValid()) {
            IENetworkController controller = data.controllers.get(this.channel);
            if (controller != null) {
                return controller;
            }
        }
        return data.FAKE_EN_CONTROLLER_TILE;
    }


    /**
     * Send as much energy as possible to the given IEnergyStorage.
     * Takes energy from the controller.
     *
     * @param target The IEnergyStorage that will be receiving energy
     */
    protected void sendEnergy(IEnergyStorage target) {
        IENetworkController controller = getController();
        int accepted = target.receiveEnergy(Math.min(ioLimit, (int) controller.getEnergyStored()), false);
        controller.extractEnergy(accepted, false);
    }


    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.channel = tag.getInteger(TileENController.CHANNEL);
        this.ioLimit = tag.getInteger(IO_LIMIT);
        this.ioUpgrades = tag.getInteger(IO_UPGRADES);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setInteger(TileENController.CHANNEL, channel);
        tag.setInteger(IO_LIMIT, ioLimit);
        tag.setInteger(IO_UPGRADES, ioUpgrades);

        return super.writeToNBT(tag);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound tag = super.getUpdateTag();
        return tag;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        super.handleUpdateTag(tag);
    }

}
