package powerlessri.anotsturdymod.blocks.gui.apiimpl;

import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.blocks.gui.api.ICollectorContainer;
import powerlessri.anotsturdymod.blocks.gui.api.IComponent;

import javax.annotation.Nullable;
import java.util.List;

public class ComponentRoot implements ICollectorContainer {
    
    private List<IComponent> windows;
    private int lastComponentId = -1;
    
    private GuiScreen gui;

    public ComponentRoot(GuiScreen gui) {
        this.gui = gui;
    }


    @Override
    public void addComponent(IComponent window) {
        window.setId(++lastComponentId);
        windows.add(window);
    }

    @Override
    public void deleteComponent(int id) {
    }

    @Override
    public List<IComponent> getComponents() {
        return windows;
    }

    @Override
    public boolean acceptsZIndex() {
        return false;
    }

    @Override
    public void initialize(GuiScreen gui, IComponent parent) {
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
    public int getAbsoluteX() {
        return 0;
    }

    @Override
    public int getAbsoluteY() {
        return 0;
    }

    @Override
    public void resetAbsolutePosition(int x, int y) {
    }

    @Override
    public int getZIndex() {
        return 0;
    }

    @Override
    public void setZIndex(int zIndex) {
    }

    @Override
    public void draw() {
        for (IComponent window : windows) {
            window.draw();
        }
    }
    
}
