package powerlessri.anotsturdymod.network;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class NetworkHelper {

    private NetworkHelper() {
    }


    public static void sendServerCommand(SimpleNetworkWrapper network, String cmd, NBTTagCompound args) {
        network.sendToServer(new PacketServerCommand(cmd, args));
    }

}
