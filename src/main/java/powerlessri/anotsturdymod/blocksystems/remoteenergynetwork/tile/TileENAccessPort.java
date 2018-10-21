package powerlessri.anotsturdymod.blocksystems.remoteenergynetwork.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.handlers.init.RegistryHandler;
import powerlessri.anotsturdymod.library.tags.TagUtils;
import powerlessri.anotsturdymod.library.Utils;
import powerlessri.anotsturdymod.network.PacketServerCommand;
import powerlessri.anotsturdymod.network.datasync.PacketClientRequestedData;
import powerlessri.anotsturdymod.network.datasync.PacketSRequestWorld;
import powerlessri.anotsturdymod.network.utils.ByteIOHelper;

import javax.annotation.Nullable;

public class TileENAccessPort extends TileENComponentBase implements ICapabilityProvider, IEnergyStorage {

    public static final String TILE_REGISTRY_NAME = RegistryHandler.makeTileEntityID("energy_network_access_port");


    public TileENAccessPort() {
    }

    public TileENAccessPort(int channel, int ioLimit) {
        super(channel, ioLimit);
    }


    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (CapabilityEnergy.ENERGY == capability) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (CapabilityEnergy.ENERGY == capability) {
            return CapabilityEnergy.ENERGY.cast(this);
        }
        return super.getCapability(capability, facing);
    }


    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        TileENController controller = getController();
        if (this.canReceive()) {

            int accepted = Math.min((int) controller.getCapacityLeft(), maxReceive);
            if (!simulate)
                controller.energyStored += accepted;

            return accepted;
        }
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        TileENController controller = getController();
        if (this.canExtract()) {

            int removed = Math.min((int) controller.energyStored, maxExtract);
            if (!simulate)
                controller.energyStored -= removed;

            return removed;
        }
        return 0;
    }

    @Override
    public int getEnergyStored() {
        return (int) this.getController().energyStored;
    }

    @Override
    public int getMaxEnergyStored() {
        return (int) this.getController().getCapacity();
    }


    @Override
    public boolean canReceive() {
        return true;
    }

    @Override
    public boolean canExtract() {
        return false;
    }


    // ======== Networking ======== //

    public static final String GET_CHANNEL = TILE_REGISTRY_NAME + ":sync.getChannel";
    public static final String SET_CHANNEL = TILE_REGISTRY_NAME + ":setChannel";

    public static void initNetwork() {
        PacketServerCommand.handlers.put(SET_CHANNEL, (msg, ctx) -> {
            onPacketSetChannel(msg, ctx);
        });

        PacketSRequestWorld.responses.put(GET_CHANNEL, (msg, ctx) -> {
            onPacketCGetChannel(msg, ctx);
            return null;
        });
    }


    public static void onPacketSetChannel(PacketServerCommand pckt, MessageContext ctx) {
        int channelTo = pckt.args.getInteger(TileENController.CHANNEL);

        World world = ByteIOHelper.getWorldFromDimension(pckt.args);
        BlockPos tilePos = TagUtils.readBlockPos(pckt.args);
        TileENComponentBase tile = (TileENComponentBase) world.getTileEntity(tilePos);

        if (tile != null) {
            tile.setChannel(channelTo);
        } else {
            Utils.getLogger().info("The given position " + tilePos.toString() + " does not contain a TileENComponentBase");
        }
    }

    public static void onPacketCGetChannel(PacketSRequestWorld pckt, MessageContext ctx) {
        World world = DimensionManager.getWorld(pckt.dimension);
        BlockPos tilePos = new BlockPos(pckt.x, pckt.y, pckt.z);
        TileENComponentBase tile = (TileENComponentBase) world.getTileEntity(tilePos);

        NBTTagCompound data = new NBTTagCompound();

        data.setInteger(TileENController.CHANNEL, tile.getChannel());

        PacketClientRequestedData response = new PacketClientRequestedData(pckt.requestId, data);
        ANotSturdyMod.genericChannel.sendToAll(response);
    }


    public static NBTTagCompound makeSetChannelArgs(int dimension, int x, int y, int z, int channelTo) {
        NBTTagCompound tag = PacketServerCommand.makeWorldPosArgs(dimension, x, y, z);

        tag.setInteger(TileENController.CHANNEL, channelTo);

        return tag;
    }

}
