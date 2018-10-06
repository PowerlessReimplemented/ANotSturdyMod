package powerlessri.anotsturdymod.blocks.gui;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.blocks.container.ContainerEnergyIOAccess;
import powerlessri.anotsturdymod.blocks.tile.TileENAccessPort;
import powerlessri.anotsturdymod.blocks.tile.TileENComponentBase;
import powerlessri.anotsturdymod.library.utils.Reference;
import powerlessri.anotsturdymod.network.NetworkHelper;

public class GuiEnergyIOAccess extends GuiContainer {

    private static final ResourceLocation BACKGROUND_LOC = new ResourceLocation(Reference.MODID,"textures/gui/access_port.png");

    private static final int guiWidth = 176;
    private static final int guiHeight = 166;

    protected static int buttonId = 0;
    protected static final int BUTTON_MINUS_10 = buttonId++;
    protected static final int BUTTON_MINUS_1 = buttonId++;
    protected static final int BUTTON_ADD_1 = buttonId++;
    protected static final int BUTTON_ADD_10 = buttonId++;

    private static final int[] BUTTON_OPERATION_VALUES = new int[] {-10, -1, 1, 10};
    
    
    
    protected int centerX;
    protected int centerY;
    
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

        // Sync channel between sides
        NetworkHelper.sendServerCommand(
                ANotSturdyMod.genericChannel,
                TileENAccessPort.SET_CHANNEL,
                TileENAccessPort.makeSetChannelArgs(Minecraft.getMinecraft().player.dimension, tilePos.getX(), tilePos.getY(), tilePos.getZ(), container.channel));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if(button.id >= BUTTON_MINUS_10 && button.id <= BUTTON_ADD_10) {
            ContainerEnergyIOAccess container = getContainer();

            int oldChannel = container.channel;
            container.channel += BUTTON_OPERATION_VALUES[button.id];

            if(container.channel < 0) {
                container.channel = oldChannel;
            }
        }
    }


    protected ContainerEnergyIOAccess getContainer() {
        return (ContainerEnergyIOAccess) inventorySlots;
    }

}
