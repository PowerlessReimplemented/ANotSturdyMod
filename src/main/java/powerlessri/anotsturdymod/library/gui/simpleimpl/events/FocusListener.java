package powerlessri.anotsturdymod.library.gui.simpleimpl.events;

public interface FocusListener {

    void onFocus(FocusEvent.On event);

    void onUnfocus(FocusEvent.Off event);

    void update(FocusEvent.Update event);

}
