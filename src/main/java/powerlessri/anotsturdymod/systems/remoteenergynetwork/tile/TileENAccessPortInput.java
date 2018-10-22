package powerlessri.anotsturdymod.systems.remoteenergynetwork.tile;

import powerlessri.anotsturdymod.handlers.init.RegistryHandler;

public class TileENAccessPortInput extends TileENAccessPort {

    public static final String TILE_REGISTRY_NAME = RegistryHandler.makeTileEntityID("energy_network_input");


    public TileENAccessPortInput() {
    }
    
    public TileENAccessPortInput(int channel, int ioLimit) {
        super(channel, ioLimit);
    }


    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return super.receiveEnergy(Math.min(ioLimit, maxReceive), simulate);
    }


    @Override
    public boolean canReceive() {
        return true;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

}
