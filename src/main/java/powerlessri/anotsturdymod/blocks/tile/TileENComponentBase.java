package powerlessri.anotsturdymod.blocks.tile;

import net.minecraft.nbt.NBTTagCompound;
import powerlessri.anotsturdymod.blocks.tile.base.TileEntityBase;
import powerlessri.anotsturdymod.world.AnsmSavedData;

import javax.annotation.Nullable;

public class TileENComponentBase extends TileEntityBase {

    // NBT tag keys
    public static final String IO_LIMIT = "ioLm";
    public static final String IO_UPGRADES = "ioUpgs";


    protected AnsmSavedData data;

    protected int channel;
    protected int ioLimit;


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
    public boolean setChannel(int channel) {
        if (channel > 0) {
            int oldChannel = this.channel;
            this.channel = channel;

            if (isControllerValid()) {
                return true;
            }

            this.channel = oldChannel;
        }
        return false;
    }


    public boolean isControllerValid() {
        return channel < data.controllerTiles.size();
    }

    @Nullable
    public TileENController getController() {
        // This channel has been allocated
        if (channel < data.controllerTiles.size()) {
            return data.controllerTiles.get(this.channel);
        }

        return data.FAKE_EN_CONTROLLER_TILE;
    }



    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.channel = tag.getInteger(TileENController.CHANNEL);
        this.ioLimit = tag.getInteger(IO_LIMIT);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setInteger(TileENController.CHANNEL, channel);
        tag.setInteger(IO_LIMIT, ioLimit);

        return super.writeToNBT(tag);
    }

}
