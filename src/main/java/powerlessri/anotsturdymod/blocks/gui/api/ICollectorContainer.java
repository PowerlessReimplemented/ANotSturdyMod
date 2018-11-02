package powerlessri.anotsturdymod.blocks.gui.api;

public interface ICollectorContainer extends IContainer {

    /**
     * Add a component to the list. Component may get organized by z-index depends on the result of {@link #acceptsZIndex()}.
     * The given component will get an ID through {@link IComponent#setId(int)}
     */
    void addComponent(IComponent component);

    /**
     * Delete component with the given ID.
     * @param id The given ID.
     */
    void deleteComponent(int id);

}