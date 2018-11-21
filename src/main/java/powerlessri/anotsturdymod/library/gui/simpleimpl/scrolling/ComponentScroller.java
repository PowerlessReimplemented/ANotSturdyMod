package powerlessri.anotsturdymod.library.gui.simpleimpl.scrolling;

import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractComponent;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;

import javax.annotation.Nullable;

public class ComponentScroller extends AbstractComponent {
    
    private ScrollingPanel parent;
    
    public ComponentScroller(int relativeX, int relativeY) {
        super(relativeX, relativeY);
    }

    
    @Override
    public boolean isLeafComponent() {
        return true;
    }

    @Override
    public int getWidth() {
        return 7;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void draw(GuiDrawBackgroundEvent event) {

    }


    @Override
    public void initialize(GuiScreen gui, IComponent parent) {
        super.initialize(gui, parent);
        this.parent = (ScrollingPanel) parent;
    }

    @Nullable
    @Override
    public IComponent getParentComponent() {
        return parent;
    }
}
