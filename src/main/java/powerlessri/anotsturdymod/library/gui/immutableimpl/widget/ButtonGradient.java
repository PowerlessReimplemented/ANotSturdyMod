package powerlessri.anotsturdymod.library.gui.immutableimpl.widget;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL45;
import powerlessri.anotsturdymod.config.ClientConfig;
import powerlessri.anotsturdymod.library.gui.immutableimpl.AbstractButton;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.varia.general.GuiUtils;

public class ButtonGradient extends AbstractButton {
    
    public static final int ALPHA_SOLID = 255;
    
    private int redNormalS;
    private int greenNormalS;
    private int blueNormalS;
    private int redNormalE;
    private int greenNormalE;
    private int blueNormalE;
    
    private int width;
    private int height;

    public ButtonGradient(int relativeX, int relativeY, int width, int height) {
        super(relativeX, relativeY);
        
        this.width = width;
        this.height = height;
        
        this.redNormalS = ClientConfig.gradientBtnStart >> 16 & 255;
        this.greenNormalS = ClientConfig.gradientBtnStart >> 8 & 255;
        this.blueNormalS = ClientConfig.gradientBtnStart & 255;
        this.redNormalE = ClientConfig.gradientBtnEnd >> 16 & 255;
        this.greenNormalE = ClientConfig.gradientBtnEnd >> 8 & 255;
        this.blueNormalE = ClientConfig.gradientBtnEnd & 255;
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
        drawGradientRectangleBox(0, redNormalS, greenNormalS, blueNormalS, redNormalE, greenNormalE, blueNormalE, ALPHA_SOLID);
    }

    @Override
    public void drawHovering(GuiDrawBackgroundEvent event) {
        drawGradientRectangleBox(0, 178, 200, 255, 107, 149, 255, ALPHA_SOLID);
    }

    @Override
    public void drawPressed(GuiDrawBackgroundEvent event) {
        drawGradientRectangleBox(0, 109, 150, 255, 0, 72, 255, ALPHA_SOLID);
    }

    @Override
    public void drawDisabled(GuiDrawBackgroundEvent event) {
        drawNormal(event);
    }


    public void drawGradientRectangleBox(int shrink, int red1, int green1, int blue1, int red2, int green2, int blue2, int alpha) {
        GuiUtils.useGradientGLStates();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

        // Move the vertexes inwards
        // *----------------*
        // | *------------* |
        // | |            | |
        // | |            | |
        // | *------------* |
        // *----------------*
        // ^^^ shrink
        int x = getActualX() + shrink;
        int y = getActualY() + shrink;
        int xBR = getActualXBR() - shrink;
        int yBR = getActualYBR() - shrink;
        
        // Top Right -> Top Left -> Bottom Left -> Bottom Right
        buffer.pos(xBR, y, z).color(red1, green1, blue1, alpha).endVertex();
        buffer.pos(x, y, z).color(red1, green1, blue1, alpha).endVertex();
        buffer.pos(x, yBR, z).color(red2, green2, blue2, alpha).endVertex();
        buffer.pos(xBR, yBR, z).color(red2, green2, blue2, alpha).endVertex();

        tessellator.draw();
    }
    
}
