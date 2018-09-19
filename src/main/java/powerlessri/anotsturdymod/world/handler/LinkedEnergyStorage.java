package powerlessri.anotsturdymod.world.handler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

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
        int channel = this.currentChannel;
        this.currentChannel++;
        this.storages.set(channel, new EnergyStorage(defaultCapacity, defaultMaxIn, defaultMaxOut, 0));
        return channel;
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
    
}
