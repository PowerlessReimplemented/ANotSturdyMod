package powerlessri.anotsturdymod.world;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import powerlessri.anotsturdymod.library.utils.Reference;
import powerlessri.anotsturdymod.library.utils.Utils;
import powerlessri.anotsturdymod.tile.TileEnergyNetworkController;

public class AnsmSavedData extends WorldSavedData {

    public static AnsmSavedData fromWorld(World world) {
        MapStorage storage = world.getMapStorage();
        AnsmSavedData result = (AnsmSavedData) storage.getOrLoadData(AnsmSavedData.class, DATA_NAME);

        if(result == null) {
            result = new AnsmSavedData();
            storage.setData(DATA_NAME, result);
//            storage.setData(DATA_NAME, new AnsmSavedData());
//            result = (AnsmSavedData) storage.getOrLoadData(AnsmSavedData.class, DATA_NAME);
        }

        return result;
    }



    public static final String DATA_NAME = Reference.MODID + "_GeneralData";

    // NBT tag keys
    public static final String CONTROLLER_CHANNEL_USAGE = "cntrllrNextChnnl";

    public int controllerNextChannel = 1;
    
    
    // ======== Runtime Data ======== //
    
    public ArrayList<TileEnergyNetworkController> controllerTiles = new ArrayList<>();


    public AnsmSavedData() {
        this(DATA_NAME);
    }

    /** Used for so that minecraft can correctly instantiate on MapStorage#getOrLoadData */
    public AnsmSavedData(String name) {
        super(name);
    }


    @Override
    public void readFromNBT(NBTTagCompound tag) {
        this.controllerNextChannel = tag.getInteger(CONTROLLER_CHANNEL_USAGE);

        controllerTiles.clear();
        controllerTiles.add(new TileEnergyNetworkController.TileFakeEnergyNetworkController());
        // When controllerNextChannel == 1, there's no channels got allocated
        // Which already gave the space for channel 0
        for(int i = 0; i < controllerNextChannel; i++) {
            this.controllerTiles.add(null);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setInteger(CONTROLLER_CHANNEL_USAGE, controllerNextChannel);

        return tag;
    }

}
