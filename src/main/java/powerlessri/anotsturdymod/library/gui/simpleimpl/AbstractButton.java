package powerlessri.anotsturdymod.library.gui.simpleimpl;

import net.minecraft.util.EnumActionResult;
import powerlessri.anotsturdymod.library.gui.api.EDisplayMode;
import powerlessri.anotsturdymod.library.gui.api.EEventType;
import powerlessri.anotsturdymod.library.gui.api.EMouseButton;
import powerlessri.anotsturdymod.library.gui.api.IInteractionHandler;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;

public abstract class AbstractButton extends AbstractComponent implements IInteractionHandler {
    
    private boolean isDisabled;
    private boolean isPressed;

    /**
     * Number of frames passes since play pressed down.
     * <b>DO NOT modify this field</b> on your own.
     */
    protected int timePressed;

    private int width;
    private int height;

    public AbstractButton(int relativeX, int relativeY, int width, int height) {
        super(relativeX, relativeY);
        
        this.width = width;
        this.height = height;
        this.isPressed = false;
        this.isDisabled = false;
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
    public boolean doesReceiveEvents() {
        return isVisible();
    }

    @Override
    public boolean isVisible() {
        return !isDisabled;
    }

    @Override
    public void draw(GuiDrawBackgroundEvent event) {
        if (isDisabled) {
            drawDisabled(event);
            return;
        }

        if (isPressed) {
            drawPressed(event);
            timePressed++;
        } else {
            timePressed = 0;
            if (isHovering(event)) {
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
        return EnumActionResult.FAIL;
    }

    @Override
    public void onClickedDragging(int mouseX, int mouseY, EMouseButton button, long timePressed) {
    }

    @Override
    public void onHoveredDragging(int mouseX, int mouseY, EMouseButton button) {
    }

    @Override
    public EnumActionResult onReleased(int mouseX, int mouseY, EMouseButton button, EEventType type) {
        isPressed = false;
        return EnumActionResult.FAIL;
    }

    
    private boolean isHovering(GuiDrawBackgroundEvent event) {
        return isPointInside(event.getMouseX(), event.getMouseY());
    }
    
}
