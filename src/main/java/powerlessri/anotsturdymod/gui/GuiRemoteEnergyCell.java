package powerlessri.anotsturdymod.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class GuiRemoteEnergyCell extends GuiContainer {
    
    public GuiRemoteEnergyCell(Container container, InventoryPlayer inventory) {
        super(container);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    }
    
}
