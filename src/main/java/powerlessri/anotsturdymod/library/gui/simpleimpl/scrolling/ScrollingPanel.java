package powerlessri.anotsturdymod.library.gui.simpleimpl.scrolling;

import com.google.common.collect.ImmutableList;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractComponent;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;

public class ScrollingPanel extends AbstractComponent {
    
    private int width;
    private int height;

    private ImmutableList<IComponent> components;

    public ScrollingPanel(int relativeX, int relativeY, int width, int height) {
        super(relativeX, relativeY);
        this.width = width;
        this.height = height;
    }

    
    @Override
    public void draw(GuiDrawBackgroundEvent event) {
        for (IComponent component : components) {
            component.tryDraw(event);
        }
    }


    public void setHeight(int height) {
        this.height = height;
    }
    
    
    @Override
    public boolean isLeafComponent() {
        return false;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
    
}
