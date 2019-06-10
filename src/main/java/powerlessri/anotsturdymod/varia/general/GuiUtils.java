package powerlessri.anotsturdymod.varia.general;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class GuiUtils {

    private GuiUtils() {
    }


    public static void useGradientGLStates() {
        GlStateManager.disableTexture();
        GlStateManager.disableAlphaTest();
        GlStateManager.enableBlend();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
    }

    public static void usePlainColorGLStates() {
        GlStateManager.disableTexture();
        GlStateManager.disableBlend();
    }
    
    public static void useTextureGLStates() {
        GlStateManager.enableTexture();
        GlStateManager.disableAlphaTest();
        GlStateManager.disableBlend();
        GlStateManager.color3f(1, 1, 1);
    }
    
}
