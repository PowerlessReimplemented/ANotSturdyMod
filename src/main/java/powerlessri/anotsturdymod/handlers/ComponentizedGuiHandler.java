package powerlessri.anotsturdymod.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import powerlessri.anotsturdymod.blocks.gui.base.ComponentizedGui;

import javax.annotation.Nullable;
import java.awt.*;

public class ComponentizedGuiHandler implements IGuiHandler {
    
    public ComponentizedGuiHandler() {
        init();
    }
    
    
    private void init() {
        // TODO add class loaders to load templates
    }
    
    
    @Nullable
    @Override
    public Container getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Nullable
    @Override
    public ComponentizedGui getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }
    
}
