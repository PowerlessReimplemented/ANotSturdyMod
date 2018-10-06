package powerlessri.anotsturdymod.blocks.tile;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.blocks.BlockEnergyController;
import powerlessri.anotsturdymod.handlers.init.RegistryHandler;
import powerlessri.anotsturdymod.library.utils.NBTUtils;
import powerlessri.anotsturdymod.network.ByteIOHelper;
import powerlessri.anotsturdymod.library.utils.Utils;
import powerlessri.anotsturdymod.network.PacketServerCommand;
import powerlessri.anotsturdymod.network.datasync.PacketClientRequestedData;
import powerlessri.anotsturdymod.network.datasync.PacketSRequestWorld;

public class TileENAccessPort extends TileENComponentBase implements IEnergyStorage {

    /**
     * Reference to (supposedly) the only instance of BlockEnergyController. Exists for compat issues.
     */
    public static final BlockEnergyController CONTROLLER_BLOCK = BlockEnergyController.INSTANCE;

    public static final String TILE_REGISTRY_NAME = RegistryHandler.makeTileEntityID("energy_network_access_port");

    // Tags
    public static final String IO_LIMIT = "ioLm";
    public static final String IO_UPGRADES = "ioUpgs";


    protected AnsmSavedData data;

    protected int channel;
    protected int ioLimit;


    public TileEnergyNetworkAccessPort() {
    }

    public TileEnergyNetworkAccessPort(int channel, int ioLimit) {
        this.channel = channel;
        this.ioLimit = ioLimit;
    }


    public int getChannel() {
        return this.channel;
    }

    /**
     * @return {@code true} for success. <br /> {@code false} for fail.
     */
    public boolean setChannel(int channel) {
        if (channel > 0) {
            int oldChannel = this.channel;
            this.channel = channel;

            if (isControllerValid()) {
                return true;
            }

            this.channel = oldChannel;
        }
        return false;
    }



    public boolean isControllerValid() {
        return channel < data.controllerTiles.size();
    }

    public TileEnergyNetworkController getController() {
        if (isControllerValid()) {
            return data.controllerTiles.get(this.channel);
        }

        return data.FAKE_EN_CONTROLLER_TILE;
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
        TileENController controller = this.getController();
        if (this.canReceive() && controller != null) {

            int accepted = Math.min((int) controller.getCapacityLeft(), maxReceive);
            if (!simulate)
                controller.energyStored += accepted;

            return accepted;
        }
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        TileENController controller = this.getController();
        if (this.canExtract() && controller != null) {

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
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return true;
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
        int channelTo = pckt.args.getInteger(TileEnergyNetworkController.CHANNEL);

        World world = DimensionManager.getWorld(NBTUtils.readDimension(pckt.args));
        BlockPos tilePos = NBTUtils.readBlockPos(pckt.args);
        TileEnergyNetworkAccessPort tile = (TileEnergyNetworkAccessPort) world.getTileEntity(tilePos);

        if (tile != null) {
            Utils.getLogger().info("trying set channel to " + channelTo);
            tile.setChannel(channelTo);
            Utils.getLogger().info("channel now " + tile.getChannel());
        }
    }

    public static void onPacketCGetChannel(PacketSRequestWorld pckt, MessageContext ctx) {
        World world = DimensionManager.getWorld(pckt.dimension);
        TileEnergyNetworkAccessPort tile = (TileEnergyNetworkAccessPort) world.getTileEntity(new BlockPos(pckt.x, pckt.y, pckt.z));

        NBTTagCompound data = new NBTTagCompound();
        data.setInteger(TileEnergyNetworkController.CHANNEL, tile.getChannel());

        PacketClientRequestedData response = new PacketClientRequestedData(pckt.requestId, data);
        ANotSturdyMod.genericChannel.sendToAll(response);
    }



    // ======== Message Argument Constructors ======== //

    public static NBTTagCompound makeSetChannelArgs(int dimension, int x, int y, int z, int channelTo) {
        NBTTagCompound tag = PacketServerCommand.makeWorldPosArgs(dimension, x, y, z);

        tag.setInteger(TileENController.CHANNEL, channelTo);

        return tag;
    }

}
