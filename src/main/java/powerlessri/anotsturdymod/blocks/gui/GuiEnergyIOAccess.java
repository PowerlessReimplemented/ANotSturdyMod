package powerlessri.anotsturdymod.blocks.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.blocks.container.ContainerEnergyAccessPort;
import powerlessri.anotsturdymod.blocks.tile.TileEnergyNetworkAccessPort;
import powerlessri.anotsturdymod.library.utils.Reference;
import powerlessri.anotsturdymod.network.utils.NetworkHelper;

import java.io.IOException;

public class GuiEnergyIOAccess extends GuiContainer {

    private static final ResourceLocation BACKGROUND_LOC = new ResourceLocation(Reference.MODID,"textures/gui/access_port.png");

    private final int guiWidth = 176;
    private final int guiHeight = 166;

    private int centerX;
    private int centerY;

    private int buttonId = 0;
    private final int BUTTON_MINUS_10 = buttonId++;
    private final int BUTTON_MINUS_1 = buttonId++;
    private final int BUTTON_ADD_1 = buttonId++;
    private final int BUTTON_ADD_10 = buttonId++;

    private final int[] BUTTON_OPERATION_VALUES = new int[] {-10, -1, 1, 10};



    public GuiEnergyIOAccess(ContainerEnergyAccessPort inventory) {
        super(inventory);
    }


    @Override
    public void initGui() {
        super.initGui();

        centerX = (width / 2) - guiWidth / 2;
        centerY = (height / 2) - guiHeight / 2;

        {
            int buttonX;
            int buttonY;
            int width;
            int height;

            buttonY = centerY + 37;
            width = 21;
            height = 14;
            addButton(new GuiButton(BUTTON_MINUS_10, centerX + 29, buttonY, width, height, "-10"));
            addButton(new GuiButton(BUTTON_MINUS_1, centerX + 53, buttonY, width, height, "-1"));
            addButton(new GuiButton(BUTTON_ADD_1, centerX + 104, buttonY, width, height, "+1"));
            addButton(new GuiButton(BUTTON_ADD_10, centerX + 128, buttonY, width, height, "+10"));
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();

        GlStateManager.color(1, 1, 1);
        Minecraft.getMinecraft().renderEngine.bindTexture(BACKGROUND_LOC);
        drawTexturedModalRect(centerX, centerY, 0, 0, guiWidth, guiHeight);

        fontRenderer.drawString(String.valueOf(getContainer().channel), centerX + (guiWidth / 2) - 7, centerY + 40, 0x000000);
    }


    @Override
    public void onGuiClosed() {
        super.onGuiClosed();

        ContainerEnergyAccessPort container = getContainer();
        TileEnergyNetworkAccessPort tile = container.tile; // TileEntity at client side
        BlockPos tilePos = tile.getPos();

        // Sync channel between sides
        NetworkHelper.sendServerCommand(
                ANotSturdyMod.genericChannel,
                TileEnergyNetworkAccessPort.SET_CHANNEL,
                TileEnergyNetworkAccessPort.makeSetChannelArgs(Minecraft.getMinecraft().player.dimension, tilePos.getX(), tilePos.getY(), tilePos.getZ(), container.channel));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if(button.id >= BUTTON_MINUS_10 && button.id <= BUTTON_ADD_10) {
            ContainerEnergyAccessPort container = getContainer();

            int oldChannel = container.channel;
            container.channel += BUTTON_OPERATION_VALUES[button.id];

            if(container.channel < 0) {
                container.channel = oldChannel;
            }
        }
    }


    private ContainerEnergyAccessPort getContainer() {
        return (ContainerEnergyAccessPort) inventorySlots;
    }

}
