package powerlessri.anotsturdymod.blocks.gui.base;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class TextureWrapper {

    /**
     * Construct a TextureWrapper using two points instead of 
     * top-left corner + width + height of the rectangle
     */
    public static TextureWrapper of1(ResourceLocation texture, int startX, int startY, int endX, int endY) {
        return of(texture, startX, startY, Math.abs(endX - startX), Math.abs(endY - startY));
    }

    /**
     * Construct a TextureWrapper with a texture file
     * and a rectangle (top-left corner + width + height).
     */
    public static TextureWrapper of(ResourceLocation texture, int startX, int startY, int width, int height) {
        return new TextureWrapper(texture, startX, startY, width, height);
    }
    
    
    private ResourceLocation texture;
    
    private int startX;
    private int startY;
    private int width;
    private int height;

    protected TextureWrapper(ResourceLocation texture, int startX, int startY, int width, int height) {
        this.texture = texture;
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
    }


    public void draw(GuiScreen gui, int baseX, int baseY) {
        GlStateManager.color(1,1,1);
        
        gui.mc.renderEngine.bindTexture(texture);
        gui.drawTexturedModalRect(baseX, baseY, startX, startY, width, height);
    }
    
}
