package powerlessri.anotsturdymod.blocks.remoteenetwork.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import powerlessri.anotsturdymod.handlers.init.RegistryHandler;
import powerlessri.anotsturdymod.network.PacketServerCommand;
import powerlessri.anotsturdymod.network.utils.ByteIOHelper;
import powerlessri.anotsturdymod.blocks.remoteenetwork.IENetworkController;
import powerlessri.anotsturdymod.varia.tags.TagUtils;

import java.util.ArrayList;
import java.util.List;

public class TileENWirelessTransmitter extends TileENComponentBase implements ITickable {

    public static final String TILE_REGISTRY_NAME = RegistryHandler.makeTileEntityID("energy_network_wireless_transmitter");

    public static final int DEFAULT_SCAN_RADIUS = 4;
    public static final String AMOUNT_POWER_RECEIVERS = "NPowerReceivers";


    private List<BlockPos> nearbyTiles = new ArrayList<>();
    /**
     * Equivalent to nearbyTiles.size()
     */
    public int amountPowerReceivers = 0;

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
        amountPowerReceivers = 0;

        // i, j, k are the offset from origin (this.pos)
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                for (int k = -radius; k <= radius; k++) {
                    BlockPos targetPos = pos.add(i, j, k);
                    TileEntity tile = world.getTileEntity(targetPos);

                    // Don't input energy into components, that might cause problems (e.g. infinite loop)
                    if (tile != null && !(tile instanceof TileENComponentBase) && tile.hasCapability(CapabilityEnergy.ENERGY, null)) {
                        nearbyTiles.add(targetPos);
                        amountPowerReceivers++;
                    }
                }
            }
        }

        // Sync amountPowerReceivers to client
        sendUpdates();
    }

    @Override
    public void update() {
        if (amountPowerReceivers == 0 || world.isRemote) {
            return;
        }

        IENetworkController controller = getController();
        if (controller == null) {
            return;
        }

        for (BlockPos pos : nearbyTiles) {
            TileEntity tile = world.getTileEntity(pos);

            // Tile entities might change after scanning
            if (tile != null) {
                IEnergyStorage storage = tile.getCapability(CapabilityEnergy.ENERGY, null);

                if (storage != null && storage.canReceive()) {
                    sendEnergy(storage);
                    if (controller.getEnergyStored() == 0L) {
                        return;
                    }
                }
            }
        }
    }


    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound tag = super.getUpdateTag();

        tag.setInteger(AMOUNT_POWER_RECEIVERS, amountPowerReceivers);

        return tag;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        super.handleUpdateTag(tag);
        amountPowerReceivers = tag.getInteger(AMOUNT_POWER_RECEIVERS);
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
        BlockPos tilePos = TagUtils.readBlockPos(pckt.args);
        TileENWirelessTransmitter tile = (TileENWirelessTransmitter) world.getTileEntity(tilePos);

        tile.scanNearbyTiles(DEFAULT_SCAN_RADIUS);
    }

}
