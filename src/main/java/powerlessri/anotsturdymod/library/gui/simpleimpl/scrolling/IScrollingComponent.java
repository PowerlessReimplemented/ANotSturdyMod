package powerlessri.anotsturdymod.library.gui.simpleimpl.scrolling;

import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;

public interface IScrollingComponent extends IComponent {
    
    /**
     * Equivalent to {@code draw(GuiDrawBackgroundEvent event, this.getActualY())}.
     * 
     * @deprecated The position of the component is fixed, not suitable if it's constantly moving as the scroll bar moves.
     * @see #draw(GuiDrawBackgroundEvent, int) 
     */
    @Override
    @Deprecated
    void draw(GuiDrawBackgroundEvent event);

    /**
     * A specialized version of {@link IComponent#draw(GuiDrawBackgroundEvent)}.
     */
    void draw(GuiDrawBackgroundEvent event, int y);
    
}
