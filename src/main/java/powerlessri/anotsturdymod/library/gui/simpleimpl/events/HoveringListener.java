package powerlessri.anotsturdymod.library.gui.simpleimpl.events;

public interface HoveringListener {

    void onCursorEnter(HoveringEvent.Enter event);

    void onCursorLeave(HoveringEvent.Leave event);

    void update(HoveringEvent.Update event);

}
