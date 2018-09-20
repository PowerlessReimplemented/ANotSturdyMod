package powerlessri.anotsturdymod.world.handler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.energy.EnergyStorage;

public class LinkedEnergyStorage {
    
    
    private List<EnergyStorage> storages;
    private int currentChannel;
    
    private int defaultCapacity;
    private int defaultMaxIn;
    private int defaultMaxOut;
    
    
    public LinkedEnergyStorage() {
        this.storages = new ArrayList<>();
        this.currentChannel = 0;
        
        this.defaultCapacity = 1000000;
        this.defaultMaxIn = 5000;
        this.defaultMaxOut = 5000;
    }
    
    
    
    public int nextChannel() {
        this.storages.add(new EnergyStorage(defaultCapacity, defaultMaxIn, defaultMaxOut, 0));
        return currentChannel++;
    }
    
    
    @Nullable
    public EnergyStorage getStorage(int channel) {
        return this.getStorage(channel, false);
    }
    
    @Nullable
    public EnergyStorage getStorage(int channel, boolean checkExistance) {
        EnergyStorage storage = this.storages.get(channel);
        
        if(checkExistance && storage == null) {
            int newChannel = this.nextChannel();
            return this.storages.get(newChannel);
        }
        return storage;
    }
    
    public void setStorageTraits(int channel, int capacity, int maxReceive, int maxExtract) {
        this.storages.set(channel, new EnergyStorage(capacity, maxReceive, maxExtract, 0));
    }
    
    public boolean doesStorageExist(int channel) {
        return this.storages.get(channel) != null;
    }
    
    
    
    private static final String STORAGES = "energyStorages";
    
    private static final String STORED_ENERGY = "storedEnergy";
    private static final String CAPACITY = "storageCapacity";
    
    
    public void writeNBT(NBTTagCompound tag) {
        NBTTagList storages = new NBTTagList();
        tag.setTag(STORAGES, storages);
        
        for(int i = 0; i < this.storages.size(); i++) {
            EnergyStorage energyStorage = this.storages.get(i);
            NBTTagCompound energyData = new NBTTagCompound();
            
            energyData.setInteger(STORED_ENERGY, energyStorage.getEnergyStored());
            energyData.setInteger(CAPACITY, energyStorage.getMaxEnergyStored());
            
            storages.appendTag(energyData);
        }
    }
    
    public void readNBT(NBTTagCompound tag) {
        NBTTagList storages = tag.getTagList(STORAGES, Constants.NBT.TAG_COMPOUND);
        
        // Get a new list, this method meant to wipe out everything
        this.storages = new ArrayList<>();
        this.currentChannel = storages.tagCount() - 1;
        
        for(int i = 0; i < storages.tagCount(); i++) {
            NBTTagCompound energyData = storages.getCompoundTagAt(i);
            EnergyStorage energyStorage = new EnergyStorage(
                    energyData.getInteger(CAPACITY),
                    this.defaultMaxIn,
                    this.defaultMaxOut,
                    energyData.getInteger(STORED_ENERGY));
            
            this.storages.add(energyStorage);
        }
    }
    
}
