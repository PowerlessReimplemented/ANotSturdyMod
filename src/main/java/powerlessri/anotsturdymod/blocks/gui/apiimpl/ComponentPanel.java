package powerlessri.anotsturdymod.blocks.gui.apiimpl;

import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.blocks.gui.api.IComponent;
import powerlessri.anotsturdymod.blocks.gui.api.group.IPanel;

import javax.annotation.Nullable;
import java.util.List;

public class ComponentPanel implements IPanel {
    
    protected GuiScreen gui;
    protected IComponent parent;
    
    
    @Override
    public List<IComponent> getComponents() {
        return null;
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
    public GuiScreen getGui() {
        return gui;
    }

    @Nullable
    @Override
    public IComponent getParentComponent() {
        return parent;
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

    }
    
}
