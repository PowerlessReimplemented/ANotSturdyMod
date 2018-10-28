package powerlessri.anotsturdymod.blocks.gui.api;

import java.util.List;

public interface IContainer extends IComponent {
    
    List<IComponent> getComponents();

    /**
     * @return Does the container invoke sub-components based on their z-index.
     */
    boolean acceptsZIndex();
    
}
