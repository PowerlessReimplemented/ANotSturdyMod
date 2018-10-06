package powerlessri.anotsturdymod.blocks.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powerlessri.anotsturdymod.blocks.container.base.PlayerContainerBase;
import powerlessri.anotsturdymod.blocks.tile.TileENAccessPort;
import powerlessri.anotsturdymod.blocks.tile.TileENController;
import powerlessri.anotsturdymod.network.datasync.PacketClientRequestedData;

public class ContainerEnergyAccessPort extends PlayerContainerBase {

    public EntityPlayer player;
    public TileENAccessPort tile;

    @SideOnly(Side.CLIENT)
    public int channel;

    public ContainerEnergyAccessPort(EntityPlayer player, TileENAccessPort tile) {
        super(player);
        this.player = player;
        this.tile = tile;

        if(player.world.isRemote) {
            this.channel = -1; // Display -1 to show it's not synced yet
            PacketClientRequestedData.requestWorldData(TileENAccessPort.GET_CHANNEL, (msg, ctx) -> {
                this.channel = msg.data.getInteger(TileENController.CHANNEL);
            }, player.dimension, tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ());
        }

        addPlayerInventorySlots(8, 84);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

}
