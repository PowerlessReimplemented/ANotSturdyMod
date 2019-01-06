package powerlessri.anotsturdymod.library.gui.simpleimpl.scrollable;

import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.library.gui.simpleimpl.button.Button;

import javax.annotation.Nullable;

public abstract class Scrollbar extends Button implements IScrollbar {
    
    private IScrollingPanel parent;

    public Scrollbar(int relativeX, int relativeY, int width, int height) {
        super(relativeX, relativeY, width, height);
    }

    
    @Override
    public void initialize(GuiScreen gui, IScrollingPanel parent) {
        super.initialize(gui, parent);
        this.parent = parent;

    }

    @Nullable
    @Override
    public IScrollingPanel getParentComponent() {
        return parent;
    }
    
}
