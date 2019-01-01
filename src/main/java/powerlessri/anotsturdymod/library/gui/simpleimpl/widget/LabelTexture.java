package powerlessri.anotsturdymod.library.gui.simpleimpl.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.library.gui.api.ITextureElement;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.varia.render.VertexSequencer;

/**
 * Wraps around a {@link ResourceLocation}, used to simplify drawing 2D textures.
 */
public class LabelTexture extends Label implements ITextureElement {

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
     * Construct a LabelTexture using two points instead of top-left corner + width + height of the rectangle
     */
    public static LabelTexture ofPoints(int x, int y, ResourceLocation texture, int startX, int startY, int endX, int endY) {
        return new LabelTexture(x, y, Math.abs(endX - startX), Math.abs(endY - startY), texture, startX, startY);
    }


    public final ResourceLocation texture;

    /**
     * Top left X coordinate for the texture.
     */
    private final int tx;
    /**
     * Top left Y coordinate for the texture.
     */
    private final int ty;

    public final float u1;
    public final float v1;
    public final float u2;
    public final float v2;

    public LabelTexture(int x, int y, int width, int height, ResourceLocation texture, int tx, int ty) {
        super(x, y, width, height);
        this.texture = texture;
        this.tx = tx;
        this.ty = ty;
        this.u1 = tx * UV_MULTIPLIER;
        this.v1 = ty * UV_MULTIPLIER;
        this.u2 = (tx + width) * UV_MULTIPLIER;
        this.v2 = (ty + height) * UV_MULTIPLIER;
    }


    @Override
    public void draw(GuiDrawBackgroundEvent event) {
        this.draw(getActualX(), getActualY());
    }

    @Override
    public void setPass(BufferBuilder buffer, int screenX1, int screenY1) {
        int screenX2 = screenX1 + getWidth();
        int screenY2 = screenY1 + getHeight();

        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        VertexSequencer.texturedBox(buffer, screenX1, screenY1, screenX2, screenY2, DEFAULT_Z, u1, v1, u2, v2);
    }


    @Override
    public int getXTex() {
        return tx;
    }

    @Override
    public int getYTex() {
        return ty;
    }

}
