package powerlessri.anotsturdymod.library.gui.simpleimpl.scrollable;

import net.minecraft.util.ActionResultType;
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
    public ActionResultType onClicked(int mouseX, int mouseY, EMouseButton button, EEventType type) {
        pressedMouseY = mouseY;
        lastMouseY = mouseY;
        return ActionResultType.FAIL;
    }

    @Override
    public void onClickedDragging(int mouseX, int mouseY, EMouseButton button, long timePressed) {
        if (lastMouseY != mouseY) {
            lastMouseY = mouseY;

            // When positive, mouse moved down. When negative, mouse moved up.
            int change = mouseY - lastMouseY;
            float stepHeight = getStepHeight();

            if (Math.abs(change) > stepHeight) {
                // Note that this operation will keep the sign of variable 'change'
                int stepChange = (int) (change / stepHeight);
                step += stepChange;
                getParentComponent().setCurrentStep(step);
            }
        }
    }

    @Override
    public ActionResultType onReleased(int mouseX, int mouseY, EMouseButton button, EEventType type) {
        lastMouseY = -1;
        return ActionResultType.FAIL;
    }

    @Override
    public int getActualY() {
        return getBaseY() + (int) (step * getStepHeight());
    }

}    
