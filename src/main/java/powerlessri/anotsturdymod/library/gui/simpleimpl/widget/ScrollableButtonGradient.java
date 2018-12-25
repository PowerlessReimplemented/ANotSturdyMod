package powerlessri.anotsturdymod.library.gui.simpleimpl.widget;

import powerlessri.anotsturdymod.library.gui.api.IScrollableComponent;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;

public class ScrollableButtonGradient extends ButtonGradient implements IScrollableComponent {

    public ScrollableButtonGradient(int relativeX, int width, int height, String text) {
        super(relativeX, 0, width, height, text);
    }


    @Override
    public void draw(GuiDrawBackgroundEvent event) {
        if (isVisible()) {
            super.draw(event);
        }
    }


    private int renderingY;
    private boolean visibility;

    @Override
    public void setExpectedY(int y) {
        this.renderingY = y;
    }

    @Override
    public int getActualY() {
        return renderingY;
    }


    public boolean isVisible() {
        return visibility;
    }

    @Override
    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    @Override
    public boolean doesReceiveEvents() {
        return super.doesReceiveEvents() && isVisible();
    }

}
