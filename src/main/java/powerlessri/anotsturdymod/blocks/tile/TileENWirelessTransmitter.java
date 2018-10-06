package powerlessri.anotsturdymod.blocks.tile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import powerlessri.anotsturdymod.handlers.init.RegistryHandler;
import powerlessri.anotsturdymod.library.utils.NBTUtils;
import powerlessri.anotsturdymod.network.ByteIOHelper;
import powerlessri.anotsturdymod.network.PacketServerCommand;

public class TileENWirelessTransmitter extends TileENComponentBase implements ITickable {
    
    public static final String TILE_REGISTRY_NAME = RegistryHandler.makeTileEntityID("energy_network_wireless_transmitter");
    
    public static final int DEFAULT_SCAN_RADIUS = 4;
    
    

    private List<BlockPos> nearbyTiles = new ArrayList<>();
    
    public TileENWirelessTransmitter() {
    }
    
    public TileENWirelessTransmitter(int channel, int ioLimit) {
        super(channel, ioLimit);
    }



    @Override
    public void onLoadServer() {
        super.onLoadServer();
        scanNearbyTiles(DEFAULT_SCAN_RADIUS);
    }
    
    public void scanNearbyTiles(int radius) {
        nearbyTiles.clear();

        // i, j, k are the offset from origin (this.pos)
        for(int i = -radius; i <= radius; i++) {
            for(int j = -radius; j <= radius; j++) {
                for(int k = -radius; k <= radius; k++) {
                    BlockPos targetPos = pos.add(i, j, k);
                    TileEntity tile = world.getTileEntity(targetPos);

                    // Don't input energy into components, that might cause problems (e.g. infinite loop)
                    if (tile != null && !(tile instanceof TileENComponentBase) && tile.hasCapability(CapabilityEnergy.ENERGY, null)) {
                        nearbyTiles.add(targetPos);
                    }
                }
            }
        }
    }

    @Override
    public void update() {
        if(nearbyTiles.size() == 0) {
            return;
        }
        
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
                    int accepted = storage.receiveEnergy(Math.min(ioLimit, (int) controller.energyStored), false);
                    controller.energyStored -= accepted;
                }

                if(controller.energyStored == 0L) {
                    return;
                }
            }
        }
    }
    
    
    
    // ======== Networking ======== //
    
    public static final String SCAN_NEARBY_TILES = TILE_REGISTRY_NAME + ":scanTe";
    
    public static void initNetwork() {
        PacketServerCommand.handlers.put(SCAN_NEARBY_TILES, (msg, ctx) -> {
            onPacketScanNearbyTiles(msg, ctx);
        });
    }
    
    
    public static void onPacketScanNearbyTiles(PacketServerCommand pckt, MessageContext ctx) {
        World world = ByteIOHelper.getWorldFromDimension(pckt.args);
        BlockPos tilePos = NBTUtils.readBlockPos(pckt.args);
        TileENWirelessTransmitter tile = (TileENWirelessTransmitter) world.getTileEntity(tilePos);
        
        tile.scanNearbyTiles(DEFAULT_SCAN_RADIUS);
    }

}
