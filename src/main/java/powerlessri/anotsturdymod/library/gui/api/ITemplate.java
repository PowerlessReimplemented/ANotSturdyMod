package powerlessri.anotsturdymod.library.gui.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import powerlessri.anotsturdymod.library.gui.integration.ComponentizedGui;
import powerlessri.anotsturdymod.library.gui.integration.ContainerPlayerInventory;

public interface ITemplate {

    /**
     * Set parameters used to create GUI related objects.
     * 
     * @see #getContainer() 
     * @see #getGui() 
     */
    void applyParameters(PlayerEntity player, World world, int x, int y, int z);

    /**
     * Create and return a new container object bond to last {@link #applyParameters(PlayerEntity, World, int, int, int)} operation.
     */
    ContainerPlayerInventory getContainer();

    /**
     * Create and return a new GUI object bond to last {@link #applyParameters(PlayerEntity, World, int, int, int)} operation.
     */
    @OnlyIn(Dist.CLIENT)
    ComponentizedGui getGui();
    
}
