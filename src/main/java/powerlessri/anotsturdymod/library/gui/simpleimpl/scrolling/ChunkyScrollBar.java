package powerlessri.anotsturdymod.library.gui.simpleimpl.scrolling;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.EnumActionResult;
import powerlessri.anotsturdymod.library.gui.api.EEventType;
import powerlessri.anotsturdymod.library.gui.api.EMouseButton;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractButton;
import powerlessri.anotsturdymod.varia.general.Utils;
import powerlessri.anotsturdymod.varia.render.TessellatorUtils;
import powerlessri.anotsturdymod.varia.render.style.GLGrayScale;

import javax.annotation.Nullable;

/**
 * Scroll bar implementation that locks its position at every meaningful level for itself.
 * <p>This means the scroll bar will not provide a smooth feeling like when you operate the vanilla scroll bar.  </p>
 * <p>
 * TODO add another implementation that is not as chunky
 * See {@link } for implementation with a smoother user experience.
 * </p>
 */
public class ChunkyScrollBar extends AbstractButton implements IScrollBar {

    private int offset;
    private int actualHeight;

    private IScrollingPanel parent;

    public ChunkyScrollBar(int relativeX, int relativeY, int height) {
        super(relativeX, relativeY, 7, height);
    }


    @Override
    public void onClickedDragging(int mouseX, int mouseY, EMouseButton button, long timePressed) {
        super.onClickedDragging(mouseX, mouseY, button, timePressed);
        offset = limitToRange(mouseY - getBaseY());

        int step = (int) (offset / getStepHeight());
        if (step != parent.getCurrentStep()) {
            parent.setCurrentStep(step);
        }
    }
    
    private float getStepHeight() {
        return (float) getEmptyLength() / parent.getTotalSteps();
    }
    
    private int limitToRange(int n) {
        if (n < 0) {
            return 0;
        }
        int bottom = getMaximumHeight() - getHeight();
        if (n >= bottom) {
            return bottom;
        }
        return n;
    }


    @Override
    public void drawNormal(GuiDrawBackgroundEvent event) {
        // TODO draw lines in the center
        BufferBuilder buffer = TessellatorUtils.getColorVBuffer();
        GLGrayScale.addConvexBox(buffer, getActualX(), getActualY(), getActualXBR(), getActualYBR());
        TessellatorUtils.draw();
    }

    @Override
    public void drawHovering(GuiDrawBackgroundEvent event) {
        // TODO add hovering blue-ish texture
        drawNormal(event);
    }

    @Override
    public void drawPressed(GuiDrawBackgroundEvent event) {
        BufferBuilder buffer = TessellatorUtils.getColorVBuffer();
        GLGrayScale.addConcaveBox(buffer, getActualX(), getActualY(), getActualXBR(), getActualYBR());
        TessellatorUtils.draw();
    }

    @Override
    public void drawDisabled(GuiDrawBackgroundEvent event) {
        drawNormal(event);
    }


    @Override
    public int getWidth() {
        return 7;
    }

    @Override
    public int getHeight() {
        return actualHeight;
    }

    @Override
    public int getMaximumHeight() {
        return super.getHeight();
    }

    @Override
    public int getActualY() {
        return super.getActualY() + offset;
    }

    @Override
    public int getBaseY() {
        return super.getActualY();
    }
    
    public int getEmptyLength() {
        return getMaximumHeight() - getHeight();
    }


    @Override
    public void initialize(GuiScreen gui, IScrollingPanel parent) {
        super.initialize(gui, parent);
        this.parent = parent;

        actualHeight = (int) (getMaximumHeight() * parent.getContentKFactor());
    }

    @Nullable
    @Override
    public IScrollingPanel getParentComponent() {
        return parent;
    }

    @Override
    public boolean isLeafComponent() {
        return true;
    }

    @Override
    public void disable() {
    }

    @Override
    public void enable() {
    }

}
