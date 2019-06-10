package powerlessri.anotsturdymod.library.network.notification;

import net.minecraft.network.NetworkManager;

public interface INotificationReceiver {

    void handleNotificationPacket(NetworkManager net, PacketNotification notification);

}
