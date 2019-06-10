package powerlessri.anotsturdymod.library.network.notification;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import powerlessri.anotsturdymod.varia.general.Utils;

import java.io.IOException;

/**
 * <p>Sender: <b>CLIENT</b></p>
 * <p>Receiver: <b>SERVER</b></p>
 */
public class PacketNotification implements IMessage {

    private int dimension;
    private ENotificationType type;

    private NBTTagCompound data;

    public PacketNotification() {
    }

    public PacketNotification(int dimension, ENotificationType type, NBTTagCompound data) {
        this.dimension = dimension;
        this.type = type;
        this.data = data;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer pkt = new PacketBuffer(buf);
        try {
            this.dimension = pkt.readInt();
            this.type = pkt.readEnumValue(ENotificationType.class);
            this.data = pkt.readCompoundTag();
        } catch (IOException e) {
            Utils.getLogger().error(e);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer pkt = new PacketBuffer(buf);
        pkt.writeInt(dimension);
        pkt.writeEnumValue(type);
        pkt.writeCompoundTag(data);
    }

    public static class Handler implements IMessageHandler<PacketNotification, IMessage> {


        @Override
        public IMessage onMessage(PacketNotification pkt, MessageContext ctx) {
            Runnable task = this.getTask(pkt, ctx);
            FMLCommonHandler.instance().getWorldThread(ctx.getServerHandler()).addScheduledTask(task);
            return null;
        }

        public Runnable getTask(PacketNotification pkt, MessageContext ctx) {
            World world = DimensionManager.getWorld(pkt.dimension);
            NetworkManager net = ctx.getServerHandler().getNetworkManager();
            switch (pkt.type) {
                case ENTITY:
                    return () -> notifyEntity(world, net, pkt);
                case TILE_ENTITY:
                    return () -> notifyTileEntity(world, net, pkt);
            }
            return () -> {
            };
        }


        private void notifyEntity(World world, NetworkManager net, PacketNotification pkt) {
            int id = pkt.data.getInteger("id");
            Entity entity = world.getEntityByID(id);
            if (entity != null) {
                if (entity instanceof INotificationReceiver) {
                    ((INotificationReceiver) entity).handleNotificationPacket(net, pkt);
                }
            }
        }

        private void notifyTileEntity(World world, NetworkManager net, PacketNotification pkt) {
            BlockPos pos = NBTUtil.getPosFromTag(pkt.data);
            TileEntity tile = world.getTileEntity(pos);
            if (tile != null) {
                if (tile instanceof INotificationReceiver) {
                    ((INotificationReceiver) tile).handleNotificationPacket(net, pkt);
                } else {
                    SPacketUpdateTileEntity tileSPkt = new SPacketUpdateTileEntity(pos, -1, pkt.data);
                    tile.onDataPacket(net, tileSPkt);
                }
            }
        }

    }


    public ENotificationType getType() {
        return type;
    }

    public NBTTagCompound getData() {
        return data;
    }

}
