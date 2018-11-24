package powerlessri.anotsturdymod.library.gui.simpleimpl.widget;

import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.scrollable.IScrollableComponent;

public class ScrollableButtonGradient extends ButtonGradient implements IScrollableComponent {
    
    public ScrollableButtonGradient(int relativeX, int relativeY, int width, int height, String text) {
        super(relativeX, relativeY, width, height, text);
    }
    

    @Override
    public void draw(GuiDrawBackgroundEvent event, int y) {
        this.y = y;
        draw(event);
    }

    
    private int y;

    @Override
    public int getActualY() {
        return parent.getY() + y;
    }
    
}
