package powerlessri.anotsturdymod.blocks.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEnergyNetworkOutput extends TileEnergyNetworkAccessPort implements ITickable {

    public TileEnergyNetworkOutput() {
    }

    public TileEnergyNetworkOutput(int channel, int ioLimit) {
        super(channel, ioLimit);
    }

    @Override
    public void update() {
        if(!getWorld().isRemote) {
            for (EnumFacing facing : EnumFacing.VALUES) {
                BlockPos neighborPos = getPos().offset(facing);
                TileEntity tile = getWorld().getTileEntity(neighborPos);
                EnumFacing opposite = facing.getOpposite();

                // Don't insert to another access port, it might cause problems (e.g. loop)
                if (tile != null && !(tile instanceof TileEnergyNetworkAccessPort)) {
                    if (tile.hasCapability(CapabilityEnergy.ENERGY, opposite)) {
                        IEnergyStorage storage = tile.getCapability(CapabilityEnergy.ENERGY, opposite);

                        if (storage.canReceive()) {
                            sendEnergy(storage);
                        }
                    }
                }
            }
        }
    }

    public void sendEnergy(IEnergyStorage targetStorage) {
        TileEnergyNetworkController controller = getController();
        if(controller != null) {
            int extractLimit = extractEnergy(ioLimit, true);
            int accepted = targetStorage.receiveEnergy(extractLimit, false);
            controller.energyStored -= accepted;
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
