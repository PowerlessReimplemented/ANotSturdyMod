package powerlessri.anotsturdymod.library.gui.api;

public interface ICollectorContainer<T extends IComponent> extends IContainer<T> {

    /**
     * Add a component to the list. Component may get organized by z-index depends on the result of {@link #acceptsZIndex()}.
     * The given component will get an ID through {@link IComponent#setId(int)}
     */
    void addComponent(T component);

    /**
     * Delete component with the given ID.
     * @param id The given ID.
     */
    void deleteComponent(int id);

}