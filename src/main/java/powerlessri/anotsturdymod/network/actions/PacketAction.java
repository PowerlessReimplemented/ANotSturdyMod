package powerlessri.anotsturdymod.network.actions;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class PacketAction implements IMessage {

    // Action HEAD
    /**
     * Receiver on server side.
     */
    private Target target;

    // Action BODY
    private Attachment attachment;
    private ByteBuf customData;

    // Action TAIL
    /**
     * UUID of the player represented by the sender client.
     */
    private UUID sender;

    public PacketAction() {
    }

    public PacketAction(Target target, Attachment attachment, ByteBuf customData, UUID sender) {
        this.target = target;
        this.attachment = attachment;
        this.customData = customData;
        this.sender = sender;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer pkt = new PacketBuffer(buf);
        byte typeID = pkt.readByte();
        this.target = DefaultTargets.createRawFromType(typeID);
        this.target.read(pkt);
        this.customData = pkt.readBytes(pkt.readInt());
        this.sender = pkt.readUniqueId();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer pkt = new PacketBuffer(buf);
        pkt.writeByte(target.getTypeID());
        target.write(pkt);
        pkt.writeInt(customData.readableBytes());
        pkt.writeBytes(customData);
        pkt.writeUniqueId(sender);
    }

    public static class Handler implements IMessageHandler<PacketAction, IMessage> {

        @Override
        public IMessage onMessage(PacketAction message, MessageContext ctx) {
            ActionTaskPool.scheduleTask(message.target, message.attachment);
            return null;
        }

    }


}
