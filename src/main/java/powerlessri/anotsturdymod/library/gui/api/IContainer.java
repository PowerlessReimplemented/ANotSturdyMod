package powerlessri.anotsturdymod.library.gui.api;

import java.util.List;

public interface IContainer<T extends IComponent> extends IComponent {
    
    List<T> getComponents();

    /**
     * @return Does the container invoke sub-components based on their z-index.
     */
    boolean acceptsZIndex();
    
}
