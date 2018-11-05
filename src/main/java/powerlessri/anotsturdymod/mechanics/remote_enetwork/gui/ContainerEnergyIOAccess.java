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

    @TemplateProvider(id = "testGui")
    public static ITemplate getGuiTemplate() {
        return new ITemplate() {
            @Override
            public void applyParameters(EntityPlayer player, World world, int x, int y, int z) {
                
            }

            @Override
            public ContainerPlayerInventory getContainer() {
                return null;
            }

            @Override
            public ComponentizedGui getGui() {
                return null;
            }
        };
    }
    
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
