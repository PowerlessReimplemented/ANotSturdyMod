package powerlessri.anotsturdymod.blocks.remoteenetwork.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powerlessri.anotsturdymod.blocks.remoteenetwork.IENetworkController;
import powerlessri.anotsturdymod.blocks.remoteenetwork.data.ControllerNetworkData;
import powerlessri.anotsturdymod.handlers.init.RegistryHandler;
import powerlessri.anotsturdymod.library.tile.base.TileEntityBase;
import powerlessri.anotsturdymod.network.PacketServerCommand;
import powerlessri.anotsturdymod.network.utils.ByteIOHelper;
import powerlessri.anotsturdymod.varia.general.Utils;
import powerlessri.anotsturdymod.varia.tags.TagUtils;
import powerlessri.anotsturdymod.world.data.AnsmSavedData;

public abstract class TileENComponentBase extends TileEntityBase {

    public static final String TILE_REGISTRY_NAME = RegistryHandler.makeTileEntityID("energy_network_access_port");

    // NBT tag keys
    public static final String IO_LIMIT = "ioLm";
    public static final String IO_UPGRADES = "ioUpgs";


    protected ControllerNetworkData data;

    protected int channel;
    protected int ioLimit;
    protected int ioUpgrades;


    public TileENComponentBase() {
    }

    public TileENComponentBase(int channel, int ioLimit) {
        this.channel = channel;
        this.ioLimit = Math.max(0, ioLimit);
    }


    @Override
    public void onLoadServer() {
        if (this.data == null) {
            this.data = AnsmSavedData.fromWorld(world).controllerEN;
        }
    }


    public int getChannel() {
        return this.channel;
    }

    /**
     * @return {@code true} for success. <br /> {@code false} for fail.
     */
    public boolean setChannel(int newChannel) {
        if (newChannel > 0) {
            int oldChannel = channel;
            this.channel = newChannel;

            if (isControllerValid()) {
                this.sendUpdates();
                this.markDirty();
                return true;
            } else {
                this.channel = oldChannel;
            }
        }
        return false;
    }


    public boolean isControllerValid() {
        return this.data.isChannelAllocated(channel);
    }

    public IENetworkController getController() {
        // This network has been allocated
        if (isControllerValid()) {
            IENetworkController controller = data.controllers.get(channel);
            if (controller != null) {
                return controller;
            }
        }
        return this.data.FAKE_EN_CONTROLLER_TILE;
    }


    /**
     * Send as much energy as possible to the given IEnergyStorage.
     * Takes energy from the controller.
     *
     * @param target The IEnergyStorage that will be receiving energy
     */
    protected void sendEnergy(IEnergyStorage target) {
        IENetworkController controller = this.getController();
        int accepted = target.receiveEnergy(Math.min(ioLimit, (int) controller.getEnergyStored()), false);
        controller.extractEnergy(accepted, false);
    }


    @Override
    public void restoreFromNBT(NBTTagCompound tag) {
        this.channel = tag.getInteger("channel");
        this.ioLimit = tag.getInteger(IO_LIMIT);
        this.ioUpgrades = tag.getInteger(IO_UPGRADES);
    }

    @Override
    public void writeRestorableNBT(NBTTagCompound tag) {
        tag.setInteger("channel", channel);
        tag.setInteger(IO_LIMIT, ioLimit);
        tag.setInteger(IO_UPGRADES, ioUpgrades);
    }


    // ======== Networking ======== //


    public static final String SET_CHANNEL = TILE_REGISTRY_NAME + ":setChannel";

    public static void initNetwork() {
        PacketServerCommand.handlers.put(SET_CHANNEL, (msg, ctx) -> {
            onPacketSetChannel(msg, ctx);
        });
    }


    public static void onPacketSetChannel(PacketServerCommand pckt, MessageContext ctx) {
        int channelTo = pckt.args.getInteger("storageChannel");

        World world = ByteIOHelper.getWorldFromDimension(pckt.args);
        BlockPos tilePos = TagUtils.readBlockPos(pckt.args);
        TileENComponentBase tile = (TileENComponentBase) world.getTileEntity(tilePos);

        if (tile != null) {
            tile.setChannel(channelTo);
        } else {
            Utils.getLogger().info("The given position " + tilePos.toString() + " does not contain a TileENComponentBase");
        }
    }


    public static NBTTagCompound makeSetChannelArgs(int dimension, int x, int y, int z, int channelTo) {
        NBTTagCompound tag = PacketServerCommand.makeWorldPosArgs(dimension, x, y, z);

        tag.setInteger("storageChannel", channelTo);

        return tag;
    }

}
