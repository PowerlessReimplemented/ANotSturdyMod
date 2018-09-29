package powerlessri.anotsturdymod.blocks.container;

import net.minecraft.entity.player.EntityPlayer;
import powerlessri.anotsturdymod.blocks.container.base.PlayerContainerBase;
import powerlessri.anotsturdymod.blocks.tile.TileEnergyNetworkAccessPort;

public class ContainerEnergyAccessPort extends PlayerContainerBase {

    public EntityPlayer player;
    public TileEnergyNetworkAccessPort tile;

    public ContainerEnergyAccessPort(EntityPlayer player, TileEnergyNetworkAccessPort tile) {
        super(player);
        this.player = player;
        this.tile = tile;

        addPlayerInventorySlots(8, 84);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }



}
