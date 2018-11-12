package powerlessri.anotsturdymod.library.gui.immutableimpl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import powerlessri.anotsturdymod.varia.general.GuiUtils;

/**
 * Wraps around a ResourceLocation, used to simplify drawing 2D textures.
 */
public class TextureWrapper {
    
    public static final float UV_MULTIPLIER = 0.00390625f;
    public static final float DEFAULT_Z = 0.0f;

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


    public final ResourceLocation texture;
    
    // Coordinates related to the texture
    public final int x1;
    public final int y1;
    public final int x2;
    public final int y2;
    public final int width;
    public final int height;
    public final float u1;
    public final float v1;
    public final float u2;
    public final float v2;
    
    protected TextureWrapper(ResourceLocation texture, int startX, int startY, int width, int height) {
        this.texture = texture;
        this.x1 = startX;
        this.y1 = startY;
        this.x2 = x1 + width;
        this.y2 = y1 + height;
        this.width = width;
        this.height = height;
        this.u1 = x1 * UV_MULTIPLIER;
        this.v1 = y1 * UV_MULTIPLIER;
        this.u2 = x2 * UV_MULTIPLIER;
        this.v2 = y2 * UV_MULTIPLIER;
    }


    public void draw(GuiScreen gui, int baseX, int baseY) {
        GuiUtils.useTextureGLStates();
        gui.mc.renderEngine.bindTexture(texture);
        gui.drawTexturedModalRect(baseX, baseY, x1, y1, width, height);
    }

    public void draw(int baseX, int baseY) {
        drawInternal(Minecraft.getMinecraft(), baseX, baseY);
    }

    private void drawInternal(Minecraft minecraft, int screenX1, int screenY1) {
        int screenX2 = screenX1 + width;
        int screenY2 = screenY1 + height;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        
        GuiUtils.useTextureGLStates();
        minecraft.renderEngine.bindTexture(texture);
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        // Bottom Left -> Top Left -> Top Right -> Bottom Right
        buffer.pos(screenX1, screenY2, DEFAULT_Z).tex(u1, v2).endVertex();
        buffer.pos(screenX2, screenY2, DEFAULT_Z).tex(u2, v2).endVertex();
        buffer.pos(screenX2, screenY1, DEFAULT_Z).tex(u2, v1).endVertex();
        buffer.pos(screenX1, screenY1, DEFAULT_Z).tex(u1, v1).endVertex();
        
        tessellator.draw();
    }
    
}
