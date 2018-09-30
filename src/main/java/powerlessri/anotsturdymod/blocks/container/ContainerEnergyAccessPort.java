package powerlessri.anotsturdymod.blocks.container;

import net.minecraft.entity.player.EntityPlayer;
import powerlessri.anotsturdymod.blocks.container.base.PlayerContainerBase;
import powerlessri.anotsturdymod.blocks.tile.TileEnergyNetworkAccessPort;
import powerlessri.anotsturdymod.library.utils.Utils;

import javax.rmi.CORBA.Util;

public class ContainerEnergyAccessPort extends PlayerContainerBase {

    public static int HANDLE_REUQEST_CHANNEL;

    public EntityPlayer player;
    public TileEnergyNetworkAccessPort tile;

    /** Has no meaning on server side. Used for buffer changed channel on client side. */
    public int channel;

    public ContainerEnergyAccessPort(EntityPlayer player, TileEnergyNetworkAccessPort tile) {
        super(player);
        this.player = player;
        this.tile = tile;

        this.channel = tile.getChannel();

        addPlayerInventorySlots(8, 84);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

}
