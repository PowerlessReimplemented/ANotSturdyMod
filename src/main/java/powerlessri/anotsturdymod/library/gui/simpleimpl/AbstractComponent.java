package powerlessri.anotsturdymod.library.gui.simpleimpl;

import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.library.gui.api.EDisplayMode;
import powerlessri.anotsturdymod.library.gui.api.IComponent;

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
        this.gui = gui;
        this.parent = parent;
        if (parent.isRootComponent()) {
            this.root = parent;
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
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getActualX() {
        return absX;
    }

    @Override
    public int getActualY() {
        return absY;
    }

    /**
     * X position of the bottom right corner.
     */
    public int getActualXBR() {
        return getActualX() + getWidth();
    }

    /**
     * Y position of the bottom right corner.
     */
    public int getActualYBR() {
        return getActualY() + getHeight();
    }

    @Override
    public boolean isPointInside(int x, int y) {
        return x >= getActualX() && x <= getActualXBR() &&
                y >= getActualY() && y <= getActualYBR();
    }

    
    /**
     * <b>WARNING: </b> This method does not do what its name says.
     * Recalculate absolute position of the component.
     */
    @Override
    @Deprecated
    public void forceActualPosition(int x, int y) {
        recalculatePosition();
    }
    
    private void recalculatePosition() {
        absX = parent.getActualX() + x;
        absY = parent.getActualY() + y;
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
        return EDisplayMode.ALL;
    }

}
