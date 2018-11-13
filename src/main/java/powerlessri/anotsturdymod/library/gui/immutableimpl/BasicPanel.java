package powerlessri.anotsturdymod.library.gui.immutableimpl;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.api.IContainer;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;

public class BasicPanel extends AbstractComponent implements IContainer<IComponent> {
    
    protected ImmutableList<IComponent> components;
    
    public BasicPanel(int x, int y, ImmutableList<IComponent> components) {
        super(x, y);
        this.components = components;
    }


    /**
     * <b>WARNING!</b> This implementation returns an instance of {@link ImmutableList}
     */
    @Override
    public ImmutableList<IComponent> getComponents() {
        return components;
    }

    @Override
    public boolean acceptsZIndex() {
        return false;
    }

    @Override
    public void initialize(GuiScreen gui, IComponent parent) {
        super.initialize(gui, parent);
        for (IComponent component : components) {
            component.initialize(gui, this);
        }
    }
    
    
    @Override
    public void draw(GuiDrawBackgroundEvent event) {
        for (IComponent component : components) {
            component.tryDraw(event);
        }
    }


    @Override
    public boolean isLeafComponent() {
        return true;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

}
