package powerlessri.anotsturdymod.library.gui.simpleimpl;

public abstract class Event {

    // For subclass compatibility
    protected boolean canceled = false;

    /**
     * Cancel the event.
     * <p>
     * Once the event is canceled, it should not be revertible, except when the event class internally decides to
     * </p>
     */
    public void cancel() {
        this.canceled = true;
    }

    public boolean isCanceled() {
        return this.canceled;
    }

}
