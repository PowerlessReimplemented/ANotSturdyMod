package powerlessri.anotsturdymod.library.gui.simpleimpl.label;

import net.minecraft.client.Minecraft;
import powerlessri.anotsturdymod.library.gui.Color;
import powerlessri.anotsturdymod.library.gui.integration.ContextGuiDrawing;
import powerlessri.anotsturdymod.library.gui.simpleimpl.button.ButtonGradient;

public class LabelText extends Label {

    private static int calculateWidth(int horizontalMargin, String text) {
        return horizontalMargin * 2 + Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
    }

    private static int calculateHeight(int verticalMargin) {
        return verticalMargin * 2 + Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
    }


    private String text;
    private int horizontalMargin;
    private int verticalMargin;
    
    private Color color;

    public LabelText(int relativeX, int relativeY, String text, int horizontalMargin, int verticalMargin) {
        super(relativeX, relativeY, calculateWidth(horizontalMargin, text), calculateHeight(verticalMargin));
        this.text = text;
        this.verticalMargin = verticalMargin;
        this.horizontalMargin = horizontalMargin;
    }
    

    @Override
    public void draw(ContextGuiDrawing event) {
        int textX = getActualX() + getHorizontalMargin();
        int textY = getActualY() + getVerticalMargin();
        Minecraft.getMinecraft().fontRenderer.drawString(text, textX, textY, color.getHex());
    }

    public LabelText withColor(Color color) {
        this.color = color;
        return this;
    }


    public int getHorizontalMargin() {
        return horizontalMargin;
    }

    public int getVerticalMargin() {
        return verticalMargin;
    }
    
}
