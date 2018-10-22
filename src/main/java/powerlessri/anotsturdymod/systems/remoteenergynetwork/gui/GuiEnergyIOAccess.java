package powerlessri.anotsturdymod.systems.remoteenergynetwork.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import powerlessri.anotsturdymod.systems.remoteenergynetwork.gui.container.ContainerEnergyIOAccess;
import powerlessri.anotsturdymod.systems.remoteenergynetwork.tile.TileENAccessPort;
import powerlessri.anotsturdymod.systems.remoteenergynetwork.tile.TileENComponentBase;
import powerlessri.anotsturdymod.varia.Reference;
import powerlessri.anotsturdymod.network.utils.NetworkHelper;

import java.io.IOException;

public class GuiEnergyIOAccess extends GuiContainer {

    private static final ResourceLocation BACKGROUND_LOC = new ResourceLocation(Reference.MODID, "textures/gui/access_port.png");

    private static final int guiWidth = 176;
    private static final int guiHeight = 166;

    protected static int buttonId = 0;
    protected static final int BUTTON_MINUS_10 = buttonId++;
    protected static final int BUTTON_MINUS_1 = buttonId++;
    protected static final int BUTTON_ADD_1 = buttonId++;
    protected static final int BUTTON_ADD_10 = buttonId++;

    protected static final int CHANNEL_BTN_WIDTH = 21;
    protected static final int CHANNEL_BTN_HEIGHT = 14;
    private static final int[] BUTTON_OPERATION_VALUES = new int[] {-10, -1, 1, 10};


    protected int centerX;
    protected int centerY;

    protected int channelButtonsY;

    protected final ContainerEnergyIOAccess container;
    protected final TileENComponentBase tile; // TileEntity at client side
    protected final BlockPos tilePos;

    public GuiEnergyIOAccess(ContainerEnergyIOAccess container) {
        super(container);

        this.container = container;
        this.tile = container.tile;
        this.tilePos = tile.getPos();
    }


    @Override
    public void initGui() {
        super.initGui();

        centerX = (width / 2) - guiWidth / 2;
        centerY = (height / 2) - guiHeight / 2;

        channelButtonsY = centerY + 10;

        addButton(new GuiButton(BUTTON_MINUS_10, centerX + 29, channelButtonsY, CHANNEL_BTN_WIDTH, CHANNEL_BTN_HEIGHT, "-10"));
        addButton(new GuiButton(BUTTON_MINUS_1, centerX + 53, channelButtonsY, CHANNEL_BTN_WIDTH, CHANNEL_BTN_HEIGHT, "-1"));
        addButton(new GuiButton(BUTTON_ADD_1, centerX + 104, channelButtonsY, CHANNEL_BTN_WIDTH, CHANNEL_BTN_HEIGHT, "+1"));
        addButton(new GuiButton(BUTTON_ADD_10, centerX + 128, channelButtonsY, CHANNEL_BTN_WIDTH, CHANNEL_BTN_HEIGHT, "+10"));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();

        GlStateManager.color(1, 1, 1);
        Minecraft.getMinecraft().renderEngine.bindTexture(BACKGROUND_LOC);
        drawTexturedModalRect(centerX, centerY, 0, 0, guiWidth, guiHeight);

        fontRenderer.drawString(String.valueOf(getContainer().channel), centerX + (guiWidth / 2) - 7, channelButtonsY + 3, 0x000000);
    }


    @Override
    public void onGuiClosed() {
        super.onGuiClosed();

        // Sync channel to server
        NetworkHelper.sendServerCommand(
                TileENComponentBase.SET_CHANNEL,
                TileENComponentBase.makeSetChannelArgs(Minecraft.getMinecraft().player.dimension, tilePos.getX(), tilePos.getY(), tilePos.getZ(), container.channel));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if (button.id >= BUTTON_MINUS_10 && button.id <= BUTTON_ADD_10) {
            ContainerEnergyIOAccess container = getContainer();
            container.channel = Math.max(container.channel + BUTTON_OPERATION_VALUES[button.id], 0);
        }
    }


    protected ContainerEnergyIOAccess getContainer() {
        return (ContainerEnergyIOAccess) inventorySlots;
    }

}
