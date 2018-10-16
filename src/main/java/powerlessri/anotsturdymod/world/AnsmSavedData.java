package powerlessri.anotsturdymod.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import powerlessri.anotsturdymod.blocksystems.remoteenergynetwork.tile.TileENController;
import powerlessri.anotsturdymod.library.Reference;

import java.util.ArrayList;

public class AnsmSavedData extends WorldSavedData {

    public static AnsmSavedData fromWorld(World world) {
        MapStorage storage = world.getMapStorage();
        AnsmSavedData result = (AnsmSavedData) storage.getOrLoadData(AnsmSavedData.class, DATA_NAME);

        // MapStorage#getOrLoadData will automatically create a object if it hasn't been loaded,
        // which means it'll never return null
//        if (result == null) {
//            storage.setData(DATA_NAME, new AnsmSavedData(DATA_NAME));
//            result = (AnsmSavedData) storage.getOrLoadData((AnsmSavedData.class), DATA_NAME);
//            result.constructRuntimeData();
//        }

        return result;
    }


    public static final String DATA_NAME = Reference.MODID + "_GeneralData";

    // NBT tag keys
    public static final String COUNTER = "counters";
    public static final String CONTROLLER_CHANNEL_USAGE = "cntrllrNextChnnl";


    public int controllerNextChannel = 1;


    // ======== Runtime Data ======== //

    public final TileENController.FakeTE FAKE_EN_CONTROLLER_TILE = new TileENController.FakeTE();
    public ArrayList<TileENController> controllerTiles = new ArrayList<>();

    // ======== Runtime Data ========//


    /**
     * Used for so that Minecraft can correctly instantiate on MapStorage#getOrLoadData
     */
    public AnsmSavedData(String name) {
        super(name);

        constructRuntimeData();
    }


    private void initListControllerTiles(int sizeToAlloc) {
        controllerTiles.clear();
        controllerTiles.add(FAKE_EN_CONTROLLER_TILE);
        // When controllerNextChannel == 1, there's no channels got allocated
        // Which already gave the space for channel 0
        for (int i = 0; i < sizeToAlloc; i++) {
            controllerTiles.add(null);
        }
    }

    public void constructRuntimeData() {
        initListControllerTiles(controllerNextChannel - 1);
    }


    @Override
    public void readFromNBT(NBTTagCompound data) {
        {
            NBTTagCompound tag = data.getCompoundTag(COUNTER);
            this.controllerNextChannel = tag.getInteger(CONTROLLER_CHANNEL_USAGE);
        }

        constructRuntimeData();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        {
            NBTTagCompound tag = data.getCompoundTag(COUNTER);
            tag.setInteger(CONTROLLER_CHANNEL_USAGE, controllerNextChannel);
        }

        return data;
    }

}
