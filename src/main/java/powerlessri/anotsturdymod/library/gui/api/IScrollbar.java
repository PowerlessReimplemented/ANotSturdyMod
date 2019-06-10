package powerlessri.anotsturdymod.library.gui.api;

import javafx.stage.Screen;

/**
 * Whenever the scroll bar changed its step, which means content inside the scrolling panel should move,
 * it must manually call {@link IScrollingPanel#setCurrentStep(int)}.
 */
public interface IScrollbar extends IInteractionHandler {

    /**
     * Implementations should <b>NOT</b> override this method, but instead {@link #initialize(GuiScreen, IScrollingPanel)}.
     */
    @Override
    @Deprecated
    default void initialize(Screen gui, IComponent parent) {
        throw new IllegalArgumentException("The parent of am IScrollbar must be an IScrollingPanel!");
    }

    /**
     * Specialized version so that the
     * <p>
     * Implementations only needs to overrides this method.
     * <p>
     * See {@link IComponent#initialize(Screen, IComponent)} for more information.
     */
    void initialize(Screen gui, IScrollingPanel parent);

    // TODO add javadoc

    int getMaximumHeight();

    @Override
    int getHeight();

    int getBaseY();

    @Override
    int getActualY();

}
