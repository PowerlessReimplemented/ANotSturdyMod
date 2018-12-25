package powerlessri.anotsturdymod.library.gui.simpleimpl.scrollable;

import net.minecraft.util.EnumActionResult;
import powerlessri.anotsturdymod.library.gui.api.EEventType;
import powerlessri.anotsturdymod.library.gui.api.EMouseButton;

public class SteppingScrollbar extends ScrollbarThinStyled {

    private int step;

    private int pressedMouseY;
    private int lastMouseY;

    public SteppingScrollbar(int relativeX, int relativeY, int height) {
        super(relativeX, relativeY, height);
    }


    @Override
    public EnumActionResult onClicked(int mouseX, int mouseY, EMouseButton button, EEventType type) {
        pressedMouseY = mouseY;
        lastMouseY = mouseY;
        return EnumActionResult.FAIL;
    }

    @Override
    public void onClickedDragging(int mouseX, int mouseY, EMouseButton button, long timePressed) {
        if (lastMouseY != mouseY) {
            lastMouseY = mouseY;
            
            // When positive, cursor moved down. When negative, cursor moved up.
            int change = mouseY - lastMouseY;
            float stepHeight = getStepHeight();
            
            if (Math.abs(change) > stepHeight) {
                // This operation will keep the sign of 'change' the same
                int stepChange = (int) (change / stepHeight);
                step += stepChange;
                getParentComponent().setCurrentStep(step);
            }
        }
    }

    @Override
    public EnumActionResult onReleased(int mouseX, int mouseY, EMouseButton button, EEventType type) {
        lastMouseY = -1;
        return EnumActionResult.FAIL;
    }


    @Override
    public int getActualY() {
        return getBaseY() + (int) (step * getStepHeight());
    }
    
}    
