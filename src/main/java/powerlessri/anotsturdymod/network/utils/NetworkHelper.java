package powerlessri.anotsturdymod.network.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.network.PacketServerCommand;

public class NetworkHelper {

    private NetworkHelper() {
    }


    public static void sendServerCommand(String cmd, NBTTagCompound args) {
        sendServerCommand(ANotSturdyMod.network, cmd, args);
    }

    public static void sendServerCommand(SimpleNetworkWrapper network, String cmd, NBTTagCompound args) {
        network.sendToServer(new PacketServerCommand(cmd, args));
    }

}
