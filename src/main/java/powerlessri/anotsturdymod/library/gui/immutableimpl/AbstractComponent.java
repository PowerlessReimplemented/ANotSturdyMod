package powerlessri.anotsturdymod.library.gui.immutableimpl;

import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.library.gui.api.EDisplayMode;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.varia.general.Utils;

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

    public int getAbsoluteX2() {
        return absX + getWidth();
    }

    public int getAbsoluteY2() {
        return absY + getHeight();
    }

    @Override
    public boolean isPointInside(int x, int y) {
        return x >= getAbsoluteX() && x <= getAbsoluteX2() &&
                y >= getAbsoluteY() && y <= getAbsoluteY2();
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
        Utils.getLogger().info("recal x=" + absX + " y=" + absY);
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
    public EDisplayMode getDisplay() {
        return EDisplayMode.CUSTOM;
    }

    
    // Event handlers

    public void onBasePositionChanged(int oldX, int oldY, int newX, int newY) {
    }
    
}
