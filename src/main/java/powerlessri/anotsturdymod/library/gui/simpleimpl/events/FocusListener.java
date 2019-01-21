package powerlessri.anotsturdymod.library.gui.simpleimpl.events;

public interface FocusListener {

    /**
     *
     */
    void onFocus(FocusEvent.On event);

    /**
     * Triggers when
     * <p>
     *     Before the different focus listener which got focused was notified,
     * </p>
     */
    void onUnfocus(FocusEvent.Off event);

    /**
     * Triggers every tick on the component that is being focused.
     *
     * @param event
     */
    void update(FocusEvent.Update event);

}
