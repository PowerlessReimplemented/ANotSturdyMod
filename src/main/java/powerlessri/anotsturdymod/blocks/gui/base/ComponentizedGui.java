package powerlessri.anotsturdymod.blocks.gui.base;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import org.lwjgl.util.glu.GLU;
import powerlessri.anotsturdymod.blocks.gui.apiimpl.VanillaGui;
import powerlessri.anotsturdymod.varia.inventory.GuiUtils;


public class ComponentizedGui extends GuiContainer {
    
    protected VanillaGui root;
    
    public ComponentizedGui(Container container) {
        super(container);
        
        root = new VanillaGui(this);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();

        GuiUtils.resetGuiGlStates();
        root.draw();
    }
    
}
