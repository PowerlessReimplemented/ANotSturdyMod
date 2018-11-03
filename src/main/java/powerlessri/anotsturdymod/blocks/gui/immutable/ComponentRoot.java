package powerlessri.anotsturdymod.blocks.gui.immutable;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.blocks.gui.api.EDisplayMode;
import powerlessri.anotsturdymod.blocks.gui.api.ICollectorContainer;
import powerlessri.anotsturdymod.blocks.gui.api.IComponent;
import powerlessri.anotsturdymod.blocks.gui.api.IContainer;

import javax.annotation.Nullable;
import java.util.List;

public class ComponentRoot implements IContainer {
    
    private ImmutableList<IComponent> windows;
    private int lastComponentId = -1;
    
    private GuiScreen gui;

    public ComponentRoot(GuiScreen gui, ImmutableList<IComponent> windows) {
        this.gui = gui;
        this.windows = windows;
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
        this.gui = gui;
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
    public EDisplayMode getDisplay() {
        return EDisplayMode.CUSTOM;
    }
    
    @Override
    public void draw() {
        for (IComponent window : windows) {
            if (window.getDisplay() != EDisplayMode.NONE) {
                window.draw();
            }
        }
    }
    
}
