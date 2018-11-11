package powerlessri.anotsturdymod.blocks.gui.api;

import io.netty.buffer.ByteBuf;

/**
 * An responder to an action triggered on client side.
 * 
 * Any implementation of this interface should only exist on server side (aka. common),
 * and be using singleton mode.
 */
public interface IActionHandler {

    /**
     * A unique name, for every IActionHandler implementation.
     */
    String getName();

    int getMessageLength();
    
    void writeToBuffer(ByteBuf buf);

    /**
     * Handle an action message sent from client.
     * 
     * @param data The data written into the ByteBuf in {@link #writeToBuffer(ByteBuf)}. Array length = {@link #getMessageLength()}
     */
    void handle(ByteBuf data);
    
}
