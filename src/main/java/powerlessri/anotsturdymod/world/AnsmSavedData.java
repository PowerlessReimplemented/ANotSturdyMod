package powerlessri.anotsturdymod.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import powerlessri.anotsturdymod.blocks.tile.TileENController;
import powerlessri.anotsturdymod.library.utils.Reference;

import java.util.ArrayList;

public class AnsmSavedData extends WorldSavedData {

    public static AnsmSavedData fromWorld(World world) {
        MapStorage storage = world.getMapStorage();
        AnsmSavedData result = (AnsmSavedData) storage.getOrLoadData(AnsmSavedData.class, DATA_NAME);

        if(result == null) {
            storage.setData(DATA_NAME, new AnsmSavedData(DATA_NAME));
            result = (AnsmSavedData) storage.getOrLoadData((AnsmSavedData.class), DATA_NAME);
            result.constructRuntimeData();
        }

        return result;
    }



    public static final String DATA_NAME = Reference.MODID + "_GeneralData";

    // NBT tag keys
    public static final String CONTROLLER_CHANNEL_USAGE = "cntrllrNextChnnl";



    public int controllerNextChannel = 1;


    // ======== Runtime Data ======== //

    public final TileENController.TileFakeENController FAKE_EN_CONTROLLER_TILE = new TileENController.TileFakeENController();
    public ArrayList<TileENController> controllerTiles = new ArrayList<>();

    // ======== Runtime Data ========//


    /** Used for so that Minecraft can correctly instantiate on MapStorage#getOrLoadData */
    public AnsmSavedData(String name) {
        super(name);
    }



    private void reconstructListControllerTiles(int size) {
        controllerTiles.clear();
        controllerTiles.add(FAKE_EN_CONTROLLER_TILE);
        // When controllerNextChannel == 1, there's no channels got allocated
        // Which already gave the space for channel 0
        for(int i = 0; i < size; i++) {
            controllerTiles.add(null);
        }
    }

    private void constructRuntimeData() {
        reconstructListControllerTiles(controllerNextChannel - 1);
    }



    @Override
    public void readFromNBT(NBTTagCompound tag) {
        this.controllerNextChannel = tag.getInteger(CONTROLLER_CHANNEL_USAGE);

        constructRuntimeData();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setInteger(CONTROLLER_CHANNEL_USAGE, controllerNextChannel);

        return tag;
    }

}
