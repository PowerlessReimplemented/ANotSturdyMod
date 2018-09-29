package powerlessri.anotsturdymod.blocks.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import powerlessri.anotsturdymod.blocks.container.ContainerEnergyAccessPort;
import powerlessri.anotsturdymod.library.utils.Reference;
import powerlessri.anotsturdymod.library.utils.Utils;

import java.io.IOException;

public class GuiEnergyAccessPort extends GuiContainer {

    private static final ResourceLocation BACKGROUND_LOC = new ResourceLocation(Reference.MODID,"textures/gui/access_port.png");

    private final int guiWidth = 176;
    private final int guiHeight = 166;

    private int centerX;
    private int centerY;



    public GuiEnergyAccessPort(ContainerEnergyAccessPort inventory) {
        super(inventory);
    }


    @Override
    public void initGui() {
        super.initGui();

        centerX = (width / 2) - guiWidth / 2;
        centerY = (height / 2) - guiHeight / 2;

        {
            int buttonId = 0;
            int buttonX;
            int buttonY;
            int width;
            int height;

            buttonY = centerY + 37;
            width = 21;
            height = 14;
            addButton(new GuiButton(buttonId++, centerX + 29, buttonY, width, height, "-10"));
            addButton(new GuiButton(buttonId++, centerX + 53, buttonY, width, height, "-1"));
            addButton(new GuiButton(buttonId++, centerX + 104, buttonY, width, height, "+1"));
            addButton(new GuiButton(buttonId++, centerX + 128, buttonY, width, height, "+10"));
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();

        GlStateManager.color(1, 1, 1);
        Minecraft.getMinecraft().renderEngine.bindTexture(BACKGROUND_LOC);
        drawTexturedModalRect(centerX, centerY, 0, 0, guiWidth, guiHeight);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString("0", centerX, centerY, 0x000000ff);
    }



    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        Utils.getLogger().info("clicked " + button.id);
    }
}
