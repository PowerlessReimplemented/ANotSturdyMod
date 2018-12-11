package powerlessri.anotsturdymod.library.gui.simpleimpl.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import powerlessri.anotsturdymod.config.client.GuiStyleConfig;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractButton;
import powerlessri.anotsturdymod.library.gui.Color;
import powerlessri.anotsturdymod.varia.render.TessellatorUtils;
import powerlessri.anotsturdymod.varia.render.VertexSequencer;
import powerlessri.anotsturdymod.varia.render.style.GLGrayScale;

public class ButtonGradient extends AbstractButton {
    
    public static final int VANILLA_CHAR_HEIGHT = 8;


    public static Color colorSNormal;
    public static Color colorENormal;

    public static Color colorSHovering;
    public static Color colorEHovering;
    
    public static Color colorSPressed;
    public static Color colorEPressed;

    private static void reloadColors() {
        colorSNormal = Color.hex(GuiStyleConfig.gradientBtnSNormal);
        colorENormal = Color.hex(GuiStyleConfig.gradientBtnENormal);

        colorSHovering = Color.hex(GuiStyleConfig.gradientBtnSHover);
        colorEHovering = Color.hex(GuiStyleConfig.gradientBtnEHover);

        colorSPressed = Color.hex(GuiStyleConfig.gradientBtnSPressed);
        colorEPressed = Color.hex(GuiStyleConfig.gradientBtnEPressed);
    }
    

    private String text;
    private int textXOffset;
    private int textYOffset;

    public ButtonGradient(int relativeX, int relativeY, int width, int height, String text) {
        super(relativeX, relativeY, width, height);
        
        this.setText(text);
        reloadColors();
    }
    
    
    private int getTextX() {
        return getActualX() + textXOffset;
    }
    
    private int getTextY() {
        return getActualY() + textYOffset;
    }


    public void setText(String text) {
        this.text = text;
        
        int textWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
        textXOffset = (getWidth() / 2) - (textWidth / 2);
        textYOffset = (getHeight() / 2) - (VANILLA_CHAR_HEIGHT / 2); 
    }
    

    @Override
    public void drawNormal(GuiDrawBackgroundEvent event) {
        drawGradientRectangleBox(1, colorSNormal, colorENormal, false);
        drawText(GuiStyleConfig.gradientBtnTextNormal);
    }

    @Override
    public void drawHovering(GuiDrawBackgroundEvent event) {
        drawGradientRectangleBox(1, colorSHovering, colorEHovering, false);
        drawText(GuiStyleConfig.gradientBtnTextNormal);
    }

    @Override
    public void drawPressed(GuiDrawBackgroundEvent event) {
        drawGradientRectangleBox(1, colorSPressed, colorEPressed, true);
        drawText(GuiStyleConfig.gradientBtnTextNormal);
    }

    @Override
    public void drawDisabled(GuiDrawBackgroundEvent event) {
        drawNormal(event);
        drawText(GuiStyleConfig.gradientBtnTextDisabled);
    }

    public void drawText(int color) {
        Minecraft.getMinecraft().fontRenderer.drawString(text, getTextX(), getTextY(), color);
    }


    public void drawGradientRectangleBox(int shrink, Color top, Color bottom, boolean invert) {
        BufferBuilder buffer = TessellatorUtils.getGradientVBuffer();
        VertexSequencer.verticalGradientBox(buffer, getActualX() + shrink, getActualY() + shrink, getActualXBR() - shrink, getActualYBR() - shrink, 0, top, bottom);
        GLGrayScale.vanillaBorder(buffer, getActualX(), getActualY(), getActualXBR(), getActualYBR(), invert);
        TessellatorUtils.finish();
    }

}
