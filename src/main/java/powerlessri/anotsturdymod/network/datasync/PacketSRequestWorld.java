package powerlessri.anotsturdymod.network.datasync;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import powerlessri.anotsturdymod.network.utils.ByteIOHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class PacketSRequestWorld implements IMessage {

    public static final Map<String, BiFunction<PacketSRequestWorld, MessageContext, PacketClientRequestedData>> responses = new HashMap<>();


    /**
     * If {@code true}, handler will be run inside server thread.
     */
    public boolean isServerThread;

    public int requestId;
    public String requestName;

    public int dimension;
    public int x;
    public int y;
    public int z;

    public PacketSRequestWorld() {
        this(-1, "none");
    }

    public PacketSRequestWorld(int requestId, String requestName) {
        this.requestId = requestId;
        this.requestName = requestName;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer pckt = new PacketBuffer(buf);

        isServerThread = pckt.readBoolean();
        requestId = pckt.readInt();
        requestName = ByteIOHelper.readString(buf);
        dimension = pckt.readInt();
        x = pckt.readInt();
        y = pckt.readInt();
        z = pckt.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer pckt = new PacketBuffer(buf);

        pckt.writeBoolean(isServerThread);
        pckt.writeInt(requestId);
        ByteIOHelper.writeString(buf, requestName);
        pckt.writeInt(dimension);
        pckt.writeInt(x);
        pckt.writeInt(y);
        pckt.writeInt(z);
    }


    public static class Handler implements IMessageHandler<PacketSRequestWorld, PacketClientRequestedData> {

        private static final NBTTagCompound errorCannotFindResponse = new NBTTagCompound();

        static {
            errorCannotFindResponse.setInteger("head", -1);
        }

        @Override
        public PacketClientRequestedData onMessage(PacketSRequestWorld message, MessageContext ctx) {
            BiFunction<PacketSRequestWorld, MessageContext, PacketClientRequestedData> handler = responses.get(message.requestName);
            if (handler != null) {
                // Response gets send inside handler
                if (message.isServerThread) {
                    FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handler.apply(message, ctx));
                } else {
                    handler.apply(message, ctx);
                }

                return null;
            }

            PacketClientRequestedData emptyResponse = new PacketClientRequestedData(message.requestId, errorCannotFindResponse);
            return emptyResponse;
        }

    }

}
