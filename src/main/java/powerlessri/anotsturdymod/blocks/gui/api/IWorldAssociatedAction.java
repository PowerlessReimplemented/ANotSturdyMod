package powerlessri.anotsturdymod.blocks.gui.api;

import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;

/**
 * An action handler that responds to an action that is associated to a world object (which means dimension and position).
 */
public interface IWorldAssociatedAction extends IActionHandler {

    /**
     * Use {@link #handleWorld(World, int, int, int, ByteBuf)} instead. <br />
     * If the usage doesn't need the parameters, this is not the appropriate interface for the usage.
     */
    @Override
    @Deprecated
    void handle(ByteBuf data);

    /**
     * Specialized version of the original one, for an action associated to an object in world.
     * See {@link IActionHandler#handle(ByteBuf)} for more information.
     * 
     * @param data This parameter is null when, and only when {@link #getMessageLength()} equals 0
     */
    void handleWorld(World world, int x, int y, int z, ByteBuf data);
}
