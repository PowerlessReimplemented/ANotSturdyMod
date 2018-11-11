package powerlessri.anotsturdymod.world.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import powerlessri.anotsturdymod.blocks.remoteenetwork.data.ControllerNetworkData;
import powerlessri.anotsturdymod.varia.Reference;

public class AnsmSavedData extends WorldSavedData {

    public static AnsmSavedData fromWorld(World world) {
        MapStorage storage = world.getMapStorage();
        AnsmSavedData result = (AnsmSavedData) storage.getOrLoadData(AnsmSavedData.class, DATA_NAME);

        if (result == null) {
            result = new AnsmSavedData(DATA_NAME);
            result.constructRuntimeData();
            storage.setData(DATA_NAME, result);
        }

        return result;
    }


    public static final String DATA_NAME = Reference.MODID + "_GeneralData";


    public ControllerNetworkData controllerEN;

    public AnsmSavedData(String name) {
        super(name);
        controllerEN = new ControllerNetworkData(this);
    }


    public void constructRuntimeData() {
        controllerEN.init();
    }


    @Override
    public void readFromNBT(NBTTagCompound tag) {
        controllerEN.deserializeParentNBT(tag);

        constructRuntimeData();
    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        controllerEN.serializeParentNBT(tag);

        return tag;
    }

}
