package powerlessri.anotsturdymod.library.gui.simpleimpl.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import powerlessri.anotsturdymod.config.ClientConfig;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractButton;
import powerlessri.anotsturdymod.library.gui.ColorApplier;
import powerlessri.anotsturdymod.library.gui.simpleimpl.scrolling.IScrollableComponent;
import powerlessri.anotsturdymod.varia.render.BoxUtils;
import powerlessri.anotsturdymod.varia.render.TessellatorUtils;
import powerlessri.anotsturdymod.varia.render.VertexSequencer;
import powerlessri.anotsturdymod.varia.render.style.GLGrayScale;

public class ButtonGradient extends AbstractButton implements IScrollableComponent {
    
    public static final int VANILLA_CHAR_HEIGHT = 8;


    private ColorApplier colorSNormal = ColorApplier.hexSolid(ClientConfig.gradientBtnSNormal);
    private ColorApplier colorENormal = ColorApplier.hexSolid(ClientConfig.gradientBtnENormal);

    private ColorApplier colorSHovering = ColorApplier.hexSolid(ClientConfig.gradientBtnSHover);
    private ColorApplier colorEHovering = ColorApplier.hexSolid(ClientConfig.gradientBtnEHover);
    
    private ColorApplier colorSPressed = ColorApplier.hexSolid(ClientConfig.gradientBtnSPressed);
    private ColorApplier colorEPressed = ColorApplier.hexSolid(ClientConfig.gradientBtnEPressed);



    private String text;
    private int textXOffset;
    private int textYOffset;

    public ButtonGradient(int relativeX, int relativeY, int width, int height, String text) {
        super(relativeX, relativeY, width, height);
        this.setText(text);
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
        drawText(ClientConfig.gradientBtnTextNormal);
    }

    @Override
    public void drawHovering(GuiDrawBackgroundEvent event) {
        drawGradientRectangleBox(1, colorSHovering, colorEHovering, false);
        drawText(ClientConfig.gradientBtnTextNormal);
    }

    @Override
    public void drawPressed(GuiDrawBackgroundEvent event) {
        drawGradientRectangleBox(1, colorSPressed, colorEPressed, true);
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


    public void drawGradientRectangleBox(int shrink, ColorApplier top, ColorApplier bottom, boolean invert) {
        BufferBuilder buffer = TessellatorUtils.getGradientVBuffer();
        VertexSequencer.verticalGradientBox(buffer, getActualX() + shrink, getActualY() + shrink, getActualXBR() - shrink, getActualYBR() - shrink, 0, top, bottom);
        GLGrayScale.vanillaBorder(buffer, getActualX(), getActualY(), getActualXBR(), getActualYBR(), invert);
        TessellatorUtils.finish();
    }

    @Override
    public void draw(GuiDrawBackgroundEvent event, int y) {
        draw(event);
    }
}
