package powerlessri.anotsturdymod.blocks.gui;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.blocks.container.ContainerEnergyIOAccess;
import powerlessri.anotsturdymod.blocks.tile.TileENWirelessTransmitter;
import powerlessri.anotsturdymod.library.utils.Utils;
import powerlessri.anotsturdymod.network.NetworkHelper;
import powerlessri.anotsturdymod.network.PacketServerCommand;

public class GuiENWirelessTransmitter extends GuiEnergyIOAccess {
    
    protected static final int BUTTON_SCAN_TILES = buttonId++;

    public GuiENWirelessTransmitter(ContainerEnergyIOAccess container) {
        super(container);
    }
    
    
    @Override
    public void initGui() {
        super.initGui();
        
        addButton(new GuiButton(BUTTON_SCAN_TILES,
                // 14: channel editor button height
                centerX + 29, centerY + 37 + 14 + 24,
                48, 14,
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
        }
    }

}
