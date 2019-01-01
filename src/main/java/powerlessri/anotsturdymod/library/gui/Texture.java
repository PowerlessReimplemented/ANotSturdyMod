package powerlessri.anotsturdymod.library.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import powerlessri.anotsturdymod.varia.general.GuiUtils;
import powerlessri.anotsturdymod.varia.render.TessellatorUtils;
import powerlessri.anotsturdymod.varia.render.VertexSequencer;

import javax.xml.bind.annotation.XmlType.DEFAULT;

/**
 * Wraps around a ResourceLocation, used to simplify drawing 2D textures.
 */
public class Texture {

    /**
     * Copied from vanilla code {@link net.minecraft.client.gui.Gui#drawTexturedModalRect(int, int, int, int, int, int) Gui#drawTexturedModalRect}
     */
    public static final float UV_MULTIPLIER = 0.00390625f;
    /**
     * Default z value which will be used.
     */
    public static final int DEFAULT_Z = 0;

    /**
     * Construct a Texture using two points instead of
     * top-left corner + width + height of the rectangle
     */
    public static Texture ofPoints(ResourceLocation texture, int startX, int startY, int endX, int endY) {
        return of(texture, startX, startY, Math.abs(endX - startX), Math.abs(endY - startY));
    }

    /**
     * Construct a Texture with a texture file
     * and a rectangle (top-left corner + width + height).
     */
    public static Texture of(ResourceLocation texture, int startX, int startY, int width, int height) {
        return new Texture(texture, startX, startY, width, height);
    }


    public final ResourceLocation texture;
    
    // Coordinates related to the texture
    public final int x1;
    public final int y1;
    
    // Dimension of the texture
    public final int width;
    public final int height;
    
    public final float u1;
    public final float v1;
    public final float u2;
    public final float v2;
    
    protected Texture(ResourceLocation texture, int startX, int startY, int width, int height) {
        this.texture = texture;
        this.x1 = startX;
        this.y1 = startY;
        this.width = width;
        this.height = height;
        this.u1 = x1 * UV_MULTIPLIER;
        this.v1 = y1 * UV_MULTIPLIER;
        this.u2 = (x1 + width) * UV_MULTIPLIER;
        this.v2 = (y1 + height) * UV_MULTIPLIER;
    }
    

    public void draw(int x, int y) {
        drawInternal(Minecraft.getMinecraft(), x, y);
    }

    private void drawInternal(Minecraft minecraft, int screenX1, int screenY1) {
        BufferBuilder buffer = TessellatorUtils.getTextureVBuffer();
        {
            int screenX2 = screenX1 + width;
            int screenY2 = screenY1 + height;
            
            minecraft.getTextureManager().bindTexture(texture);
            VertexSequencer.texturedBox(buffer, screenX1, screenY1, screenX2, screenY2, DEFAULT_Z, u1, v1, u2, v2);
        }
        TessellatorUtils.finish();
    }
    
}
