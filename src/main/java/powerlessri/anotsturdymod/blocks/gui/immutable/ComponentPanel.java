package powerlessri.anotsturdymod.blocks.gui.immutable;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.blocks.gui.api.IComponent;
import powerlessri.anotsturdymod.blocks.gui.api.render.IRenderedComponent;
import powerlessri.anotsturdymod.blocks.gui.api.group.IContainer;

public class ComponentPanel extends AbstractComponent implements IContainer<IRenderedComponent> {
    
    protected ImmutableList<IRenderedComponent> components;
    
    public ComponentPanel(int x, int y, ImmutableList<IRenderedComponent> components) {
        super(x, y);
        this.components = components;
    }


    /**
     * <b>WARNING!</b> This implementation returns an instance of {@link ImmutableList}
     */
    @Override
    public ImmutableList<IRenderedComponent> getComponents() {
        return components;
    }

    @Override
    public boolean acceptsZIndex() {
        return false;
    }

    @Override
    public void initialize(GuiScreen gui, IComponent parent) {
        this.gui = gui;
        this.parent = parent;
    }
    
    
    @Override
    public void draw() {
        for (IRenderedComponent component : components) {
            component.draw();
        }
    }
    
}
