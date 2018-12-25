package powerlessri.anotsturdymod.library.gui.simpleimpl.widget;

import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractComponent;

public abstract class Label extends AbstractComponent {

    private int width;
    private int height;

    public Label(int relativeX, int relativeY, int width, int height) {
        super(relativeX, relativeY);
        this.width = width;
        this.height = height;
    }
    

    @Override
    public boolean isLeafComponent() {
        return false;
    }
    
    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
    
}