package powerlessri.anotsturdymod.library.gui.immutableimpl;

import net.minecraft.util.EnumActionResult;
import powerlessri.anotsturdymod.library.gui.api.EEventType;
import powerlessri.anotsturdymod.library.gui.api.EMouseButton;
import powerlessri.anotsturdymod.library.gui.api.IInteractionHandler;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;

public abstract class AbstractButton extends AbstractComponent implements IInteractionHandler {
    
    private boolean isDisabled;
    private boolean isPressed;
    protected int timePressed;

    private int width;
    private int height;

    public AbstractButton(int relativeX, int relativeY, int width, int height) {
        super(relativeX, relativeY);
        this.width = width;
        this.height = height;
    }


    @Override
    public boolean isLeafComponent() {
        return true;
    }
    
    public boolean isPressed() {
        return isPressed;
    }
    
    public boolean isDisabled() {
        return isDisabled;
    }
    
    public void disable() {
        isDisabled = true;
    }
    
    public void enable() {
        isDisabled = false;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void draw(GuiDrawBackgroundEvent event) {
        if (isPressed) {
            timePressed++;
            drawPressed(event);
        } else {
            timePressed = 0;
            if (this.isDisabled) {
                drawDisabled(event);
            } else if (isHovering(event)) {
                drawHovering(event);
            } else {
                drawNormal(event);
            }
        }
    }
    
    public abstract void drawNormal(GuiDrawBackgroundEvent event);
    
    public abstract void drawHovering(GuiDrawBackgroundEvent event);
    
    public abstract void drawPressed(GuiDrawBackgroundEvent event);
    
    public abstract void drawDisabled(GuiDrawBackgroundEvent event);


    @Override
    public EnumActionResult onClicked(int mouseX, int mouseY, EMouseButton button, EEventType type) {
        isPressed = true;
        return EnumActionResult.PASS;
    }
    
    @Override
    public EnumActionResult onReleased(int mouseX, int mouseY, EMouseButton button, EEventType type) {
        isPressed = false;
        return EnumActionResult.PASS;
    }

    
    private boolean isHovering(GuiDrawBackgroundEvent event) {
        return isPointInside(event.getMouseX(), event.getMouseY());
    }
    
}
