package powerlessri.anotsturdymod.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.library.utils.Reference;

public class GuiRemoteEnergyCell extends GuiContainer {
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Reference.MODID, "textures/items/transmutation_orb.png");
    
    private final int GUI_WIDTH = 140;
    private final int GUI_HEIGHT = 100;
    
    public GuiRemoteEnergyCell(Container container, InventoryPlayer inventory) {
        super(container);
    }
    
    private int lineSize = -1;
    private void calculateLineSize() {
        if(lineSize == -1) {
            lineSize = 10;
        }
    }
    
    protected void drawHorizontalLine(int startX, int endX, int y, int color) {
        calculateLineSize();
        drawRect(startX, y, endX + 1, y + lineSize, color);
    }
    protected void drawVerticalLine(int x, int startY, int endY, int color) {
        calculateLineSize();
        drawRect(x, startY + 1, x + lineSize, endY, color);
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        // Upper-left corner position
        int x = (width - GUI_WIDTH) / 2;
        int y = (height - GUI_HEIGHT) / 2;
        
        
//        GlStateManager.color(1, 1, 1);
//        mc.getTextureManager().bindTexture(BG_TEXTURE);
//        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
        
        
        drawRect(x, y, x + GUI_WIDTH, y + GUI_HEIGHT, 0xc6c6c6ff);
        
        // +2 to create the curve effect on the edge
        // Side black lines
        this.drawVerticalLine(x, y + 2, y + GUI_HEIGHT - 2, 0x000000ff);
        this.drawVerticalLine(x + GUI_WIDTH, y - 2, y + GUI_HEIGHT + 2, 0x000000ff);
        // Top and bottom black lines
        this.drawHorizontalLine(x + 2, x + GUI_WIDTH + 2, y, 0x000000ff);
        this.drawHorizontalLine(x + 2, x + GUI_WIDTH + 2, y + GUI_HEIGHT, 0x000000ff);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        
    }
    
}
