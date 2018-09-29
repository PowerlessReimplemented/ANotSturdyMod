package powerlessri.anotsturdymod.blocks.container;

import net.minecraft.entity.player.EntityPlayer;
import powerlessri.anotsturdymod.blocks.container.base.PlayerContainerBase;
import powerlessri.anotsturdymod.blocks.tile.TileControllerEnergyNetworkAccessPort;

public class ContainerEnergyAccessPort extends PlayerContainerBase {

    protected TileControllerEnergyNetworkAccessPort tile;

    public ContainerEnergyAccessPort(EntityPlayer player, TileControllerEnergyNetworkAccessPort tile) {
        super(player);
        this.tile = tile;

        addPlayerInventorySlots(8, 84);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

}
