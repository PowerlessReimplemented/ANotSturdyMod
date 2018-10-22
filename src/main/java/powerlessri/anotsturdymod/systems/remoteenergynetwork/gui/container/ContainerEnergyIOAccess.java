package powerlessri.anotsturdymod.systems.remoteenergynetwork.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powerlessri.anotsturdymod.blocks.container.base.PlayerContainerBase;
import powerlessri.anotsturdymod.systems.remoteenergynetwork.tile.TileENComponentBase;

public class ContainerEnergyIOAccess extends PlayerContainerBase {

    public EntityPlayer player;
    public TileENComponentBase tile;

    @SideOnly(Side.CLIENT)
    public int channel;

    public ContainerEnergyIOAccess(EntityPlayer player, TileENComponentBase tile) {
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
