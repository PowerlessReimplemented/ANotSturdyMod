package powerlessri.anotsturdymod.network.actions;

import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import powerlessri.anotsturdymod.library.gui.api.IActionWorldAssociated;
import powerlessri.anotsturdymod.library.gui.api.IActionHandler;

/**
 * <p>Sender: <b>CLIENT</b></p>
 * <p>Receiver: <b>SERVER</b></p>
 */
// TODO determine usage of this class
public class PacketLocationalGuiAction implements IMessage {
    
    // Present on both sides
    private int actionId;
    private int dimension;
    private int x;
    private int y;
    private int z;
    
    // Present on receiving side only
    private ByteBuf data;
    
    // Present on sending side only
    private IActionHandler action;
    
    public PacketLocationalGuiAction() {
    }

    public PacketLocationalGuiAction(IActionHandler handler, int dimension, int x, int y, int z) {
        this(handler.getName(), dimension, x, y, z);
    }
    
    public PacketLocationalGuiAction(String name, int dimension, int x, int y, int z) {
        this(GuiActionManager.findID(name), dimension, x, y, z);
    }

    public PacketLocationalGuiAction(int actionId, int dimension, int x, int y, int z) {
        this.actionId = actionId;
        this.dimension = dimension;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    
    @Override
    public void fromBytes(ByteBuf buf) {
        actionId = buf.readInt();
        
        int dataLength = GuiActionManager.findAction(actionId).getMessageLength();
        if (dataLength > 0) {
            data = buf.readBytes(dataLength);
        } else {
            data = null;
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(actionId);
        
        action.writeToBuffer(buf);
    }


    public static class Handler implements IMessageHandler<PacketLocationalGuiAction, IMessage> {
        
        @Override
        public IMessage onMessage(PacketLocationalGuiAction msg, MessageContext ctx) {
            IActionWorldAssociated action = (IActionWorldAssociated) GuiActionManager.findAction(msg.actionId);
            World world = DimensionManager.getWorld(msg.dimension);
            
            action.handleWorld(world, msg.x, msg.y, msg.z, msg.data);
            
            return null;
        }
        
    }
    
}
