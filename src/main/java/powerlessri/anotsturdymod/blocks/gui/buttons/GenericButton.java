package powerlessri.anotsturdymod.blocks.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GenericButton extends GuiButton {


    public GenericButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }


    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return super.mousePressed(mc, mouseX, mouseY);
    }

}
