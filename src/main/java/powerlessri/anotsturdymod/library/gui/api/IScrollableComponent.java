package powerlessri.anotsturdymod.library.gui.api;

public interface IScrollableComponent extends IComponent {

    /**
     * Called whenever the content offset was changed.\
     * <p>If the parameter y is less than 0, it means the component is moved out of the range</p>
     *
     * @param y The expected y value to be used to display
     */
    void setExpectedY(int y);

    /**
     * Called when the component is moved out/in of the visible range of the parent scrollable panel.
     * <p>
     * When this method is called, the component should remove itself from the list of the event manager so that event
     * passing works correctly.
     * </p>
     */
    void setVisibility(boolean visibility);

    default void inverseVisibility() {
        setVisibility(!isVisible());
    }

}
