package powerlessri.anotsturdymod.tile.energy;

import java.util.function.BiFunction;

import net.minecraftforge.energy.IEnergyStorage;

public class RedirectedEnergyStorage implements IEnergyStorage {
    
    private final BiFunction<Integer, Boolean, Integer> onReceive;
    private final BiFunction<Integer, Boolean, Integer> onExtract;
    
    public RedirectedEnergyStorage(BiFunction<Integer, Boolean, Integer> onReceive, BiFunction<Integer, Boolean, Integer> onExtract) {
        this.onReceive = onReceive;
        this.onExtract = onExtract;
    }
    

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return this.onReceive.apply(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return this.onExtract.apply(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored() {
        return 0;
    }

    @Override
    public int getMaxEnergyStored() {
        return 0;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return false;
    }

}
