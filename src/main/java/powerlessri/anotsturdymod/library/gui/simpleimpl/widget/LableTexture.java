package powerlessri.anotsturdymod.library.gui.simpleimpl.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.varia.render.TESRStateManager;
import powerlessri.anotsturdymod.varia.render.VertexSequencer;

/**
 * Wraps around a ResourceLocation, used to simplify drawing 2D textures.
 */
public class LableTexture extends Label {

    /**
     * Copied from vanilla code {@link net.minecraft.client.gui.Gui#drawTexturedModalRect(int, int, int, int, int, int)
     * Gui#drawTexturedModalRect}
     */
    public static final float UV_MULTIPLIER = 0.00390625f;
    /**
     * Default z value which will be used.
     */
    public static final int DEFAULT_Z = 0;

    /**
     * Construct a LableTexture using two points instead of top-left corner + width + height of the rectangle
     */
    public static LableTexture ofPoints(int x, int y, ResourceLocation texture, int startX, int startY, int endX, int endY) {
        return new LableTexture(x, y, Math.abs(endX - startX), Math.abs(endY - startY), texture, startX, startY);
    }


    public final ResourceLocation texture;

    // Coordinates related to the texture
    private final int x1;
    private final int y1;

    public final float u1;
    public final float v1;
    public final float u2;
    public final float v2;

    public LableTexture(int x, int y, int width, int height, ResourceLocation texture, int startX, int startY) {
        super(x, y, width, height);
        this.texture = texture;
        this.x1 = startX;
        this.y1 = startY;
        this.u1 = x1 * UV_MULTIPLIER;
        this.v1 = y1 * UV_MULTIPLIER;
        this.u2 = (x1 + width) * UV_MULTIPLIER;
        this.v2 = (y1 + height) * UV_MULTIPLIER;
    }


    @Override
    public void draw(GuiDrawBackgroundEvent event) {
        BufferBuilder buffer = TESRStateManager.getTextureVBuffer();
        setVertexes(Minecraft.getMinecraft(), buffer, getActualX(), getActualY());
        TESRStateManager.finish();
    }

    // Used at powerlessri.anotsturdymod.library.gui.simpleimpl.widget.Labels
    void setVertexes(Minecraft minecraft, BufferBuilder buffer, int screenX1, int screenY1) {
        int screenX2 = screenX1 + getWidth();
        int screenY2 = screenY1 + getHeight();

        minecraft.getTextureManager().bindTexture(texture);
        VertexSequencer.texturedBox(buffer, screenX1, screenY1, screenX2, screenY2, DEFAULT_Z, u1, v1, u2, v2);
    }

    
    public int getXTex() {
        return x1;
    }

    public int getYTex() {
        return y1;
    }
    
}
