package powerlessri.anotsturdymod.library.gui.simpleimpl;

import powerlessri.anotsturdymod.library.gui.api.EMouseButton;

public class EventDispatcher implements IGuiEventTrigger {

    private CursorEventManager cursorDelegate;

    public EventDispatcher(CursorEventManager cursorDelegate) {
        this.cursorDelegate = cursorDelegate;
    }


    @Override
    public void fireClickedEvent(int mx, int my, EMouseButton button) {
        cursorDelegate.fireClickedEvent(mx, my, button);
    }

    @Override
    public void fireDraggingEvent(int mx, int my, EMouseButton button, long timePressed) {
        cursorDelegate.fireDraggingEvent(mx, my, button, timePressed);
    }

    @Override
    public void fireHoveringEvent(int mx, int my) {
        cursorDelegate.fireHoveringEvent(mx, my);
    }

    @Override
    public void fireReleasedEvent(int mx, int my, EMouseButton button) {
        cursorDelegate.fireReleasedEvent(mx, my, button);
    }

}
