package powerlessri.anotsturdymod.network.datasync;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.network.utils.ByteIOHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiConsumer;

public class PacketClientRequestedData implements IMessage {

    public static void requestWorldData(String requestName, BiConsumer<PacketClientRequestedData, MessageContext> onResponse, int dimension, int x, int y, int z) {
        int requestId = getRequestId();
        handlers.set(requestId, (msg, ctx) -> {
            onResponse.accept(msg, ctx);
            releaseRequestId(requestId);
        });

        PacketSRequestWorld request = new PacketSRequestWorld(requestId, requestName);
        request.isServerThread = true;
        request.dimension = dimension;
        request.x = x;
        request.y = y;
        request.z = z;
        ANotSturdyMod.genericChannel.sendToServer(request);
    }


    private static int nextRequestId = 0;
    private static final Queue<Integer> idleRequestIds = new LinkedList<>();

    private static int getRequestId() {
        if (!idleRequestIds.isEmpty()) {
            return idleRequestIds.remove();
        }

        handlers.add(null); // Increase capacity
        return nextRequestId++;
    }

    private static void releaseRequestId(int id) {
        handlers.set(id, null);
        idleRequestIds.add(id);
    }


    public static final List<BiConsumer<PacketClientRequestedData, MessageContext>> handlers = new ArrayList<>();


    /**
     * Assigned at server side when creating response packet, used to invoke onResponse at client side.
     */
    public int requestId;
    public NBTTagCompound data;

    public PacketClientRequestedData() {
    }

    public PacketClientRequestedData(int requestId, NBTTagCompound data) {
        this.requestId = requestId;
        this.data = data;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer pckt = new PacketBuffer(buf);

        requestId = pckt.readInt();
        data = ByteIOHelper.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer pckt = new PacketBuffer(buf);

        pckt.writeInt(requestId);
        ByteIOHelper.writeTag(buf, data);
    }


    public static class Handler implements IMessageHandler<PacketClientRequestedData, IMessage> {

        @Override
        public IMessage onMessage(PacketClientRequestedData message, MessageContext ctx) {
            BiConsumer<PacketClientRequestedData, MessageContext> handler = handlers.get(message.requestId);
            if (handler != null) {
                Minecraft.getMinecraft().addScheduledTask(() -> handler.accept(message, ctx));
            }
            return null;
        }

    }

}
