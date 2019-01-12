package powerlessri.anotsturdymod.library.gui.simpleimpl.scrollable;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import powerlessri.anotsturdymod.library.gui.integration.ContextGuiDrawing;
import powerlessri.anotsturdymod.library.gui.simpleimpl.button.Button;
import powerlessri.anotsturdymod.library.gui.simpleimpl.button.ButtonGradient;
import powerlessri.anotsturdymod.varia.render.utils.BoxUtils;
import powerlessri.anotsturdymod.varia.render.utils.LineUtils;
import powerlessri.anotsturdymod.varia.render.TESRStateManager;
import powerlessri.anotsturdymod.varia.render.style.VanillaPresets;

import javax.annotation.Nonnull;

public abstract class ScrollbarThinStyled extends Button implements IScrollbar {
    
    public static final int WIDTH = 7;
    public static final int LINE_LABEL_OFFSET = 2;
    

    private int actualHeight;

    private IScrollingPanel parent;

    public ScrollbarThinStyled(int relativeX, int relativeY, int height) {
        super(relativeX, relativeY, WIDTH, height);
    }


    @Override
    public void drawNormal(ContextGuiDrawing event) {
        BufferBuilder buffer = TESRStateManager.getColorVBuffer();
        VanillaPresets.putConvexBox(buffer, getActualX(), getActualY(), getActualXRight(), getActualYBottom());
        drawLabel(buffer);
        TESRStateManager.draw();
    }

    @Override
    public void drawHovering(ContextGuiDrawing event) {
        // TODO add hovering blue-ish texture
        drawNormal(event);

        BufferBuilder buffer = TESRStateManager.getColorVBuffer();

        BoxUtils.putBorderedBox(buffer, getActualX(), getActualY(), getActualXRight(), getActualYBottom(), 1, ButtonGradient.colorEHovering, ButtonGradient.colorSHovering, ButtonGradient.colorEHovering);

        drawLabel(buffer);
        TESRStateManager.draw();
    }

    @Override
    public void drawPressed(ContextGuiDrawing event) {
        BufferBuilder buffer = TESRStateManager.getColorVBuffer();
        VanillaPresets.putConcaveBox(buffer, getActualX(), getActualY(), getActualXRight(), getActualYBottom());
        drawLabel(buffer);
        TESRStateManager.draw();
    }

    @Override
    public void drawDisabled(ContextGuiDrawing event) {
        drawNormal(event);
    }

    private void drawLabel(BufferBuilder buffer) {
        int centerY = getActualY() + (getHeight() / 2);

        int lineX = getActualX() + LINE_LABEL_OFFSET;
        int lineY = centerY - LINE_LABEL_OFFSET;
        for (int i = 0; i < 3; i++) {
            LineUtils.putHorizontalLine(buffer, lineX, 3, lineY, VanillaPresets.BORDER_COLOR_DARK);
            lineY += LINE_LABEL_OFFSET;
        }
    }


    public void initialize(GuiScreen gui, IScrollingPanel parent) {
        super.initialize(gui, parent);
        if (parent != null) {
            this.parent = parent;
        } else {
            throw new IllegalArgumentException("Scrollbar must have a parent!");
        }

        actualHeight = (int) (getMaximumHeight() * parent.getContentScaleFactor());
    }

    @Nonnull
    @Override
    public IScrollingPanel getParentComponent() {
        return parent;
    }

    @Override
    public int getHeight() {
        return actualHeight;
    }

    public float getStepHeight() {
        return (float) getEmptyLength() / parent.getTotalSteps();
    }

    @Override
    public int getMaximumHeight() {
        return super.getHeight();
    }
    
    @Override
    public abstract int getActualY();

    @Override
    public int getBaseY() {
        return super.getActualY();
    }

    public int getEmptyLength() {
        return getMaximumHeight() - getHeight();
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
