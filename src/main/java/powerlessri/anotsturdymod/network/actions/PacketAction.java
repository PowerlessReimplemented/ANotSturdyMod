package powerlessri.anotsturdymod.network.actions;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import powerlessri.anotsturdymod.network.actions.registry.TargetMapping;

import java.util.UUID;

public class PacketAction implements IMessage {

    // Action HEAD
    /**
     * UUID of the player represented by the sender client.
     */
    private UUID sender;

    /**
     * Receiver on server side.
     */
    private Target target;

    // Action BODY
    /**
     * Custom data.
     */
    private Attachment attachment;

    public PacketAction() {
    }

    public PacketAction(Target target, Attachment attachment) {
        this.target = target;
        this.attachment = attachment;
        this.sender = attachment.getSender();
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer pkt = new PacketBuffer(buf);

        // Head of packet
        this.sender = pkt.readUniqueId();
        byte typeID = pkt.readByte();
        this.target = TargetMapping.getInstance().create(typeID);
        this.target.read(pkt);

        // Body of packet
        int len = pkt.readInt();
        ByteBuf data = pkt.readBytes(len);
        this.attachment = new ReceivedAttachment(sender, new PacketBuffer(data));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer pkt = new PacketBuffer(buf);

        // Head of packet
        pkt.writeUniqueId(sender);
        pkt.writeByte(target.getTypeID());
        target.write(pkt);

        // Body of packet
        attachment.overrideSource(pkt);
        pkt.writeInt(attachment.getBuffer().readableBytes());
        pkt.writeBytes(attachment.getBuffer());
    }

    public static class Handler implements IMessageHandler<PacketAction, IMessage> {

        @Override
        public IMessage onMessage(PacketAction message, MessageContext ctx) {
            ActionTaskPool.scheduleTask(message.target, message.attachment);
            return null;
        }

    }

}
