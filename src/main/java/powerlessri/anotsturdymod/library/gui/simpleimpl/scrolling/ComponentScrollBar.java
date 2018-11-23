package powerlessri.anotsturdymod.library.gui.simpleimpl.scrolling;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.Util;
import powerlessri.anotsturdymod.library.gui.api.EEventType;
import powerlessri.anotsturdymod.library.gui.api.EMouseButton;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractButton;
import powerlessri.anotsturdymod.varia.general.Utils;
import powerlessri.anotsturdymod.varia.render.TessellatorUtils;
import powerlessri.anotsturdymod.varia.render.style.GLGrayScale;

import javax.annotation.Nullable;

public class ComponentScrollBar extends AbstractButton implements IScrollBar {
    
    private int offset;
    private int actualHeight;
    
    private IScrollingPanel parent;
    
    public ComponentScrollBar(int relativeX, int relativeY, int height) {
        super(relativeX, relativeY, 7, height);
    }


    @Override
    public void onClickedDragging(int mouseX, int mouseY, EMouseButton button, long timePressed) {
        super.onClickedDragging(mouseX, mouseY, button, timePressed);
        offset = limitToRange(mouseY - getBaseY());
    }


    private int limitToRange(int n) {
        if (n < 0) {
            return 0;
        }
        int bottom = getMaximumHeight() - getHeight(); 
        if(n > bottom) {
            return bottom;
        }
        return n;
    }

    @Override
    public EnumActionResult onReleased(int mouseX, int mouseY, EMouseButton button, EEventType type) {
        super.onReleased(mouseX, mouseY, button, type);
        
        float stepHeight = (float) parent.getTotalSteps() / getMaximumHeight();
        Utils.getLogger().info("stepHeight: " + stepHeight);
        int step = (int) (offset / stepHeight);
        Utils.getLogger().info("step: " + step);
        parent.setContentStep(step);
        
        return EnumActionResult.FAIL;
    }
    

    @Override
    public void drawNormal(GuiDrawBackgroundEvent event) {
        BufferBuilder buffer = TessellatorUtils.getColorVBuffer();
        GLGrayScale.vanillaConvexBox(buffer, getActualX(), getActualY(), getActualXBR(), getActualYBR());
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
        GLGrayScale.vanillaConcaveBox(buffer, getActualX(), getActualY(), getActualXBR(), getActualYBR());
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
    

    @Override
    public void initialize(GuiScreen gui, IScrollingPanel parent) {
        super.initialize(gui, parent);
        this.parent = parent;

        int maxHeight = getMaximumHeight();
        float scaleFactor = (float) maxHeight / parent.getHeight();
        actualHeight = (int) (maxHeight * scaleFactor);
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
