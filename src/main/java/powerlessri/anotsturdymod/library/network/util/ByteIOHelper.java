package powerlessri.anotsturdymod.library.network.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import powerlessri.anotsturdymod.varia.general.Utils;

import javax.annotation.Nullable;
import java.io.IOException;

public final class ByteIOHelper {

    @Nullable
    public static NBTTagCompound readTag(ByteBuf buf) {
        try {
            return new PacketBuffer(buf).readCompoundTag();
        } catch (IOException e) {
            Utils.getLogger().error("Error reading compound from packet", e);
        }
        return null;
    }

    public static void writeTag(ByteBuf buf, NBTTagCompound tag) {
        new PacketBuffer(buf).writeCompoundTag(tag);
    }

}
