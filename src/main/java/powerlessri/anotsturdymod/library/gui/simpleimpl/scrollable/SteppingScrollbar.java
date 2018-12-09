package powerlessri.anotsturdymod.library.gui.simpleimpl.scrollable;

import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.library.gui.api.EMouseButton;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractButton;

import javax.annotation.Nullable;

public class SteppingScrollbar extends AbstractButton implements IScrollbar {

    private int offset;
    
    private IScrollingPanel parent;

    public SteppingScrollbar(int relativeX, int relativeY, int height) {
        super(relativeX, relativeY, 7, height);
    }


    @Override
    public void onClickedDragging(int mouseX, int mouseY, EMouseButton button, long timePressed) {
        // TODO
        super.onClickedDragging(mouseX, mouseY, button, timePressed);
    }

    @Override
    public int getMaximumHeight() {
        return super.getHeight();
    }

    @Override
    public int getActualY() {
        // TODO
        return 0;
    }

    @Override
    public int getBaseY() {
        return super.getActualY();
    }

    @Override
    public int getWidth() {
        return 7;
    }

    // TODO
    
    @Override
    public void drawNormal(GuiDrawBackgroundEvent event) {
        
    }

    @Override
    public void drawHovering(GuiDrawBackgroundEvent event) {

    }

    @Override
    public void drawPressed(GuiDrawBackgroundEvent event) {

    }

    @Override
    public void drawDisabled(GuiDrawBackgroundEvent event) {

    }

    
    @Override
    public void initialize(GuiScreen gui, IScrollingPanel parent) {
        super.initialize(gui, parent);
        this.parent = parent;
    }

    @Nullable
    @Override
    public IComponent getParentComponent() {
        return parent;
    }
    
}
