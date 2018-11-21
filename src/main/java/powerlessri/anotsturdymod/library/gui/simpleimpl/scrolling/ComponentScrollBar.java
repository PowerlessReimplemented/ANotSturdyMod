package powerlessri.anotsturdymod.library.gui.simpleimpl.scrolling;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumActionResult;
import powerlessri.anotsturdymod.library.gui.api.EEventType;
import powerlessri.anotsturdymod.library.gui.api.EMouseButton;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractComponent;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;

import javax.annotation.Nullable;

public class ComponentScrollBar extends AbstractComponent implements IScrollBar {
    
    private ScrollingPanel parent;
    
    public ComponentScrollBar(int relativeX, int relativeY) {
        super(relativeX, relativeY);
    }

    
    @Override
    public boolean isLeafComponent() {
        return true;
    }

    @Override
    public int getWidth() {
        return 7;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void draw(GuiDrawBackgroundEvent event) {

    }


    @Override
    public void initialize(GuiScreen gui, IComponent parent) {
        super.initialize(gui, parent);
        this.parent = (ScrollingPanel) parent;
    }

    @Override
    public void initalize(GuiScreen gui, IScrollingPanel parent) {
        
    }

    @Nullable
    @Override
    public IComponent getParentComponent() {
        return parent;
    }

    @Override
    public EnumActionResult onClicked(int mouseX, int mouseY, EMouseButton button, EEventType type) {
        return null;
    }

    @Override
    public void onClickedDragging(int mouseX, int mouseY, EMouseButton button, long timePressed) {

    }

    @Override
    public void onHoveredDragging(int mouseX, int mouseY, EMouseButton button) {

    }

    @Override
    public EnumActionResult onReleased(int mouseX, int mouseY, EMouseButton button, EEventType type) {
        return null;
    }
    
}
