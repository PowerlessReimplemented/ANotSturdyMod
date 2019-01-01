package powerlessri.anotsturdymod.varia.render;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import powerlessri.anotsturdymod.varia.general.GuiUtils;

/**
 * Wraps commonly used operations about {@link Tessellator} and {@link GlStateManager}.
 */
public class TESRStateManager {

    private static Tessellator tessellator = Tessellator.getInstance();
    private static BufferBuilder buffer = tessellator.getBuffer();

    public static BufferBuilder getTextureVBuffer() {
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        return buffer;
    }

    public static BufferBuilder getColorVBuffer() {
        GlStateManager.disableTexture2D();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        return buffer;
    }

    public static BufferBuilder getGradientVBuffer() {
        GuiUtils.useGradientGLStates();
        return getColorVBuffer();
    }


    public static void draw() {
        tessellator.draw();
    }

    public static void finish() {
        draw();
        // Minecraft's rendering mechanic, especially font rendering, fails if GlStateManager.texture2D isn't enabled yet.
        GlStateManager.enableTexture2D();
    }

}
