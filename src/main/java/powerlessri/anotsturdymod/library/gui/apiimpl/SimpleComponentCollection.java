package powerlessri.anotsturdymod.library.gui.apiimpl;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.library.gui.api.EDisplayMode;
import powerlessri.anotsturdymod.library.gui.api.IInteractionHandler;
import powerlessri.anotsturdymod.library.gui.api.ICollectorContainer;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.TextureWrapper;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.varia.general.GuiUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Simple {@link ICollectorContainer} implementation that: <br />
 * <ol>
 *     <li>Ignores component's Z index, drawing order depends on order gets added into panel.</li>
 * </ol>
 * 
 * @deprecated Immutable component collector are usually a better choice, as they won't cause NullPointerExceptions. <br />
 *             This implementation of a mutable component collection also have some performence issues, e.g. search
 *             through all components every updates to sort by z-index.
 */
public class SimpleComponentCollection implements ICollectorContainer<IInteractionHandler>, Iterable<IInteractionHandler> {

    private TextureWrapper background;
    
    protected GuiScreen gui;
    protected IComponent parent;
    
    protected int id;
    
    protected int relativeX;
    protected int relativeY;
    protected int z;
    
    private int baseX;
    private int baseY;
    
    private List<IInteractionHandler> components;
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
    public void addComponent(IInteractionHandler component) {
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
    public List<IInteractionHandler> getComponents() {
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
    public int getWidth() {
        return background.width;
    }

    @Override
    public int getHeight() {
        return background.height;
    }

    /**
     * X position of top-left corner.
     */
    @Override
    public int getActualX() {
        return baseX;
    }

    /**
     * Y postition of top-left corner.
     */
    @Override
    public int getActualY() {
        return baseY;
    }

    /**
     * X position of bottom-right corner.
     */
    public int getAbsoluteX2() {
        return getActualX() + getWidth();
    }

    /**
     * Y position of bottom-right corner.
     */
    public int getAbsoluteY2() {
        return getActualY() + getHeight();
    }

    @Override
    public boolean isPointInside(int x, int y) {
        return x >= getActualX() && x <= getAbsoluteX2() &&
                y >= getActualY() && y <= getAbsoluteY2();
    }

    @Override
    public void forceActualPosition(int x, int y) {
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
    public boolean isLeafComponent() {
        return false;
    }

    @Override
    public boolean isRootComponent() {
        return false;
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
    public EDisplayMode getDisplay() {
        return EDisplayMode.CUSTOM;
    }


    @Override
    public void draw(GuiDrawBackgroundEvent event) {
        GuiUtils.useTextureGLStates();

        if (background != null) {
            background.draw(gui, baseX, baseY);
        }
        
        for (IInteractionHandler component : components) {
            component.draw(event);
        }
    }


    private void reInitializeComponent(IInteractionHandler component) {
        component.initialize(gui, this);
        component.forceActualPosition(relativeX + component.getX(), relativeY + component.getY());
    }
    
    protected void recalculateBasePosition() {
        baseX = parent.getX() + relativeX;
        baseY = parent.getY() + relativeY;
    }
    
    
    @Override
    public Iterator<IInteractionHandler> iterator() {
        return components.iterator();
    }
    
}
