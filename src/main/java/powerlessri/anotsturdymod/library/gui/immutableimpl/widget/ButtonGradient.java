package powerlessri.anotsturdymod.library.gui.immutableimpl.widget;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import powerlessri.anotsturdymod.library.gui.immutableimpl.AbstractButton;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.varia.general.GuiUtils;

public class ButtonGradient extends AbstractButton {
    
    private int width;
    private int height;

    public ButtonGradient(int relativeX, int relativeY, int width, int height) {
        super(relativeX, relativeY);
        
        this.width = width;
        this.height = height;
    }
    

    @Override
    public boolean isLeafComponent() {
        return true;
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
    public void drawNormal(GuiDrawBackgroundEvent event) {
        GuiUtils.useGradientGLStates();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

        // Bottom Left -> Top Left -> Top Right -> Bottom Right
        buffer.pos(getActualXBR(), getActualY(), z).color(255, 255, 255, 255).endVertex();
        buffer.pos(getActualX(), getActualY(), z).color(255, 255, 255, 255).endVertex();
        buffer.pos(getActualX(), getActualYBR(), z).color(37, 37, 37, 255).endVertex();
        buffer.pos(getActualXBR(), getActualYBR(), z).color(37, 37, 37, 255).endVertex();

        tessellator.draw();
    }

    @Override
    public void drawHovering(GuiDrawBackgroundEvent event) {
    }

    @Override
    public void drawClicked(GuiDrawBackgroundEvent event) {

    }

    @Override
    public void drawDisabled(GuiDrawBackgroundEvent event) {
    }
    
}
