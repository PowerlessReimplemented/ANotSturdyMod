package powerlessri.anotsturdymod.network.actions;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Default attachment implementation on client side.
 * <p>
 * On creation, the object is in the state of pending to write until {@link #overrideSource(PacketBuffer)} gets called. Every object can
 * only override once. See the Javadoc of the method for more information.
 * </p>
 */
@SideOnly(Side.CLIENT)
public abstract class WritingAttachment implements Attachment {

    private UUID sender;
    private PacketBuffer buffer = null;
    private boolean written = false;

    public WritingAttachment() {
        this(Minecraft.getMinecraft().player.getUniqueID());
    }

    public WritingAttachment(UUID sender) {
        this.sender = sender;
    }


    /**
     * <p>
     * This method ends the pending state, therefore it can only be called once. It sets the internal reference to a byte buffer to the
     * parameter and write data into it. All the data written is up to the implementation.
     * </p>
     *
     * @param buffer Internal reference will be set to this
     * @throws IllegalStateException When called more than once
     */
    @Override
    public void overrideSource(PacketBuffer buffer) {
        if (written) {
            throw new IllegalStateException("Cannot write to a already written attachment object.");
        }
        buffer.writeUniqueId(sender);
        write(buffer);

        this.buffer = buffer;
        this.written = true;
    }

    /**
     * Custom data.
     */
    protected abstract void write(PacketBuffer buffer);

    @Override
    public UUID getSender() {
        return sender;
    }

    /**
     * Returns the buffer object received at {@link #overrideSource(PacketBuffer)} when out of pending. If it is still in pending, the
     * method will return null.
     *
     * @return Buffer received at {@link #overrideSource(PacketBuffer)} or null
     */
    @Nullable
    @Override
    public PacketBuffer getBuffer() {
        return buffer;
    }

}
