package powerlessri.anotsturdymod.network.actions;

import net.minecraft.network.PacketBuffer;

/**
 * Custom attached data to an action packet which is used when processing the action.
 */
public class Attachment {

    private PacketBuffer buffer;

    public Attachment(PacketBuffer buffer) {
        this.buffer = buffer;
    }

}
