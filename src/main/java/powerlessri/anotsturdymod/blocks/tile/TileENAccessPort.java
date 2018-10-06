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
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.blocks.BlockEnergyController;
import powerlessri.anotsturdymod.handlers.init.RegistryHandler;
import powerlessri.anotsturdymod.library.utils.NBTUtils;
import powerlessri.anotsturdymod.network.ByteIOHelper;
import powerlessri.anotsturdymod.network.PacketServerCommand;
import powerlessri.anotsturdymod.network.datasync.PacketClientRequestedData;
import powerlessri.anotsturdymod.network.datasync.PacketSRequestWorld;

public class TileENAccessPort extends TileENComponentBase implements IEnergyStorage {

    /**
     * Reference to (supposedly) the only instance of BlockEnergyController. Exists for compat issues.
     */
    public static final BlockEnergyController CONTROLLER_BLOCK = BlockEnergyController.INSTANCE;

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

    public static void initNetwork() {
        PacketServerCommand.handlers.put(SET_CHANNEL, (msg, ctx) -> {
            int channelTo = msg.args.getInteger(TileENController.CHANNEL);

            World world = ByteIOHelper.getWorldFromDimension(msg.args);
            BlockPos tilePos = NBTUtils.readBlockPos(msg.args);
            TileENAccessPort tile = (TileENAccessPort) world.getTileEntity(tilePos);

            if (tile != null) {
                tile.setChannel(channelTo);
            }
        });

        PacketSRequestWorld.responses.put(GET_CHANNEL, (msg, ctx) -> {
            World world = DimensionManager.getWorld(msg.dimension);
            TileENAccessPort tile = (TileENAccessPort) world.getTileEntity(new BlockPos(msg.x, msg.y, msg.z));

            NBTTagCompound data = new NBTTagCompound();
            data.setInteger(TileENController.CHANNEL, tile.getChannel());

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
        tag.setInteger(TileENController.CHANNEL, channelTo);
        NBTUtils.writeBlockPos(tag, x, y, z);

        return tag;
    }

}