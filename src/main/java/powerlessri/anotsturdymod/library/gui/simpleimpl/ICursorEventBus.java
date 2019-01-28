package powerlessri.anotsturdymod.library.gui.simpleimpl;

import powerlessri.anotsturdymod.library.gui.api.EMouseButton;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.integration.ContextGuiUpdate;
import powerlessri.anotsturdymod.library.gui.simpleimpl.events.IFocusListener;
import powerlessri.anotsturdymod.library.gui.simpleimpl.events.IHoveringListener;
import powerlessri.anotsturdymod.library.gui.simpleimpl.events.IInteractionHandler;

public interface ICursorEventBus {

    IComponent getFocusedComponent();

    IFocusListener getCurrentFocusListener();

    IComponent getHoveringComponent();

    IHoveringListener getCurrentHoveringListener();

    IInteractionHandler getLastClickedHandler();

    default boolean isFocusInteractable() {
        return getFocusedComponent() instanceof IInteractionHandler;
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
