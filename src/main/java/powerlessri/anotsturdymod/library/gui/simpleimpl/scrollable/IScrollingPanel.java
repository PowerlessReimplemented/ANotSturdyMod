package powerlessri.anotsturdymod.library.gui.simpleimpl.scrollable;

import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.api.IContainer;

public interface IScrollingPanel extends IContainer<IComponent> {

    /**
     * Number of rows of components that are available.
     */
    int getTotalSteps();

    /**
     * Get index of the first component gets drawn.
     * @return Index which starts at 0
     */
    int getCurrentStep();

    /**
     * Set the index of first component which gets drawn.
     * <p>In other words, shift the viewport to the given index.</p>
     * @param step Target index, starts at 0
     */
    void setCurrentStep(int step);

    /**
     * Scale factor of the height of all contents, to the display height of the panel.
     */
    float getContentScaleFactor();
    
}
