package powerlessri.anotsturdymod.library.gui.simpleimpl.widget;

import powerlessri.anotsturdymod.library.gui.simpleimpl.scrollable.IScrollableComponent;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * <<p>An invisible rectangle that it does not have a position.</p>
 * <p>
 * Works as a placeholder to displace following components in a way.
 * Usually used in a {@link powerlessri.anotsturdymod.library.gui.simpleimpl.section.FlexingList FlexingList}.
 * </p>
 */
public class Placeholder extends AbstractComponent implements IScrollableComponent {
    
    private static List<Placeholder> heightPlaceholders = new ArrayList<>(64);
    private static List<Placeholder> widthPlaceholders = new ArrayList<>(64);


    /**
     * Create a placeholder with a certain height and 0 width.
     */
    public static Placeholder forHeight(int height) {
        int index = height - 1;
        if (heightPlaceholders.get(index) == null) {
            Placeholder placeholder = new Placeholder(0, height);
            heightPlaceholders.set(index, placeholder);
            return placeholder;
        }
        return heightPlaceholders.get(index);
    }
    
    /**
     * Create a placeholder with a certain width and 0 height.
     */
    public static Placeholder forWidth(int width) {
        int index = width - 1;
        if (widthPlaceholders.get(index) == null) {
            Placeholder placeholder = new Placeholder(width, 0);
            widthPlaceholders.set(index, placeholder);
            return placeholder;
        }
        return widthPlaceholders.get(index);
    }
    

    private int width;
    private int height;

    public Placeholder(int width, int height) {
        super(0, 0);
        this.width = width;
        this.height = height;
    }


    @Override
    public void draw(GuiDrawBackgroundEvent event) {
    }

    @Override
    public void setExpectedY(int y) {
    }

    @Override
    public void setVisibility(boolean visibility) {
    }


    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean isLeafComponent() {
        return false;
    }

}
