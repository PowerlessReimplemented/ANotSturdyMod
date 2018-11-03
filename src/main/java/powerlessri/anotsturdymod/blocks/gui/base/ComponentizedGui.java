package powerlessri.anotsturdymod.blocks.gui.base;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import powerlessri.anotsturdymod.blocks.gui.apiimpl.ComponentRoot;
import powerlessri.anotsturdymod.varia.inventory.GuiUtils;


public class ComponentizedGui extends GuiContainer {
    
    protected ComponentRoot root;
    
    public ComponentizedGui(Container container) {
        super(container);
        
        root = new ComponentRoot(this);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();

        GuiUtils.resetGuiGlStates();
        root.draw();
    }
    
}
