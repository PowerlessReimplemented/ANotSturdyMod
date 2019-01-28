package powerlessri.anotsturdymod.library.gui.simpleimpl;

import powerlessri.anotsturdymod.library.gui.api.EMouseButton;

public interface IGuiEventTrigger {

    void fireClickedEvent(int mx, int my, EMouseButton button);

    void fireDraggingEvent(int mx, int my, EMouseButton button, long timePressed);

    void fireHoveringEvent(int mx, int my);

    void fireReleasedEvent(int mx, int my, EMouseButton button);

}
