package powerlessri.anotsturdymod.blocks.remoteenetwork.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.TextComponentString;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.blocks.remoteenetwork.tile.TileENWirelessTransmitter;
import powerlessri.anotsturdymod.library.gui.api.ITemplate;
import powerlessri.anotsturdymod.library.gui.api.TemplateProvider;
import powerlessri.anotsturdymod.library.gui.integration.ComponentizedGui;
import powerlessri.anotsturdymod.network.PacketServerCommand;
import powerlessri.anotsturdymod.varia.general.Utils;

import java.io.IOException;

public class GuiENWirelessTransmitter extends GuiEnergyIOAccess {

    @TemplateProvider(id = "energy_wireless_transmitter")
    public static ITemplate getGuiTemplate() {
        return new Template() {
            @Override
            public ComponentizedGui getGui() {
                return new GuiENWirelessTransmitter(getContainer());
            }
        };
    }


    protected static final int BUTTON_SCAN_TILES = buttonId++;

    protected static final int SCAN_TILES_BTN_WIDTH = 38;


    private int buttonScanTilesX;
    private int buttonScanTilesY;

    private int textScanResultX;
    private int textScanResultY;

    public GuiENWirelessTransmitter(ContainerEnergyIOAccess container) {
        super(container);
    }


    @Override
    public void initGui() {
        super.initGui();

        buttonScanTilesX = centerX + 29;
        buttonScanTilesY = channelButtonsY + CHANNEL_BTN_HEIGHT + 10;

        textScanResultX = buttonScanTilesX;
        textScanResultY = buttonScanTilesY + CHANNEL_BTN_HEIGHT + 8;

        addButton(new GuiButton(BUTTON_SCAN_TILES,
                buttonScanTilesX, buttonScanTilesY,
                SCAN_TILES_BTN_WIDTH, CHANNEL_BTN_HEIGHT,
                Utils.readFromLang("gui.ansm:wireless_transmitter.button.scanNearbyTiles")));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        String msg = Utils.readFromLang("gui.ansm:wireless_transmitter.text.amountPowerReceivers") +
                " " +
                ((TileENWirelessTransmitter) container.tile).amountPowerReceivers;

        fontRenderer.drawString(msg, textScanResultX, textScanResultY, 0x000000);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if (button.id == BUTTON_SCAN_TILES) {
            ANotSturdyMod.network.sendToServer(
                    new PacketServerCommand(TileENWirelessTransmitter.SCAN_NEARBY_TILES, PacketServerCommand.makeWorldPosArgs(
                            Minecraft.getMinecraft().player.dimension,
                            tilePos.getX(),
                            tilePos.getY(),
                            tilePos.getZ())));

            Minecraft.getMinecraft().player.sendMessage(new TextComponentString(Utils.readFromLang("gui.ansm:wireless_transmitter.text.startedScanning")));
        }
    }

}
