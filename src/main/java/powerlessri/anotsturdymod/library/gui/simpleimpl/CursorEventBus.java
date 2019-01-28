package powerlessri.anotsturdymod.library.gui.simpleimpl;

import powerlessri.anotsturdymod.library.gui.api.EMouseButton;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.integration.ContextGuiUpdate;
import powerlessri.anotsturdymod.library.gui.simpleimpl.events.FocusListener;
import powerlessri.anotsturdymod.library.gui.simpleimpl.events.HoveringListener;
import powerlessri.anotsturdymod.library.gui.simpleimpl.events.InteractionHandler;

public interface CursorEventBus {

    IComponent getFocusedComponent();

    FocusListener getCurrentFocusListener();

    IComponent getHoveringComponent();

    HoveringListener getCurrentHoveringListener();

    InteractionHandler getLastClickedHandler();

    default boolean isFocusInteractable() {
        return getFocusedComponent() instanceof InteractionHandler;
    }

    void update(ContextGuiUpdate context);

    void handleMouseClick(int x, int y, EMouseButton button);

    void handleMouseRelease(int x, int y, EMouseButton button);

    boolean supportsBubbling();

    boolean supportsCapture();

    void enableBubbling();

    void disableBubbling();

    void enableCapture();

    void disableCapture();

}
