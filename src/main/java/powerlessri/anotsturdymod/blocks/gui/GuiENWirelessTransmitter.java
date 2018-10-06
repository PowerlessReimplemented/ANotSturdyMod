package powerlessri.anotsturdymod.blocks.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.TextComponentString;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.blocks.container.ContainerEnergyIOAccess;
import powerlessri.anotsturdymod.blocks.tile.TileENWirelessTransmitter;
import powerlessri.anotsturdymod.library.utils.Utils;
import powerlessri.anotsturdymod.network.PacketServerCommand;
import powerlessri.anotsturdymod.network.utils.NetworkHelper;

import java.io.IOException;

public class GuiENWirelessTransmitter extends GuiEnergyIOAccess {
    
    protected static final int BUTTON_SCAN_TILES = buttonId++;
    
    protected static final int SCAN_TILES_BTN_WIDTH = 38;

    public GuiENWirelessTransmitter(ContainerEnergyIOAccess container) {
        super(container);
    }
    
    
    @Override
    public void initGui() {
        super.initGui();
        
        addButton(new GuiButton(BUTTON_SCAN_TILES,
                centerX + 29, channelButtonsY + CHANNEL_BTN_HEIGHT + 10,
                SCAN_TILES_BTN_WIDTH, CHANNEL_BTN_HEIGHT,
                Utils.readFromLang("gui.ansm:wireless_transmitter.button.scanNearbyTiles")));
    }
    
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        
        if(button.id == BUTTON_SCAN_TILES) {
            NetworkHelper.sendServerCommand(
                    ANotSturdyMod.genericChannel,
                    TileENWirelessTransmitter.SCAN_NEARBY_TILES,
                    PacketServerCommand.makeWorldPosArgs(Minecraft.getMinecraft().player.dimension, tilePos.getX(), tilePos.getY(), tilePos.getZ()));

            Minecraft.getMinecraft().player.sendMessage(new TextComponentString(Utils.readFromLang("gui.ansm:wireless_transmitter.text.startedScanning")));
        }
    }

}
