package powerlessri.anotsturdymod.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerRemoteEnergyCell extends Container {

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

}
