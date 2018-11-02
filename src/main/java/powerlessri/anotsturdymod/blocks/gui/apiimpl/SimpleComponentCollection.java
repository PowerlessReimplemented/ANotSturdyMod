package powerlessri.anotsturdymod.blocks.gui.apiimpl;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.blocks.gui.api.ICollectorContainer;
import powerlessri.anotsturdymod.blocks.gui.api.IComponent;
import powerlessri.anotsturdymod.blocks.gui.api.group.IPanel;
import powerlessri.anotsturdymod.blocks.gui.base.TextureWrapper;
import powerlessri.anotsturdymod.varia.inventory.GuiUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Simple {@link IPanel} implementation that: <br />
 * <ol>
 *     <li>Ignores component's Z index, drawing order depends on order gets added into panel.</li>
 * </ol>
 */
public class SimpleComponentCollection implements ICollectorContainer, Iterable<IComponent> {

    private TextureWrapper background;
    
    protected GuiScreen gui;
    protected IComponent parent;
    
    protected int id;
    
    protected int relativeX;
    protected int relativeY;
    protected int z;
    
    private int baseX;
    private int baseY;
    
    private List<IComponent> components;
    private int lastComponentID;
    
    public SimpleComponentCollection(ResourceLocation background, int startX, int startY, int width, int height, int componentX, int componentY) {
        this(TextureWrapper.of(background, startX, startY, width, height), componentX, componentY);
    }
    
    public SimpleComponentCollection(TextureWrapper background, int x, int y) {
        this.background = background;
        
        this.relativeX = x;
        this.relativeY = y;
        this.recalculateBasePosition();

        this.components = new ArrayList<>();
        this.lastComponentID = 0;
    }


    @Override
    public void addComponent(IComponent component) {
        reInitializeComponent(component);
        component.setId(++lastComponentID);
        
        int z = component.getZIndex();
        int last = 0;
        for (int i = 0; i < components.size(); i++) {
            int compZ = components.get(i).getZIndex();
            if (compZ == z) {
                last = i;
            } else if (compZ > z) {
                components.add(last, component);
                last = -1;
                break;
            }
        }
        
        if (last != -1) {
            components.add(component);
        }
    }

    @Override
    public void deleteComponent(int id) {
        if (id < components.size()) {
            components.set(id, null);
        }
    }

    @Override
    public List<IComponent> getComponents() {
        return components;
    }

    @Override
    public boolean acceptsZIndex() {
        return true;
    }

    @Override
    public void initialize(GuiScreen gui, IComponent parent) {
        this.gui = gui;
        this.parent = parent;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int setId(int id) {
        int oldId = getId();
        this.id = id;
        return oldId;
    }

    
    @Override
    public int getX() {
        return relativeX;
    }

    @Override
    public int getY() {
        return relativeY;
    }

    @Override
    public int getAbsoluteX() {
        return baseX;
    }

    @Override
    public int getAbsoluteY() {
        return baseY;
    }

    @Override
    public void resetAbsolutePosition(int x, int y) {
        baseX = x;
        baseY = y;
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
    public int getZIndex() {
        return z;
    }

    @Override
    public void setZIndex(int zIndex) {
        z = zIndex;
    }

    
    @Override
    public void draw() {
        GuiUtils.resetGuiGlStates();

        if (background != null) {
            background.draw(gui, baseX, baseY);
        }
        
        for (IComponent component : components) {
            component.draw();
        }
    }


    private void reInitializeComponent(IComponent component) {
        component.initialize(gui, this);
        component.resetAbsolutePosition(relativeX + component.getX(), relativeY + component.getY());
    }
    
    protected void recalculateBasePosition() {
        baseX = parent.getX() + relativeX;
        baseY = parent.getY() + relativeY;
    }
    
    
    @Override
    public Iterator<IComponent> iterator() {
        return components.iterator();
    }
    
}
