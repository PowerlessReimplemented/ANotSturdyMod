package powerlessri.anotsturdymod.library.gui.simpleimpl.scrollable;

import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.api.IInteractionHandler;

/**
 * <p>
 * Whenever the scroll bar changed its step, which means content inside the scrolling panel should move,
 * it must manually call {@link IScrollingPanel#setCurrentStep(int)}.
 * </p>
 */
public interface IScrollBar extends IInteractionHandler {

    /**
     * Implementations should <b>NOT</b> override this method, but instead {@link #initialize(GuiScreen, IScrollingPanel)}.
     */
    @Override
    @Deprecated
    default void initialize(GuiScreen gui, IComponent parent) {
        throw new IllegalArgumentException("The parent of am IScrollBar must be an IScrollingPanel!");
    }

    /**
     * Specialized version so that the
     *
     * <p>Implementations only needs to overrides this method.</p>
     * <p>See {@link IComponent#initialize(GuiScreen, IComponent)} for more information. </p>
     */
    void initialize(GuiScreen gui, IScrollingPanel parent);


    /**
     * The maximimum height that the scroll bar can have.
     * Should always greater or equal to {@link #getHeight()}.
     */
    int getMaximumHeight();

    /**
     * The current height that is scale down to be able to slided.
     * Should always be less than or equal to {@link #getMaximumHeight()} and greater than 0.
     */
    @Override
    int getHeight();

    /**
     * The y value of top left corner of the outer box, which is fixed.
     * @see IComponent#getActualY()
     */
    int getBaseY();

    /**
     * The y value of the movable object that is being displayed to the player.
     */
    @Override
    int getActualY();


    /**
     * Whitespace gap on top and bottom of the scroll bar, in pixels.
     * @return Number of pixels of margin top and margin bottom
     */
    int getVerticalMargin();

}
