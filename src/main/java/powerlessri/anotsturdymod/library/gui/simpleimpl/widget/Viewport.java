package powerlessri.anotsturdymod.library.gui.simpleimpl.widget;

import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.api.IContainer;
import powerlessri.anotsturdymod.library.gui.integration.ContextGuiDrawing;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractComponent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.ComponentStructureProjector;

import java.util.List;

public class Viewport extends AbstractComponent {

    private int width;
    private int height;

    private List<IComponent> content;

    public Viewport(int relativeX, int relativeY, int width, int height) {
        super(relativeX, relativeY);
        this.width = width;
        this.height = height;
    }


    public void bind(IContainer<IComponent> container) {
        this.bind(container.getComponents());
    }

    /**
     * <p>
     * The method assumes the positioning logic of the components are the same. This means the trait of parent does not affect the distance
     * from leftmost/topmost and rightmost/bottommost component.
     * </p>
     */
    public void bind(List<IComponent> newContent) {
        int width = ComponentStructureProjector.findMinimumWidth(newContent);
        int height = ComponentStructureProjector.findMinimumHeight(newContent);
        if (width > this.getWidth() || height > this.getHeight()) {
            throw new IllegalArgumentException("");
        }

        for (IComponent component : newContent) {
            component.initialize(gui, this);
        }
        this.content = newContent;
    }

    @Override
    public void draw(ContextGuiDrawing event) {
        for (IComponent component : content) {
            component.draw(event);
        }
    }


    public List<IComponent> getCurrentContent() {
        return content;
    }

    // This is not an container since it is meant to be a reference to the actual container
    @Override
    public boolean isLeafComponent() {
        return false;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

}
