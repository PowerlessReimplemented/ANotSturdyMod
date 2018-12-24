package powerlessri.anotsturdymod.library.gui.simpleimpl;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.api.IContainer;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.varia.general.Utils;

import javax.annotation.Nullable;
import java.util.List;

public class ComponentRoot implements IContainer {

    private ImmutableList<IContainer<IComponent>> windows;
    private ImmutableList<IComponent> flattened;
    private ImmutableList<IComponent> leaves;
    private GuiScreen gui;

    private EventManager eventManager;

    public ComponentRoot(GuiScreen gui, ImmutableList<IContainer<IComponent>> windows) {
        this.gui = gui;
        this.windows = windows;
    }

    private void initializeAllComponents() {
        for (IComponent component : windows) {
            component.initialize(gui, this);
        }
    }


    @Override
    public void draw(GuiDrawBackgroundEvent event) {
        for (IContainer<IComponent> window : windows) {
            window.draw(event);
        }
    }


    public EventManager getEventManager() {
        return eventManager;
    }

    @Override
    public List<IContainer<IComponent>> getComponents() {
        return windows;
    }


    @Override
    public boolean acceptsZIndex() {
        return false;
    }

    @Override
    public void initialize(GuiScreen gui, IComponent parent) {
        this.gui = gui;
        if (parent != null) {
            Utils.getLogger().warn("Possibly effective parent component was passed to ComponentRoot.");
        }

        this.flattened = ComponentStructureProjector.flatten(windows);
        this.leaves = ComponentStructureProjector.leaves(flattened);

        this.eventManager = EventManager.forLeaves(leaves);

        this.initializeAllComponents();
    }

    @Override
    public GuiScreen getGui() {
        return gui;
    }

    @Nullable
    @Override
    public IComponent getParentComponent() {
        return this;
    }

    @Override
    public boolean isLeafComponent() {
        return false;
    }

    @Override
    public boolean isRootComponent() {
        return true;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public int setId(int id) {
        return 0;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public int getWidth() {
        return gui.width;
    }

    @Override
    public int getHeight() {
        return gui.height;
    }

    @Override
    public int getActualX() {
        return 0;
    }

    @Override
    public int getActualY() {
        return 0;
    }

    @Override
    public boolean isPointInside(int x, int y) {
        return true;
    }

    @Override
    public int getZIndex() {
        return 0;
    }

    @Override
    public void setZIndex(int zIndex) {
    }

    @Override
    public boolean isVisible() {
        return true;
    }

}
