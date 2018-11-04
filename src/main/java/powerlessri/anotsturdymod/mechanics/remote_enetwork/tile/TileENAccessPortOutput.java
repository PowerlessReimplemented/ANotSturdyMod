package powerlessri.anotsturdymod.mechanics.remote_enetwork.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import powerlessri.anotsturdymod.handlers.init.RegistryHandler;

public class TileENAccessPortOutput extends TileENAccessPort implements ITickable {

    public static final String TILE_REGISTRY_NAME = RegistryHandler.makeTileEntityID("energy_network_output");


    public TileENAccessPortOutput() {
    }

    public TileENAccessPortOutput(int channel, int ioLimit) {
        super(channel, ioLimit);
    }


    @Override
    public void update() {
        if (world.isRemote) {
            return;
        }

        for (EnumFacing facing : EnumFacing.VALUES) {
            BlockPos neighborPos = pos.offset(facing);
            TileEntity tile = world.getTileEntity(neighborPos);
            EnumFacing opposite = facing.getOpposite();

            // Don't insert to another access port, it might cause problems (e.g. loop)
            if (tile != null && !(tile instanceof TileENAccessPort)) {
                if (tile.hasCapability(CapabilityEnergy.ENERGY, opposite)) {
                    IEnergyStorage storage = tile.getCapability(CapabilityEnergy.ENERGY, opposite);

                    if (storage.canReceive()) {
                        sendEnergy(storage);
                    }
                }
            }
        }
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return false;
    }

}
