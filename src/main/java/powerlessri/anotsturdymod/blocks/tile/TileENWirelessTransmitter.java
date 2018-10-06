package powerlessri.anotsturdymod.blocks.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.ArrayList;
import java.util.List;

public class TileENWirelessTransmitter extends TileENComponentBase implements ITickable {

    private final int maxOutput = 80;

    private List<BlockPos> nearbyTiles = new ArrayList<>();



    public void scanNearbyTiles(int radius) {
        nearbyTiles.clear();

        // i, j, k are the offset from origin (this.pos)
        for(int i = -radius; i <= radius; i++) {
            for(int j = -radius; j <= radius; j++) {
                for(int k = -radius; k <= radius; k++) {
                    BlockPos targetPos = pos.add(i, j, k);
                    TileEntity tile = world.getTileEntity(targetPos);

                    if (tile != null && tile.hasCapability(CapabilityEnergy.ENERGY, null)) {
                        nearbyTiles.add(targetPos);
                    }
                }
            }
        }
    }

    @Override
    public void update() {
        TileENController controller = getController();
        if(controller == null || controller.energyStored == 0L) {
            return;
        }

        for (BlockPos p : nearbyTiles) {
            TileEntity tile = world.getTileEntity(p);

            // Tile entities might change after scanning
            if(tile != null) {
                IEnergyStorage storage = tile.getCapability(CapabilityEnergy.ENERGY, null);

                if (storage != null && storage.canReceive()) {
                    int accepted = storage.receiveEnergy(Math.min(maxOutput, (int) controller.energyStored), false);
                    controller.energyStored -= accepted;
                }

                if(controller.energyStored == 0L) {
                    return;
                }
            }
        }
    }

}
