package powerlessri.anotsturdymod.world;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import powerlessri.anotsturdymod.library.utils.Reference;
import powerlessri.anotsturdymod.world.handler.LinkedEnergyStorage;

public class AnsmSaveData extends WorldSavedData {
    
    public static AnsmSaveData fromWorld(World world) {
        MapStorage storage = world.getMapStorage();
        AnsmSaveData result = (AnsmSaveData) storage.getOrLoadData(AnsmSaveData.class, Reference.MODID);

        if(result == null) {
            result = new AnsmSaveData();
            storage.setData(result.mapName, result);
        }

        return result;
    }
    

    public static final String CONTROLLER_CHANNEL_USAGE = "cntrllrNextChnnl";
    public static final String SHARED_ENERGY_NETWORK = "sharedEngStorage";

    public int controllerNextChannel;
    public List<LinkedEnergyStorage> linkedEnergyNet;


    public AnsmSaveData() {
        super(Reference.MODID);
        
        this.linkedEnergyNet = new ArrayList<>();
        this.linkedEnergyNet.add(new LinkedEnergyStorage());
    }


    @Override
    public void readFromNBT(NBTTagCompound tag) {
        this.controllerNextChannel = tag.getInteger(CONTROLLER_CHANNEL_USAGE);

        this.linkedEnergyNet.clear();
        NBTTagList nbtLinkedEN = tag.getTagList(SHARED_ENERGY_NETWORK, Constants.NBT.TAG_COMPOUND);
        for(int i = 0; i < nbtLinkedEN.tagCount(); i++) {
            LinkedEnergyStorage storage = new LinkedEnergyStorage();
            storage.readNBT(nbtLinkedEN.getCompoundTagAt(i));
            this.linkedEnergyNet.set(i, storage);
        }
        
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setInteger(CONTROLLER_CHANNEL_USAGE, controllerNextChannel);
        
        NBTTagList nbtLinkedEN = new NBTTagList();
        for(int i = 0; i < this.linkedEnergyNet.size(); i++) {
            NBTTagCompound storage = new NBTTagCompound();
            this.linkedEnergyNet.get(i).writeNBT(storage);
            nbtLinkedEN.appendTag(storage);
        }
            

        return tag;
    }

}
