package powerlessri.anotsturdymod.blocks.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powerlessri.anotsturdymod.blocks.container.base.PlayerContainerBase;
import powerlessri.anotsturdymod.blocks.tile.TileENAccessPort;
import powerlessri.anotsturdymod.blocks.tile.TileENComponentBase;
import powerlessri.anotsturdymod.blocks.tile.TileENController;
import powerlessri.anotsturdymod.network.datasync.PacketClientRequestedData;

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
