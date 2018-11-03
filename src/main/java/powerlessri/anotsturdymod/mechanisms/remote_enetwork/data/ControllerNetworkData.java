package powerlessri.anotsturdymod.mechanisms.remote_enetwork.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldSavedData;
import powerlessri.anotsturdymod.mechanisms.remote_enetwork.IENetworkController;
import powerlessri.anotsturdymod.mechanisms.remote_enetwork.tile.TileENController;
import powerlessri.anotsturdymod.world.data.AbstractSubStorage;

import java.util.ArrayList;

public class ControllerNetworkData extends AbstractSubStorage {

    public static final int DEFAULT_CHANNEL = 0;

    public static final String NAME = "systemENData";
    public static final String CONTROLLER_CHANNEL_USAGE = "cntrlrNxtChnl";


    // Data which will be saved to disk
    private int lastChannel = DEFAULT_CHANNEL;

    // Runtime data
    public final TileENController.FakeTE FAKE_EN_CONTROLLER_TILE = new TileENController.FakeTE();
    public ArrayList<IENetworkController> controllers = new ArrayList<>();

    public ControllerNetworkData(WorldSavedData parentData) {
        super(parentData);
    }


    public void init() {
        controllers.clear();
        controllers.add(FAKE_EN_CONTROLLER_TILE);
        // When controllerNextChannel == 1, there's no channels got allocated
        // Which already gave the space for channel 0
        for (int i = 0; i < lastChannel; i++) {
            controllers.add(null);
        }
    }


    public int getNextChannel() {
        int allocation = ++lastChannel;
        controllers.add(null);

        parentData.markDirty();
        return allocation;
    }

    /**
     * Test if the channel given is appropriate or not. Ignores allocation range.
     *
     * @return {@code true} when != DEFAULT_CHANNEL
     * {@code false} when == DEFAULT_CHANNEL
     */
    public boolean isChannelInitialized(int channel) {
        return channel != DEFAULT_CHANNEL;
    }

    /**
     * Test whether channel is in range of allocation or not.
     */
    public boolean isChannelAllocated(int channel) {
        return isChannelInitialized(channel) && channel <= lastChannel;
    }

    public boolean isControllerLoaded(IENetworkController controller) {
        return isControllerLoaded(controller.getChannel());
    }

    public boolean isControllerLoaded(int channel) {
        return controllers.get(channel) != null;
    }


    /**
     * Put given controller tile into reference list <b>if</b> the reference is clear.
     *
     * @see #setControllerReference
     */
    public void trySetControllerReference(IENetworkController controller) {
        if (isChannelAllocated(controller.getChannel()) && !isControllerLoaded(controller)) {
            setControllerReference(controller);
        }
    }

    /**
     * Put given controller tile into reference list.
     */
    public void setControllerReference(IENetworkController controller) {
        controllers.set(controller.getChannel(), controller);
    }

    public void deleteControllerReference(IENetworkController controller) {
        deleteControllerReference(controller.getChannel());
    }

    public void deleteControllerReference(int channel) {
        if (isChannelAllocated(channel)) {
            controllers.set(channel, null);
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
