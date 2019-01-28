package powerlessri.anotsturdymod.library.network.actions;

import net.minecraft.network.PacketBuffer;

import java.util.UUID;

/**
 * Custom attached data to an action packet which is used when processing the action.
 */
public interface IAttachment {

    /**
     * UUID of the sender player entity.
     * <p>
     * This field should be transient, which means it does not get written into the byte buffer for networking.
     * </p>
     */
    UUID getSender();

    /**
     * Get the stored data in form of a byte buffer.
     */
    PacketBuffer getBuffer();

    /**
     * Write custom data into the parameter.
     */
    void overrideSource(PacketBuffer buffer);

}
