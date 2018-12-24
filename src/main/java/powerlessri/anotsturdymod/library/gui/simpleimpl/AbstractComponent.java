package powerlessri.anotsturdymod.library.gui.simpleimpl;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import powerlessri.anotsturdymod.library.gui.api.EDisplayMode;
import powerlessri.anotsturdymod.library.gui.api.IComponent;

import javax.annotation.Nullable;

public abstract class AbstractComponent implements IComponent {

    protected GuiScreen gui;
    protected IComponent root;
    protected IComponent parent;

    protected final int relativeX;
    protected final int relativeY;
    protected int z;
    
    private boolean useZ;
    private int absX;
    private int absY;
    private int id;

    public AbstractComponent(int relativeX, int relativeY) {
        this(relativeX, relativeY, false);
    }

    public AbstractComponent(int relativeX, int relativeY, boolean useZ) {
        this.relativeX = relativeX;
        this.relativeY = relativeY;
        this.useZ = useZ;
    }
    

    @Override
    public void initialize(GuiScreen gui, IComponent parent) {
        this.gui = gui;
        this.parent = parent;
        if (parent.isRootComponent()) {
            root = parent;
        } else if (parent instanceof AbstractComponent) {
            root = ((AbstractComponent) parent).root;
        }
        recalculatePosition();
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
    public boolean isRootComponent() {
        return false;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int setId(int id) {
        int oldID = id; 
        this.id = id;
        return oldID;
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
    public int getActualX() {
        return absX;
    }

    @Override
    public int getActualY() {
        return absY;
    }

    @Override
    public boolean isPointInside(int x, int y) {
        return x >= getActualX() && x <= getActualXRight() &&
                y >= getActualY() && y <= getActualYBottom();
    }

    public ComponentRoot getFunctionalRoot() {
        return (ComponentRoot) root;
    }
    
    
    private void recalculatePosition() {
        absX = parent.getActualX() + relativeX;
        absY = parent.getActualY() + relativeY;
    }


    @Override
    public int getZIndex() {
        return z;
    }

    @Override
    public void setZIndex(int z) {
        this.z = z;
    }
    
    
    @Override
    public boolean isVisible() {
        return true;
    }

}
