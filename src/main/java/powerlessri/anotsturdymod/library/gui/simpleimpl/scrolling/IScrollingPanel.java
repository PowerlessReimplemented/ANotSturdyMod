package powerlessri.anotsturdymod.library.gui.simpleimpl.scrolling;

public interface IScrollingPanel {

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
    void setContentStep(int step);
    
}
