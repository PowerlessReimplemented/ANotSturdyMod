package powerlessri.anotsturdymod.library.gui.template;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.library.gui.api.ITemplate;
import powerlessri.anotsturdymod.library.gui.integration.ComponentizedGui;
import powerlessri.anotsturdymod.library.gui.integration.ContainerPlayerInventory;

//TODO add bits and pieces
public abstract class AbstractTemplate implements ITemplate {
    
    public EntityPlayer player;
    public World world;
    public int x;
    public int y;
    public int z;

    @Override
    public void applyParameters(EntityPlayer player, World world, int x, int y, int z) {
        this.player = player;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
}
