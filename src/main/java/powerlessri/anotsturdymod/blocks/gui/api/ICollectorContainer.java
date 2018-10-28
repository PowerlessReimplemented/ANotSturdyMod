package powerlessri.anotsturdymod.blocks.gui.api;

public interface ICollectorContainer extends IContainer {

    /**
     * Add a component to the list. Component may get organized by z-index depends on the result of {@link #acceptsZIndex()}.
     *
     * @param component
     */
    void addComponent(IComponent component);

    void deleteComponent(int id);

}
