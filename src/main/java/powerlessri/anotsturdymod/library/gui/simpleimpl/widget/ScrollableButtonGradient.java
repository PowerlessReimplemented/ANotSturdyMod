package powerlessri.anotsturdymod.library.gui.simpleimpl.widget;

import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.scrollable.IScrollableComponent;

public class ScrollableButtonGradient extends ButtonGradient implements IScrollableComponent {

    private int y;
    private boolean visibility;

    public ScrollableButtonGradient(int relativeX, int relativeY, int width, int height, String text) {
        super(relativeX, relativeY, width, height, text);
    }

    
    @Override
    public void setExpectedY(int y) {
        this.y = y;
    }

    @Override
    public int getActualY() {
        return y;
    }

    
    
    public boolean isVisible() {
        return visibility;
    }
    
    @Override
    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
        if (visibility) {
            getFunctionalRoot().getEventManager().markVisible(this);
        } else {
            getFunctionalRoot().getEventManager().markInvisible(this);
        }
    }
    
    public void inverseVisibility() {
        setVisibility(!isVisible());
    }
    
}
