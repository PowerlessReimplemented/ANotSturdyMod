package powerlessri.anotsturdymod.mechanics.remote_enetwork.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powerlessri.anotsturdymod.blocks.gui.api.template.ITemplate;
import powerlessri.anotsturdymod.blocks.gui.api.template.TemplateProvider;
import powerlessri.anotsturdymod.blocks.gui.base.ComponentizedGui;
import powerlessri.anotsturdymod.blocks.gui.container.ContainerPlayerInventory;
import powerlessri.anotsturdymod.mechanics.remote_enetwork.tile.TileENComponentBase;

public class ContainerEnergyIOAccess extends ContainerPlayerInventory {
    
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
