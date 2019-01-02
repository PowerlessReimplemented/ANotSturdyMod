package powerlessri.anotsturdymod.library.gui.simpleimpl.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import powerlessri.anotsturdymod.config.client.GuiStyleConfig;
import powerlessri.anotsturdymod.library.gui.Color;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractButton;
import powerlessri.anotsturdymod.varia.render.TESRStateManager;
import powerlessri.anotsturdymod.varia.render.VertexSequencer;
import powerlessri.anotsturdymod.varia.render.style.VanillaPresets;
import powerlessri.anotsturdymod.varia.render.utils.BoxUtils;

public class ButtonGradient extends AbstractButton {

    public static final int VANILLA_CHAR_HEIGHT = 8;


    public static Color colorSNormal;
    public static Color colorENormal;

    public static Color colorSHovering;
    public static Color colorEHovering;

    public static Color colorSPressed;
    public static Color colorEPressed;

    public static Color colorNormalText;
    public static Color colorDisabledText;

    private static void reloadColors() {
        colorSNormal = Color.hex(GuiStyleConfig.gradientBtnSNormal);
        colorENormal = Color.hex(GuiStyleConfig.gradientBtnENormal);

        colorSHovering = Color.hex(GuiStyleConfig.gradientBtnSHover);
        colorEHovering = Color.hex(GuiStyleConfig.gradientBtnEHover);

        colorSPressed = Color.hex(GuiStyleConfig.gradientBtnSPressed);
        colorEPressed = Color.hex(GuiStyleConfig.gradientBtnEPressed);

        colorNormalText = Color.hex(GuiStyleConfig.gradientBtnTextNormal);
        colorDisabledText = Color.hex(GuiStyleConfig.gradientBtnTextDisabled);
    }


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
        this.textXOffset = (getWidth() / 2) - (Minecraft.getMinecraft().fontRenderer.getStringWidth(text) / 2);
        this.textYOffset = (getHeight() / 2) - (VANILLA_CHAR_HEIGHT / 2);
    }


    @Override
    public void drawNormal(GuiDrawBackgroundEvent event) {
        this.drawGradientRectangleBox(1, colorSNormal, colorENormal, false);
        this.drawText(colorNormalText.getHex());
    }

    @Override
    public void drawHovering(GuiDrawBackgroundEvent event) {
        this.drawGradientRectangleBox(1, colorSHovering, colorEHovering, false);
        this.drawText(colorNormalText.getHex());
    }

    @Override
    public void drawPressed(GuiDrawBackgroundEvent event) {
        this.drawGradientRectangleBox(1, colorSPressed, colorEPressed, true);
        this.drawText(colorNormalText.getHex());
    }

    @Override
    public void drawDisabled(GuiDrawBackgroundEvent event) {
        this.drawNormal(event);
        this.drawText(colorDisabledText.getHex());
    }


    private void drawText(int color) {
        Minecraft.getMinecraft().fontRenderer.drawString(text, getTextX(), getTextY(), color);
    }

    private void drawGradientRectangleBox(int shrink, Color top, Color bottom, boolean invert) {
        BufferBuilder buffer = TESRStateManager.getGradientVBuffer();
        {
            VertexSequencer.verticalGradientBox(buffer, getActualX() + shrink, getActualY() + shrink, getActualXRight() - shrink, getActualYBottom() - shrink, 0, top, bottom);
            int x1 = getActualX();
            int y1 = getActualY();
            int x2 = getActualXRight();
            int y2 = getActualYBottom();
            if (invert) {
                BoxUtils.putBorderVertexes(buffer, x1, y1, x2, y2, 1, VanillaPresets.BORDER_COLOR_DARK, VanillaPresets.BORDER_COLOR_LIGHT);
            } else {
                BoxUtils.putBorderVertexes(buffer, x1, y1, x2, y2, 1, VanillaPresets.BORDER_COLOR_LIGHT, VanillaPresets.BORDER_COLOR_DARK);
            }
        }
        TESRStateManager.finish();
    }


    @Override
    public String toString() {
        return "ButtonGradient{" +
                "text='" + text + '\'' +
                '}';
    }

}
