package powerlessri.anotsturdymod.varia.render;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import powerlessri.anotsturdymod.varia.general.GuiUtils;

/**
 * Automatically controls start and end of every drawing (using BufferBuilder).
 */
public class TessellatorUtils {
    
    private static Tessellator tessellator = Tessellator.getInstance();
    private static BufferBuilder buffer = tessellator.getBuffer();

    public static BufferBuilder getColorVBuffer() {
        GlStateManager.disableTexture2D();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        return buffer;
    }
    
    public static BufferBuilder getGradientVBuffer() {
        GuiUtils.useGradientGLStates();
        return getColorVBuffer();
    }
    
    public static BufferBuilder getTextureVBuffer() {
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        return buffer;
    }
    
    
    public static void draw() {
        tessellator.draw();
    }
    
    public static void finish() {
        draw();
        // Most of Minecraft codes uses 2d texture, if we don't re-enable it, it might cause problems (e.g. fails to render text)
        GlStateManager.enableTexture2D();
    }
    
}
