package powerlessri.anotsturdymod.library.gui.simpleimpl.button;

import net.minecraft.util.EnumActionResult;
import powerlessri.anotsturdymod.library.gui.api.EEventType;
import powerlessri.anotsturdymod.library.gui.api.EMouseButton;
import powerlessri.anotsturdymod.library.gui.simpleimpl.events.IInteractionHandler;
import powerlessri.anotsturdymod.library.gui.integration.ContextGuiDrawing;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractComponent;

public abstract class Button extends AbstractComponent implements IInteractionHandler {

    private boolean disabled = false;
    private boolean pressed = false;

    private long timePressed;

    private int width;
    private int height;

    public Button(int relativeX, int relativeY, int width, int height) {
        super(relativeX, relativeY);

        this.width = width;
        this.height = height;
    }


    @Override
    public boolean isLeafComponent() {
        return true;
    }

    public void disable() {
        disabled = true;
    }

    public void enable() {
        disabled = false;
    }

    public boolean isPressed() {
        return pressed;
    }

    public boolean isDisabled() {
        return disabled;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    /**
     * Number of ticks (supposedly) passes since player pressed the button. Resets when pressed again.
     */
    protected long getTimePressed() {
        return timePressed;
    }


    @Override
    public boolean doesReceiveEvents() {
        return !isDisabled();
    }


    @Override
    public void draw(ContextGuiDrawing event) {
        if (isDisabled()) {
            drawDisabled(event);
            return;
        }

        if (isPressed()) {
            drawPressed(event);
        } else {
            if (isHovering(event)) {
                drawHovering(event);
            } else {
                drawNormal(event);
            }
        }
    }

    public abstract void drawNormal(ContextGuiDrawing event);

    public abstract void drawHovering(ContextGuiDrawing event);

    public abstract void drawPressed(ContextGuiDrawing event);

    public abstract void drawDisabled(ContextGuiDrawing event);


    @Override
    public EnumActionResult onClicked(int mouseX, int mouseY, EMouseButton button, EEventType type) {
        this.pressed = true;
        this.timePressed = 0;
        return EnumActionResult.FAIL;
    }

    @Override
    public void onClickedDragging(int mouseX, int mouseY, EMouseButton button, long timePressed) {
        this.timePressed = timePressed;
    }

    @Override
    public void onHovering(int mouseX, int mouseY) {
    }

    @Override
    public EnumActionResult onReleased(int mouseX, int mouseY, EMouseButton button, EEventType type) {
        this.pressed = false;
        return EnumActionResult.FAIL;
    }


    private boolean isHovering(ContextGuiDrawing event) {
        return isPointInside(event.getMouseX(), event.getMouseY());
    }

}
