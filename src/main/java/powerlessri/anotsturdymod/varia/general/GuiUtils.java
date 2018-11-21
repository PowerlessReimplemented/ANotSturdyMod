package powerlessri.anotsturdymod.varia.general;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiUtils {
    
    private GuiUtils() {
    }


    public static void useGradientGLStates() {
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
    }

    public static void usePlainColorGLStates() {
        GlStateManager.disableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void useTextureGLStates() {
        GlStateManager.enableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.color(1, 1, 1);
    }
    
}
