package powerlessri.anotsturdymod.library.gui.simpleimpl.scrollable;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import powerlessri.anotsturdymod.library.gui.api.EMouseButton;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractButton;
import powerlessri.anotsturdymod.varia.general.Utils;
import powerlessri.anotsturdymod.varia.render.LineUtils;
import powerlessri.anotsturdymod.varia.render.TessellatorUtils;
import powerlessri.anotsturdymod.varia.render.style.GLGrayScale;

import javax.annotation.Nullable;

public class ComponentScrollBar extends AbstractButton implements IScrollBar {
    
    public static final int WIDTH = 7;
    public static final int LINE_LABEL_OFFSET = 2;
    

    private int offset;
    private int actualHeight;

    private IScrollingPanel parent;

    public ComponentScrollBar(int relativeX, int relativeY, int height) {
        super(relativeX, relativeY, WIDTH, height);
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
        BufferBuilder buffer = TessellatorUtils.getColorVBuffer();
        GLGrayScale.addConvexBox(buffer, getActualX(), getActualY(), getActualXBR(), getActualYBR());
        drawLabel(buffer);
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
        drawLabel(buffer);
        TessellatorUtils.draw();
    }

    @Override
    public void drawDisabled(GuiDrawBackgroundEvent event) {
        drawNormal(event);
    }

    private void drawLabel(BufferBuilder buffer) {
        int centerY = getActualY() + (getHeight() / 2);
        
        int lineX = getActualX() + LINE_LABEL_OFFSET;
        int lineY = centerY - LINE_LABEL_OFFSET;
        for (int i = 0; i < 3; i++) {
            LineUtils.horizontalLine(buffer, lineX, 3, lineY, GLGrayScale.BORDER_COLOR_DARK);
            lineY += LINE_LABEL_OFFSET;
        }
    } 


    @Override
    public int getWidth() {
        return WIDTH;
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
        this.actualHeight = (int) (getMaximumHeight() * parent.getContentScaleFactor());
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
    public int getVerticalMargin() {
        return 0;
    }

    
    @Override
    public void disable() {
    }
    
    @Override
    public void enable() {
    }
    
}
