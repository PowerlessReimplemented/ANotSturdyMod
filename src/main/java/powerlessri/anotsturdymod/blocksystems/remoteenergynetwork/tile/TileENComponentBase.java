package powerlessri.anotsturdymod.blocksystems.remoteenergynetwork.tile;

import net.minecraft.nbt.NBTTagCompound;
import powerlessri.anotsturdymod.blocks.tile.base.TileEntityBase;
import powerlessri.anotsturdymod.world.AnsmSavedData;

public class TileENComponentBase extends TileEntityBase {

    // NBT tag keys
    public static final String IO_LIMIT = "ioLm";
    public static final String IO_UPGRADES = "ioUpgs";


    protected AnsmSavedData data;

    protected int channel;
    protected int ioLimit;
    protected int ioUpgrades;


    public TileENComponentBase() {
    }

    public TileENComponentBase(int channel, int ioLimit) {
        this.channel = channel;
        this.ioLimit = ioLimit;
    }


    @Override
    public void onLoadServer() {
        if (data == null) {
            data = AnsmSavedData.fromWorld(getWorld());
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
        return channel < data.controllerTiles.size();
    }

    public TileENController getController() {
        // This channel has been allocated
        if (isControllerValid()) {
            TileENController controller = data.controllerTiles.get(this.channel);
            if (controller != null) {
                return controller;
            }
        }
        return data.FAKE_EN_CONTROLLER_TILE;
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
