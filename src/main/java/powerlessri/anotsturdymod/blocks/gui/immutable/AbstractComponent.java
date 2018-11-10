package powerlessri.anotsturdymod.blocks.gui.immutable;

import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.blocks.gui.api.render.EDisplayMode;
import powerlessri.anotsturdymod.blocks.gui.api.IComponent;

import javax.annotation.Nullable;

public abstract class AbstractComponent implements IComponent {

    protected GuiScreen gui;
    protected IComponent root;
    protected IComponent parent;

    protected final int x;
    protected final int y;
    protected int z;
    
    private boolean useZ;
    private int absX;
    private int absY;
    private int id;

    public AbstractComponent(int relativeX, int relativeY) {
        this(relativeX, relativeY, false);
    }

    public AbstractComponent(int relativeX, int relativeY, boolean useZ) {
        this.x = relativeX;
        this.y = relativeY;
        this.useZ = useZ;
    }
    

    @Override
    public void initialize(GuiScreen gui, IComponent parent) {
        int oldX = getAbsoluteX();
        int oldY = getAbsoluteY();
        
        this.gui = gui;
        this.parent = parent;
        if (parent.isRootComponent()) {
            this.root = parent;
        }
        recalculatePosition();
        
        // Events
        onBasePositionChanged(oldX, oldY, getAbsoluteX(), getAbsoluteY());
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
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getAbsoluteX() {
        return absX;
    }

    @Override
    public int getAbsoluteY() {
        return absY;
    }

    @Override
    public boolean isPointInBound(int x, int y) {
        return x >= getAbsoluteX() && x <= getAbsoluteX() + getWidth() &&
                y >= getAbsoluteY() && y <= getAbsoluteY() + getHeight();
    }

    
    /**
     * Recalculate absolute position of the component.
     */
    @Override
    public void resetAbsolutePosition(int x, int y) {
        recalculatePosition();
    }
    
    private void recalculatePosition() {
        absX = parent.getAbsoluteX() + x;
        absY = parent.getAbsoluteY() + y;
    }


    @Override
    public int getZIndex() {
        return z;
    }

    @Override
    public void setZIndex(int zIndex) {
        if (useZ) {
            z = zIndex;
        }
    }
    
    
    @Override
    public EDisplayMode getDisplay() {
        return EDisplayMode.CUSTOM;
    }


    public void onBasePositionChanged(int oldX, int oldY, int newX, int newY) {
    }
    
}
