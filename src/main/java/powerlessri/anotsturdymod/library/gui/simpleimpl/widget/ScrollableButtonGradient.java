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


    /**
     * Cache to the input {@link #draw(GuiDrawBackgroundEvent, int)}.
     */
    private int y;

    @Override
    public int getActualY() {
        // Force other stuff to use the y given in method draw(GuiDrawBackgroundEvent, int)
        return y;
    }
    
}
