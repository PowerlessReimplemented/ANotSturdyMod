package powerlessri.anotsturdymod.mechanics.remote_enetwork.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.blocks.tile.base.TileEntityBase;
import powerlessri.anotsturdymod.handlers.init.RegistryHandler;
import powerlessri.anotsturdymod.network.PacketServerCommand;
import powerlessri.anotsturdymod.network.datasync.PacketClientRequestedData;
import powerlessri.anotsturdymod.network.datasync.PacketSRequestWorld;
import powerlessri.anotsturdymod.network.utils.ByteIOHelper;
import powerlessri.anotsturdymod.mechanics.remote_enetwork.IENetworkController;
import powerlessri.anotsturdymod.mechanics.remote_enetwork.data.ControllerNetworkData;
import powerlessri.anotsturdymod.varia.general.Utils;
import powerlessri.anotsturdymod.varia.tags.TagUtils;
import powerlessri.anotsturdymod.world.data.AnsmSavedData;

public class TileENComponentBase extends TileEntityBase {

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
        if (data == null) {
            data = AnsmSavedData.fromWorld(world).controllerEN;
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
                sendUpdates();
                return true;
            }

            this.channel = oldChannel;
        }
        return false;
    }


    public boolean isControllerValid() {
        return data.isChannelAllocated(channel);
    }

    public IENetworkController getController() {
        // This channel has been allocated
        if (isControllerValid()) {
            IENetworkController controller = data.controllers.get(channel);
            if (controller != null) {
                return controller;
            }
        }
        return data.FAKE_EN_CONTROLLER_TILE;
    }


    /**
     * Send as much energy as possible to the given IEnergyStorage.
     * Takes energy from the controller.
     *
     * @param target The IEnergyStorage that will be receiving energy
     */
    protected void sendEnergy(IEnergyStorage target) {
        IENetworkController controller = getController();
        int accepted = target.receiveEnergy(Math.min(ioLimit, (int) controller.getEnergyStored()), false);
        controller.extractEnergy(accepted, false);
    }


    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.channel = tag.getInteger(TileENController.CHANNEL);
        this.ioLimit = tag.getInteger(IO_LIMIT);
        this.ioUpgrades = tag.getInteger(IO_UPGRADES);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setInteger(TileENController.CHANNEL, channel);
        tag.setInteger(IO_LIMIT, ioLimit);
        tag.setInteger(IO_UPGRADES, ioUpgrades);

        return super.writeToNBT(tag);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound tag = super.getUpdateTag();
        return tag;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        super.handleUpdateTag(tag);
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
