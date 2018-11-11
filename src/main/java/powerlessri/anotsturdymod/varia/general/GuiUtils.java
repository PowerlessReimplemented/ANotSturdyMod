package powerlessri.anotsturdymod.varia.general;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiUtils {
    
    private GuiUtils() {
    }
    
    
    public static void resetGuiGlStates() {
        GlStateManager.color(1, 1, 1);
        GlStateManager.disableBlend();
    }
    
}
