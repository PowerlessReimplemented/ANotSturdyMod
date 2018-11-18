package powerlessri.anotsturdymod.library.gui.immutableimpl.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import powerlessri.anotsturdymod.config.ClientConfig;
import powerlessri.anotsturdymod.library.gui.immutableimpl.AbstractButton;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.varia.general.GuiUtils;

public class ButtonGradient extends AbstractButton {
    
    public static final int ALPHA_SOLID = 255;
    public static final int VANILLA_CHAR_HEIGHT = 8;
    
    
    private int redNormalS;
    private int greenNormalS;
    private int blueNormalS;
    private int redNormalE;
    private int greenNormalE;
    private int blueNormalE;

    private int redHoverS;
    private int greenHoverS;
    private int blueHoverS;
    private int redHoverE;
    private int greenHoverE;
    private int blueHoverE;

    private int redPressedS;
    private int greenPressedS;
    private int bluePressedS;
    private int redPressedE;
    private int greenPressedE;
    private int bluePressedE;
    
    
    private String text;
    private int textXOffset;
    private int textYOffset;

    public ButtonGradient(int relativeX, int relativeY, int width, int height, String text) {
        super(relativeX, relativeY, width, height);
        
        this.width = width;
        this.height = height;
        this.initializeColors();
        this.setText(text);
    }
    
    private void initializeColors() {
        redNormalS = ClientConfig.gradientBtnSNormal >> 16 & 255;
        greenNormalS = ClientConfig.gradientBtnSNormal >> 8 & 255;
        blueNormalS = ClientConfig.gradientBtnSNormal & 255;
        redNormalE = ClientConfig.gradientBtnENormal >> 16 & 255;
        greenNormalE = ClientConfig.gradientBtnENormal >> 8 & 255;
        blueNormalE = ClientConfig.gradientBtnENormal & 255;

        redHoverS = ClientConfig.gradientBtnSHover >> 16 & 255;
        greenHoverS = ClientConfig.gradientBtnSHover >> 8 & 255;
        blueHoverS = ClientConfig.gradientBtnSHover & 255;
        redHoverE = ClientConfig.gradientBtnEHover >> 16 & 255;
        greenHoverE = ClientConfig.gradientBtnEHover >> 8 & 255;
        blueHoverE = ClientConfig.gradientBtnEHover & 255;

        redPressedS = ClientConfig.gradientBtnSPressed >> 16 & 255;
        greenPressedS = ClientConfig.gradientBtnSPressed >> 8 & 255;
        bluePressedS = ClientConfig.gradientBtnSPressed & 255;
        redPressedE = ClientConfig.gradientBtnEPressed >> 16 & 255;
        greenPressedE = ClientConfig.gradientBtnEPressed >> 8 & 255;
        bluePressedE = ClientConfig.gradientBtnEPressed & 255;
    }
    
    
    public int getTextX() {
        return getActualX() + textXOffset;
    }
    
    public int getTextY() {
        return getActualY() + textYOffset;
    }


    public void setText(String text) {
        this.text = text;
        
        int textWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
        textXOffset = (width / 2) - (textWidth / 2);
        textYOffset = (height / 2) - (VANILLA_CHAR_HEIGHT / 2); 
    }
    

    @Override
    public void drawNormal(GuiDrawBackgroundEvent event) {
        drawGradientRectangleBox(1, redNormalS, greenNormalS, blueNormalS, redNormalE, greenNormalE, blueNormalE, ALPHA_SOLID);
        drawText(ClientConfig.gradientBtnTextNormal);
    }

    @Override
    public void drawHovering(GuiDrawBackgroundEvent event) {
        drawGradientRectangleBox(1, redHoverS, greenHoverS, blueHoverS, redHoverE, greenHoverE, blueHoverE, ALPHA_SOLID);
        drawText(ClientConfig.gradientBtnTextNormal);
    }

    @Override
    public void drawPressed(GuiDrawBackgroundEvent event) {
        drawGradientRectangleBox(1, redPressedS, greenPressedS, bluePressedS, redPressedE, greenPressedE, bluePressedE, ALPHA_SOLID);
        drawText(ClientConfig.gradientBtnTextNormal);
    }

    @Override
    public void drawDisabled(GuiDrawBackgroundEvent event) {
        drawNormal(event);
        drawText(ClientConfig.gradientBtnTextDisabled);
    }

    public void drawText(int color) {
        Minecraft.getMinecraft().fontRenderer.drawString(text, getTextX(), getTextY(), color);
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
        GlStateManager.enableTexture2D();
    }
    
}
