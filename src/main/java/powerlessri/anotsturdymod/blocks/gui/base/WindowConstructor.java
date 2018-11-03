package powerlessri.anotsturdymod.blocks.gui.base;

import com.google.common.collect.ImmutableList;
import powerlessri.anotsturdymod.blocks.gui.api.IComponent;
import powerlessri.anotsturdymod.blocks.gui.immutable.ComponentPanel;

import java.util.ArrayList;
import java.util.List;

public class WindowConstructor {

    /**
     * Number of pixels in between two windows.
     */
    public static final int GAP = 24;

    private List<IComponent> windowList = new ArrayList<>();
    private int x;
    private int y;

    public WindowConstructor component(IComponent... windows) {
        for (int i = 0; i < windows.length; i++) {
            windowList.add(windows[i]);
        }
        return this;
    }

    public WindowConstructor pos(int x, int y) {
        setX(x);
        setY(y);
        return this;
    }

    public WindowConstructor setX(int x) {
        this.x = x;
        return this;
    }

    public WindowConstructor setY(int y) {
        this.y = y;
        return this;
    }
    
    public IComponent create() {
        return new ComponentPanel(0, 0, ImmutableList.of());
    }
    
}
