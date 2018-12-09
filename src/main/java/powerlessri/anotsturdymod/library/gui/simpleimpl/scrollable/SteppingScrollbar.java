package powerlessri.anotsturdymod.library.gui.simpleimpl.scrollable;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumActionResult;
import powerlessri.anotsturdymod.library.gui.api.*;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractButton;

import javax.annotation.Nullable;

public class SteppingScrollbar extends AbstractButton implements IScrollbar {
    
    private int offset;
    private int step;
    private int actualHeight;
    
    private int lastMouseY;
    
    private IScrollingPanel parent;

    public SteppingScrollbar(int relativeX, int relativeY, int height) {
        super(relativeX, relativeY, 7, height);
    }


    @Override
    public void initialize(GuiScreen gui, IScrollingPanel parent) {
        super.initialize(gui, parent);
        this.parent = parent;
        this.actualHeight = (int) (getMaximumHeight() * parent.getContentKFactor());
    }

    @Override
    public EnumActionResult onClicked(int mouseX, int mouseY, EMouseButton button, EEventType type) {
        lastMouseY = mouseY;
        return EnumActionResult.FAIL;
    }

    @Override
    public void onClickedDragging(int mouseX, int mouseY, EMouseButton button, long timePressed) {
        if (lastMouseY != mouseY) {
            // When positive, mouse moved down. When negative, mouse moved up.
            int change = mouseY - lastMouseY;
            offset += change;
        }
        lastMouseY = mouseY;
    }

    @Override
    public EnumActionResult onReleased(int mouseX, int mouseY, EMouseButton button, EEventType type) {
        if (offset != parent.getCurrentStep()) {
            parent.setCurrentStep(offset);
        }
        
        lastMouseY = -1;
        return EnumActionResult.FAIL;
    }

    
    @Override
    public int getHeight() {
        return actualHeight;
    }

    @Override
    public int getMaximumHeight() {
        return super.getHeight();
    }

    @Override
    public int getActualY() {
        return getBaseY() + offset;
    }

    @Override
    public int getBaseY() {
        return super.getActualY();
    }

    @Override
    public int getWidth() {
        return 7;
    }

    @Nullable
    @Override
    public IComponent getParentComponent() {
        return parent;
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

    
}
