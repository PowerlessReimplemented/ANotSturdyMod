package powerlessri.anotsturdymod.tile;

import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import powerlessri.anotsturdymod.library.utils.Reference.EEnergyAccessState;
import powerlessri.anotsturdymod.tile.base.TileEntityBase;

public class TileEnergyStorage extends TileEntityBase implements ICapabilityProvider {
    
    final EnergyStorage storage;
    
    EEnergyAccessState upState;
    EEnergyAccessState downState;
    EEnergyAccessState northState;
    EEnergyAccessState southState;
    EEnergyAccessState eastState;
    EEnergyAccessState westState;
    
    
    public TileEnergyStorage() {
        this(1000000, 5000, 5000);
    }
    
    public TileEnergyStorage(int capacity, int maxReceive, int maxSend) {
        this(new EnergyStorage(capacity, maxReceive, maxSend, 0));
    }
    
    public TileEnergyStorage(EnergyStorage storage) {
        this.storage = storage;
    }
    
    
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return this.hasCapability(capability, facing, true);
    }
    
    public boolean hasCapability(Capability<?> capability, EnumFacing facing, boolean ignoreFacing) {
        // If ignoreFacing, (ignoreFacing || this.isFaceEnabled(facing))
        // returns true immediately, which is equivalent to the expression doesn't exist
        if(CapabilityEnergy.ENERGY == capability && 
                (ignoreFacing || this.isFaceEnabled(facing))) {
            return true;
        }
        
        return super.hasCapability(capability, facing);
    }
    
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(this.hasCapability(capability, facing, false)) {
            CapabilityEnergy.ENERGY.cast(this.storage);
        }
        
        return super.getCapability(capability, facing);

    }
    
    
    
    private boolean isFaceEnabled(EnumFacing facing) {
        return this.isFaceInput(facing) || this.isFaceOutput(facing);
    }
    
    private boolean isFaceInput(EnumFacing facing) {
        EEnergyAccessState state = this.getFaceState(facing);
        return state == EEnergyAccessState.INPUT || state == EEnergyAccessState.BOTH;
    }
    
    private boolean isFaceOutput(EnumFacing facing) {
        EEnergyAccessState state = this.getFaceState(facing);
        return state == EEnergyAccessState.OUTPUT || state == EEnergyAccessState.BOTH;
    }
    
    
    private EEnergyAccessState getFaceState(EnumFacing facing) {
        switch(facing) {
            case UP: return this.upState;
            case DOWN: return this.downState;
            case NORTH: return this.northState;
            case SOUTH: return this.westState;
            case EAST: return this.eastState;
            case WEST: return this.westState;
        }
        
        return EEnergyAccessState.BLOCKED;
    }
    
    

}
