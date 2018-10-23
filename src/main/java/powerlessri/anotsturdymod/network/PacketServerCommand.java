package powerlessri.anotsturdymod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import powerlessri.anotsturdymod.network.utils.ByteIOHelper;
import powerlessri.anotsturdymod.varia.general.Utils;
import powerlessri.anotsturdymod.varia.tags.TagUtils;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class PacketServerCommand implements IMessage {

    public static final HashMap<String, BiConsumer<PacketServerCommand, MessageContext>> handlers = new HashMap<>();


    public String cmd;
    public NBTTagCompound args;

    public PacketServerCommand() {
    }

    public PacketServerCommand(String cmd, NBTTagCompound args) {
        this.cmd = cmd;
        this.args = args;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        cmd = ByteIOHelper.readString(buf);
        args = ByteIOHelper.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteIOHelper.writeString(buf, cmd);
        ByteIOHelper.writeTag(buf, args);
    }


    public static class Handler implements IMessageHandler<PacketServerCommand, IMessage> {

        @Override
        public IMessage onMessage(PacketServerCommand message, MessageContext ctx) {
            BiConsumer<PacketServerCommand, MessageContext> handler = handlers.get(message.cmd);
            if (handler != null) {
                FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handler.accept(message, ctx));
            } else {
                Utils.getLogger().error("Cannot find server command \"" + message.cmd + "\"!");
            }
            return null;
        }

    }


    public static NBTTagCompound makeWorldPosArgs(int dimension, int x, int y, int z) {
        NBTTagCompound tag = new NBTTagCompound();
        makeWorldPosArgs(tag, dimension, x, y, z);
        return tag;
    }

    public static void makeWorldPosArgs(NBTTagCompound tag, int dimension, int x, int y, int z) {
        TagUtils.writeDimension(tag, dimension);
        TagUtils.writeBlockPos(tag, x, y, z);
    }

}
