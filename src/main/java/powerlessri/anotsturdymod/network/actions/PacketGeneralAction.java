package powerlessri.anotsturdymod.network.actions;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class PacketGeneralAction implements IMessage {


    // Action HEAD
    /**
     * Internal ID mapping to
     */
    int receiverID;

    // Action BODY
    private Attachment attachment;

    // Action TAIL
    /**
     * UUID of the player represented by the sender client.
     */
    private UUID sender;

    public PacketGeneralAction() {
    }


    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class Handler implements IMessageHandler<PacketGeneralAction, IMessage> {

        @Override
        public IMessage onMessage(PacketGeneralAction message, MessageContext ctx) {
            return null;
        }

    }


}
