package powerlessri.anotsturdymod.blocks.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.blocks.BlockEnergyController;
import powerlessri.anotsturdymod.blocks.tile.base.TileEntityBase;
import powerlessri.anotsturdymod.handlers.init.RegistryHandler;
import powerlessri.anotsturdymod.library.utils.NBTUtils;
import powerlessri.anotsturdymod.network.PacketServerCommand;
import powerlessri.anotsturdymod.network.datasync.PacketClientRequestedData;
import powerlessri.anotsturdymod.network.datasync.PacketSRequestWorld;
import powerlessri.anotsturdymod.world.AnsmSavedData;

import javax.annotation.Nullable;

public class TileEnergyNetworkAccessPort extends TileEntityBase implements IEnergyStorage {

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

            if (getController() != null) {
                return true;
            }

            this.channel = oldChannel;
        }
        return false;
    }


    // Note: even though this method might return null, TileEnergyNetworkAccessPort#setChannel will ensure it won't happen by ensuring the input channel exists.
    // Except: started with a channel don't even exist.
    @Nullable
    public TileEnergyNetworkController getController() {
        // This channel has been allocated
        if (channel < data.controllerTiles.size()) {
            return data.controllerTiles.get(this.channel);
        }

        return null;
    }

    @Override
    public void onLoadServer() {
        if (data == null) {
            data = AnsmSavedData.fromWorld(getWorld());
        }
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
        TileEnergyNetworkController controller = this.getController();
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
        TileEnergyNetworkController controller = this.getController();
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


    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.channel = tag.getInteger(TileEnergyNetworkController.CHANNEL);
        this.ioLimit = tag.getInteger(IO_LIMIT);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setInteger(TileEnergyNetworkController.CHANNEL, channel);
        tag.setInteger(IO_LIMIT, ioLimit);

        return super.writeToNBT(tag);
    }


    // ======== Networking ======== //

    public static void initNetwork() {
        PacketServerCommand.handlers.put(SET_CHANNEL, (msg, ctx) -> {
            int channelTo = msg.args.getInteger(TileEnergyNetworkController.CHANNEL);

            World world = DimensionManager.getWorld(msg.args.getInteger(NBTUtils.DIMENSION));
            BlockPos tilePos = NBTUtils.readBlockPos(msg.args);
            TileEnergyNetworkAccessPort tile = (TileEnergyNetworkAccessPort) world.getTileEntity(tilePos);

            if (tile != null) {
                tile.setChannel(channelTo);
            }
        });

        PacketSRequestWorld.responses.put(GET_CHANNEL, (msg, ctx) -> {
            World world = DimensionManager.getWorld(msg.dimension);
            TileEnergyNetworkAccessPort tile = (TileEnergyNetworkAccessPort) world.getTileEntity(new BlockPos(msg.x, msg.y, msg.z));

            NBTTagCompound data = new NBTTagCompound();
            data.setInteger(TileEnergyNetworkController.CHANNEL, tile.getChannel());

            PacketClientRequestedData response = new PacketClientRequestedData(msg.requestId, data);
            ANotSturdyMod.genericChannel.sendToAll(response);

            return null;
        });
    }


    public static final String GET_CHANNEL = TILE_REGISTRY_NAME + ":sync.getChannel";
    public static final String SET_CHANNEL = TILE_REGISTRY_NAME + ":setChannel";

    public static NBTTagCompound makeSetChannelArgs(int dimension, int x, int y, int z, int channelTo) {
        NBTTagCompound tag = new NBTTagCompound();

        tag.setInteger(NBTUtils.DIMENSION, dimension);
        tag.setInteger(TileEnergyNetworkController.CHANNEL, channelTo);
        NBTUtils.writeBlockPos(tag, x, y, z);

        return tag;
    }

}