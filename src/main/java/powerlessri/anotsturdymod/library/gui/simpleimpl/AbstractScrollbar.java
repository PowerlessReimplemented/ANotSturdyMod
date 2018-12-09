package powerlessri.anotsturdymod.library.gui.simpleimpl;

import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.library.gui.api.IScrollbar;
import powerlessri.anotsturdymod.library.gui.api.IScrollingPanel;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractButton;

import javax.annotation.Nullable;

public abstract class AbstractScrollbar extends AbstractButton implements IScrollbar {
    
    private IScrollingPanel parent;

    public AbstractScrollbar(int relativeX, int relativeY, int width, int height) {
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
