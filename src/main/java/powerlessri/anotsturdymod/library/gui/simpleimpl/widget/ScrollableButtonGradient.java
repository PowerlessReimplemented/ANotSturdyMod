package powerlessri.anotsturdymod.library.gui.simpleimpl.widget;

import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.api.IScrollableComponent;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;

public class ScrollableButtonGradient extends ButtonGradient implements IScrollableComponent {

    public ScrollableButtonGradient(int relativeX, int width, int height, String text) {
        super(relativeX, 0, width, height, text);
    }


    @Override
    public void initialize(GuiScreen gui, IComponent parent) {
        super.initialize(gui, parent);
        this.visibility = true;
        this.renderingY = super.getActualY();
    }

    @Override
    public void draw(GuiDrawBackgroundEvent event) {
        if (isVisible()) {
            super.draw(event);
        }
    }


    private boolean visibility;
    private int renderingY;

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
