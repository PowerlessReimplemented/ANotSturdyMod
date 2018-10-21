package powerlessri.anotsturdymod.blocksystems.remoteenergynetwork.storage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldSavedData;
import powerlessri.anotsturdymod.blocksystems.remoteenergynetwork.tile.TileENController;
import powerlessri.anotsturdymod.world.AbstractSubStorage;

import java.util.ArrayList;

public class ControllerNetworkData extends AbstractSubStorage {

    public static final int DEFAULT_CHANNEL = 0;

    public static final String NAME = "systemENData";
    public static final String CONTROLLER_CHANNEL_USAGE = "cntrllrNextChnnl";


    // Data which will be saved to disk
    private int lastChannel = DEFAULT_CHANNEL;

    public final TileENController.FakeTE FAKE_EN_CONTROLLER_TILE = new TileENController.FakeTE();
    public ArrayList<TileENController> controllerTiles = new ArrayList<>();

    public ControllerNetworkData(WorldSavedData parentData) {
        super(parentData);
    }


    public void init() {
        controllerTiles.clear();
        controllerTiles.add(FAKE_EN_CONTROLLER_TILE);
        // When controllerNextChannel == 1, there's no channels got allocated
        // Which already gave the space for channel 0
        for (int i = 0; i < lastChannel; i++) {
            controllerTiles.add(null);
        }
    }


    public int getNextChannel() {
        int allocation = ++lastChannel;
        controllerTiles.add(null);

        parentData.markDirty();
        return allocation;
    }

    /**
     * Test if the channel given is appropriate or not. Ignores allocation range.
     *
     * @return {@code true} when != DEFAULT_CHANNEL
     *         {@code false} when == DEFAULT_CHANNEL
     */
    public boolean isChanelInitialized(int channel) {
        return channel != DEFAULT_CHANNEL;
    }

    /**
     * Test whether channel is in range of allocation or not.
     */
    public boolean isChannelAllocated(int channel) {
        return isChanelInitialized(channel) && channel <= lastChannel;
    }

    public boolean isControllerLoaded(TileENController controller) {
        return isControllerLoaded(controller.channel);
    }

    public boolean isControllerLoaded(int channel) {
        return controllerTiles.get(channel) != null;
    }


    /**
     * Put given controller tile into reference list <b>if</b> the reference is clear.
     * @see #setControllerReference
     */
    public void trySetControllerReference(TileENController controller) {
        if (isChannelAllocated(controller.channel) && !isControllerLoaded(controller)) {
            setControllerReference(controller);
        }
    }

    /**
     * Put given controller tile into reference list.
     */
    public void setControllerReference(TileENController controller) {
        controllerTiles.set(controller.channel, controller);
    }

    public void deleteControllerReference(TileENController controller) {
        deleteControllerReference(controller.channel);
    }

    public void deleteControllerReference(int channel) {
        if (isChannelAllocated(channel)) {
            controllerTiles.set(channel, null);
        }
    }


    public void readFromNBT(NBTTagCompound tag) {
        lastChannel = tag.getInteger(CONTROLLER_CHANNEL_USAGE);
    }

    public void writeToNBT(NBTTagCompound tag) {
        tag.setInteger(CONTROLLER_CHANNEL_USAGE, lastChannel);
    }

    @Override
    public String getName() {
        return NAME;
    }

}
