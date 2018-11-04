package powerlessri.anotsturdymod.blocks.gui.api.template;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powerlessri.anotsturdymod.blocks.gui.base.ComponentizedGui;
import powerlessri.anotsturdymod.blocks.gui.container.ContainerPlayerInventory;

public interface ITemplate {

    /**
     * Stage gui related object based on the given parameters.
     * This should <b>create a new</b> set of object every time, even if the parameters are exactly the same.
     */
    void create(EntityPlayer player, World world, int x, int y, int z);

    /**
     * @return Container bond to last {@link #create(EntityPlayer, World, int, int, int)} operation.
     */
    ContainerPlayerInventory getContainer();

    /**
     * @return GuiContainer bond to last {@link #create(EntityPlayer, World, int, int, int)} operation.
     */
    @SideOnly(Side.CLIENT)
    ComponentizedGui getGui();
    
}
