package powerlessri.anotsturdymod.blocks.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.library.utils.Reference;

public class GuiEnergyAccessPort extends GuiContainer {

    private static final ResourceLocation BACKGROUND_LOC = new ResourceLocation(Reference.MODID,"textures/gui/access_port.png");
    private int guiWidth = 176;
    private int guiHeight = 166;

    public GuiEnergyAccessPort(Container inventory) {
        super(inventory);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();

        int centerX = (width / 2) - guiWidth / 2;
        int centerY = (height / 2) - guiHeight / 2;

        GlStateManager.color(1, 1, 1);
        Minecraft.getMinecraft().renderEngine.bindTexture(BACKGROUND_LOC);
        drawTexturedModalRect(centerX, centerY, 0, 0, guiWidth, guiHeight);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float particalTicks) {
        super.drawScreen(mouseX, mouseY, particalTicks);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

    }

}
