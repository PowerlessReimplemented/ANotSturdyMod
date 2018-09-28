package powerlessri.anotsturdymod.blocks.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import powerlessri.anotsturdymod.blocks.tile.TileControllerEnergyNetworkAccessPort;

public class ContainerEnergyAccessPort extends Container {

    public ContainerEnergyAccessPort(TileControllerEnergyNetworkAccessPort tile) {

    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

}
