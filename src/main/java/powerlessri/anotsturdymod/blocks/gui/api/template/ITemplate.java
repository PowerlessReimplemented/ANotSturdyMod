package powerlessri.anotsturdymod.blocks.gui.api.template;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powerlessri.anotsturdymod.blocks.gui.base.ComponentizedGui;
import powerlessri.anotsturdymod.blocks.gui.container.ContainerPlayerInventory;

public interface ITemplate extends IStringSerializable {

    /**
     * Set parameters used to create GUI related objects.
     * 
     * @see #getContainer() 
     * @see #getGui() 
     */
    void applyParameters(EntityPlayer player, World world, int x, int y, int z);

    /**
     * Create and return a new container object bond to last {@link #applyParameters(EntityPlayer, World, int, int, int)} operation.
     */
    ContainerPlayerInventory getContainer();

    /**
     * Create and return a new GUI object bond to last {@link #applyParameters(EntityPlayer, World, int, int, int)} operation.
     */
    @SideOnly(Side.CLIENT)
    ComponentizedGui getGui();
    
}
