package powerlessri.anotsturdymod.blocks.gui.apiimpl;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.blocks.gui.api.IComponent;
import powerlessri.anotsturdymod.blocks.gui.api.group.IPanel;
import powerlessri.anotsturdymod.varia.inventory.GuiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple {@link IPanel} implementation that: <br />
 * <ol>
 *     <li>Ignores component's Z index, drawing order depends on order gets added into panel.</li>
 *     <li>Does not support deleting components.</li>
 * </ol>
 */
public class SimpleGuiPanel implements IPanel {
    
    protected GuiScreen gui;
    private ResourceLocation background;
    
    protected int baseX;
    protected int baseY;
    
    protected List<ISubComponent> components = new ArrayList<>();
    
    protected int lastComponentID;
    
    public SimpleGuiPanel(GuiScreen gui, ResourceLocation background, int x, int y) {
        this.gui = gui;
        this.baseX = x;
        this.baseY = y;
        
        this.lastComponentID = 0;
    }
    
    
    @Override
    public GuiScreen getGui() {
        return gui;
    }
    
    @Override
    public List<IComponent> getComponents() {
        return components;
    }

    @Override
    public void addComponent(ISubComponent component) {
        addComponent(component, 0);
    }

    @Override
    public void addComponent(ISubComponent component, int zIndex) {
        reInitializeComponent(component);
        component.setId(++lastComponentID);
        components.add(component);
    }

    @Override
    public IComponent deleteComponent(int id) {
        return null;
    }

    @Override
    public void setPosition(int x, int y) {
        baseX = x;
        baseY = y;

        for (IComponent component : components) {
            reInitializeComponent(component);
        }
    }

    @Override
    public int getX() {
        return baseX;
    }

    @Override
    public int getY() {
        return baseY;
    }

    @Override
    public void draw(int x, int y) {
        GuiUtils.resetGuiGlStates();
        
        for (IComponent component : components) {
            component.draw();
        }
    }


    private void reInitializeComponent(IComponent component) {
        component.resetParents(gui, this);
        component.resetPosition(baseX + component.getRelativeX(), baseY + component.getRelativeY());
    }
    
}
