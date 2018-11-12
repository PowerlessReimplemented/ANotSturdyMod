package powerlessri.anotsturdymod.library.gui.immutableimpl.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Util;
import powerlessri.anotsturdymod.library.gui.immutableimpl.AbstractComponent;
import powerlessri.anotsturdymod.varia.general.GuiUtils;
import powerlessri.anotsturdymod.varia.general.Utils;

public class ButtonGradient extends AbstractComponent {
    
    private int width;
    private int height;

    public ButtonGradient(int relativeX, int relativeY, int width, int height) {
        super(relativeX, relativeY);
        
        this.width = width;
        this.height = height;
    }
    

    @Override
    public boolean isLeafComponent() {
        return true;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void draw() {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        
        buffer.pos(getAbsoluteX2(), getAbsoluteY(), z).color(255, 255, 255, 255).endVertex();
        buffer.pos(getAbsoluteX(), getAbsoluteY(), z).color(255, 255, 255, 255).endVertex();
        buffer.pos(getAbsoluteX(), getAbsoluteY2(), z).color(37, 37, 37, 255).endVertex();
        buffer.pos(getAbsoluteX2(), getAbsoluteY2(), z).color(37, 37, 37, 255).endVertex();
        
        tessellator.draw();
    }
    
}
