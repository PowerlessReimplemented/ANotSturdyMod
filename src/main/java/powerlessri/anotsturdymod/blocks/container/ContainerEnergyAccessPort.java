package powerlessri.anotsturdymod.blocks.container;

import net.minecraft.entity.player.EntityPlayer;
import powerlessri.anotsturdymod.blocks.container.base.PlayerContainerBase;
import powerlessri.anotsturdymod.blocks.tile.TileEnergyNetworkAccessPort;
import powerlessri.anotsturdymod.blocks.tile.TileEnergyNetworkController;
import powerlessri.anotsturdymod.library.utils.Utils;
import powerlessri.anotsturdymod.network.datasync.PacketClientRequestedData;

public class ContainerEnergyAccessPort extends PlayerContainerBase {

    public EntityPlayer player;
    public TileEnergyNetworkAccessPort tile;

    /** Has no meaning on server side. Used for buffer changed channel on client side. */
    public int channel;

    public ContainerEnergyAccessPort(EntityPlayer player, TileEnergyNetworkAccessPort tile) {
        super(player);
        this.player = player;
        this.tile = tile;

        // Display -1 to show it's not synced yet
        this.channel = -1;
        PacketClientRequestedData.requestWorldData(TileEnergyNetworkAccessPort.GET_CHANNEL, (msg, ctx) -> {
            this.channel = msg.data.getInteger(TileEnergyNetworkController.CHANNEL);
        }, tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ());

        addPlayerInventorySlots(8, 84);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

}
