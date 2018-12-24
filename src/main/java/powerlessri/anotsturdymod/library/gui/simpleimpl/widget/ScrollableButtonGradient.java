package powerlessri.anotsturdymod.library.gui.simpleimpl.widget;

import powerlessri.anotsturdymod.library.gui.api.IScrollableComponent;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;

public class ScrollableButtonGradient extends ButtonGradient implements IScrollableComponent {

    private int y;
    private boolean visibility;

    public ScrollableButtonGradient(int relativeX, int width, int height, String text) {
        super(relativeX, 0, width, height, text);
    }


    @Override
    public void draw(GuiDrawBackgroundEvent event) {
        if (isVisible()) {
            super.draw(event);
        }
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
    }

    public void inverseVisibility() {
        setVisibility(!isVisible());
    }

    @Override
    public boolean doesReceiveEvents() {
        return super.doesReceiveEvents() && isVisible();
    }

}
