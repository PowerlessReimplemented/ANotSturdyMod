package powerlessri.anotsturdymod.library.gui.immutableimpl.widget;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import powerlessri.anotsturdymod.config.ClientConfig;
import powerlessri.anotsturdymod.library.gui.immutableimpl.AbstractButton;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.varia.general.GuiUtils;
import powerlessri.anotsturdymod.varia.general.Utils;

public class ButtonGradient extends AbstractButton {
    
    private float redStart;
    private float greenStart;
    private float blueStart;
    private float redEnd;
    private float greenEnd;
    private float blueEnd;
    
    private int width;
    private int height;

    public ButtonGradient(int relativeX, int relativeY, int width, int height) {
        super(relativeX, relativeY);
        
        this.width = width;
        this.height = height;
        
        this.redStart = (ClientConfig.gradientBtnStart >> 16 & 255) / 255.0f;
        this.greenStart = (ClientConfig.gradientBtnStart >> 8 & 255) / 255.0f;
        this.blueStart = (ClientConfig.gradientBtnStart & 255) / 255.0f;
        this.redEnd = (ClientConfig.gradientBtnEnd >> 16 & 255) / 255.0f;
        this.greenEnd = (ClientConfig.gradientBtnEnd >> 8 & 255) / 255.0f;
        this.blueEnd = (ClientConfig.gradientBtnEnd & 255) / 255.0f;
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

    // TODO add states
    @Override
    public void drawNormal(GuiDrawBackgroundEvent event) {
        GuiUtils.useGradientGLStates();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

        // Bottom Left -> Top Left -> Top Right -> Bottom Right
        buffer.pos(getActualXBR(), getActualY(), z).color(redStart, greenStart, blueStart, 1.0f).endVertex();
        buffer.pos(getActualX(), getActualY(), z).color(redStart, greenStart, blueStart, 1.0f).endVertex();
        buffer.pos(getActualX(), getActualYBR(), z).color(redEnd, greenEnd, blueEnd, 1.0f).endVertex();
        buffer.pos(getActualXBR(), getActualYBR(), z).color(redEnd, greenEnd, blueEnd, 1.0f).endVertex();

        tessellator.draw();
    }

    @Override
    public void drawHovering(GuiDrawBackgroundEvent event) {
    }

    @Override
    public void drawPressed(GuiDrawBackgroundEvent event) {
    }

    @Override
    public void drawDisabled(GuiDrawBackgroundEvent event) {
    }
    
}
