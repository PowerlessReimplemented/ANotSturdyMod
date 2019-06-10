package powerlessri.anotsturdymod.library.gui.api;

import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;

public interface IScrollableComponent extends IComponent {

    /**
     * Equivalent to {@code draw(GuiDrawBackgroundEvent event, this.getActualY())}.
     *
     * @see #draw(GuiDrawBackgroundEvent, int)
     * @deprecated The position of the component is fixed, not suitable if it's constantly moving as the scroll bar moves.
     */
    @Override
    @Deprecated
    void draw(GuiDrawBackgroundEvent event);

    /**
     * A specialized version of {@link IComponent#draw(GuiDrawBackgroundEvent)}.
     */
    void draw(GuiDrawBackgroundEvent event, int y);

}
