package powerlessri.anotsturdymod.library.gui.simpleimpl.scrollable;

import powerlessri.anotsturdymod.library.gui.api.EMouseButton;

public class SlidingScrollbar extends ScrollbarThinStyled {
    
    private int offset;
    
    public SlidingScrollbar(int relativeX, int relativeY, int height) {
        super(relativeX, relativeY, height);
    }


    @Override
    public void onClickedDragging(int mouseX, int mouseY, EMouseButton button, long timePressed) {
        offset = limitToRange(mouseY - getBaseY());

        int step = (int) (offset / getStepHeight());
        IScrollingPanel parent = getParentComponent();
        if (step != parent.getCurrentStep()) {
            parent.setCurrentStep(step);
        }
    }

    private int limitToRange(int n) {
        if (n < 0) {
            return 0;
        }
        int bottom = getMaximumHeight() - getHeight();
        if (n >= bottom) {
            return bottom;
        }
        return n;
    }

    
    @Override
    public int getActualY() {
        return getBaseY() + offset;
    }

}
