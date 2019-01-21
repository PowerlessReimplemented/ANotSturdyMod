package powerlessri.anotsturdymod.network.actions;

import net.minecraft.network.PacketBuffer;

import java.util.UUID;

/**
 * Attachment retrieved from <tt>bytes[]</tt> with Netty on server side.
 */
public class ReceivedAttachment implements Attachment {

    private UUID sender;
    private PacketBuffer buffer;

    public ReceivedAttachment(UUID sender, PacketBuffer buffer) {
        this.sender = sender;
        this.buffer = buffer;
    }


    @Override
    public UUID getSender() {
        return sender;
    }

    @Override
    public PacketBuffer getBuffer() {
        return buffer;
    }

    /**
     * Since this object is retrieved from client side, its data should not be changed.
     *
     * @throws IllegalStateException Always
     */
    @Override
    public void overrideSource(PacketBuffer buffer) {
        throw new IllegalStateException("Server side action attachment does not write to byte buffer again.");
    }

}
