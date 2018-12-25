package powerlessri.anotsturdymod.varia.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import powerlessri.anotsturdymod.library.gui.Color;

import javax.annotation.Nullable;

/**
 * <p>Draw things directly with {@link net.minecraft.client.renderer.Tessellator Tessellator}.</p>
 * <p>
 * Equivalent to create/get a {@link BufferBuilder} and than call corresponding functions in {@link
 * powerlessri.anotsturdymod.varia.render.utils}.
 * </p>
 */
public class Displays {

    public static void drawHorizontalLine(int x1, int y1, int x2, int color) {
        Gui.drawRect(x1, y1, x2, y1 + 1, color);
    }

    public static void drawVerticalLine(int x1, int y1, int y2, int color) {
        Gui.drawRect(x1, y1, x1 + 1, y2, color);
    }

    /**
     * Draws a rectangle with a vertical gradient between the specified colors.
     * <p>x2 and y2 are not included. </p>
     */
    public static void drawVerticalGradientRect(int x1, int y1, int x2, int y2, Color top, Color bottom) {
        BufferBuilder buffer = TessellatorUtils.getGradientVBuffer();
        VertexSequencer.verticalGradientBox(buffer, x1, y1, x2, y2, 0, top, bottom);
        TessellatorUtils.finish();
    }

    /**
     * Draws a rectangle with a horizontal gradient between the specified colors.
     * <p>x2 and y2 are not included. </p>
     */
    public static void drawHorizontalGradientRect(int x1, int y1, int x2, int y2, Color left, Color right) {
        BufferBuilder buffer = TessellatorUtils.getGradientVBuffer();
        VertexSequencer.horizontalGradientBox(buffer, x1, y1, x2, y2, 0, left, right);
        TessellatorUtils.finish();
    }

    
    public static void drawItemStack(ItemStack stack, int x, int y, @Nullable String alt) {
        Minecraft minecraft = Minecraft.getMinecraft();
        RenderItem renderer = minecraft.getRenderItem();
        
        renderer.renderItemAndEffectIntoGUI(stack, x, y);
        renderer.renderItemOverlayIntoGUI(minecraft.fontRenderer, stack, x, y, alt);
    }

    public static void drawFullItemStack(ItemStack stack, int x, int y) {
        drawItemStack(stack, x, y, null);
    }
    
    public static void drawItemStackWithoutSize(ItemStack stack, int x, int y) {
        drawItemStack(stack, x, y, "");
    }
    
}
