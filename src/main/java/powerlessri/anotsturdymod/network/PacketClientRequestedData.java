package powerlessri.anotsturdymod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import powerlessri.anotsturdymod.library.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class PacketClientRequestedData implements IMessage {

    public static final List<BiConsumer<PacketClientRequestedData, MessageContext>> handlers = new ArrayList<>();

    public int dataHandle;
    public NBTTagCompound data;

    public PacketClientRequestedData(NBTTagCompound data) {
        this.data = data;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer pckt = new PacketBuffer(buf);

        dataHandle = pckt.readInt();
        data = ByteIOHelper.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer pckt = new PacketBuffer(buf);

        pckt.writeInt(dataHandle);
        ByteIOHelper.writeTag(buf, data);
    }


    public static class Handler implements IMessageHandler<PacketClientRequestedData, IMessage> {

        @Override
        public IMessage onMessage(PacketClientRequestedData message, MessageContext ctx) {
            BiConsumer<PacketClientRequestedData, MessageContext> handler = handlers.get(message.dataHandle);
            if(handler != null) {
                Minecraft.getMinecraft().addScheduledTask(() -> handler.accept(message, ctx));
            }
            return null;
        }

    }

}
