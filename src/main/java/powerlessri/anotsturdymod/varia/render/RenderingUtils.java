package powerlessri.anotsturdymod.varia.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.library.gui.Color;
import powerlessri.anotsturdymod.library.gui.simpleimpl.widget.LabelTexture;

import javax.annotation.Nullable;

/**
 * <p>
 * Draw things directly with {@link net.minecraft.client.renderer.Tessellator Tessellator}.
 * </p>
 */
public class RenderingUtils {

    /**
     * 1/256, which is what vanilla assumes the texture size is.
     * <p>
     * Copied from vanilla code {@link Gui#drawTexturedModalRect(int, int, int, int, int, int)
     * Gui#drawTexturedModalRect}
     * </p>
     */
    public static final float UV_MULTIPLIER = 0.00390625f;

    public static void drawHorizontalLine(int x1, int y1, int x2, int color) {
        Gui.drawRect(x1, y1, x2, y1 + 1, color);
    }

    public static void drawVerticalLine(int x1, int y1, int y2, int color) {
        Gui.drawRect(x1, y1, x1 + 1, y2, color);
    }

    public static void drawTexturedRect(int bx, int by, ResourceLocation texture, int tx, int ty, int width, int height) {
        BufferBuilder buffer = TESRStateManager.getTextureVBuffer();
        {
            int x2 = bx + width;
            int y2 = by + height;

            float u1 = bx * UV_MULTIPLIER;
            float v1 = by * UV_MULTIPLIER;
            float u2 = x2 * UV_MULTIPLIER;
            float v2 = y2 * UV_MULTIPLIER;
            VertexSequencer.texturedBox(buffer, bx, by, x2, y2, LabelTexture.DEFAULT_Z, u1, v1, u2, v2);
        }
        TESRStateManager.finish();
    }

    /**
     * Draws a rectangle with a vertical gradient between the specified colors.
     * <p>x2 and y2 are not included. </p>
     */
    public static void drawVerticalGradientRect(int x1, int y1, int x2, int y2, Color top, Color bottom) {
        BufferBuilder buffer = TESRStateManager.getGradientVBuffer();
        VertexSequencer.verticalGradientBox(buffer, x1, y1, x2, y2, 0, top, bottom);
        TESRStateManager.finish();
    }

    /**
     * Draws a rectangle with a horizontal gradient between the specified colors.
     * <p>x2 and y2 are not included. </p>
     */
    public static void drawHorizontalGradientRect(int x1, int y1, int x2, int y2, Color left, Color right) {
        BufferBuilder buffer = TESRStateManager.getGradientVBuffer();
        VertexSequencer.horizontalGradientBox(buffer, x1, y1, x2, y2, 0, left, right);
        TESRStateManager.finish();
    }


    public static void drawItemStack(ItemStack stack, int x, int y, @Nullable String alt) {
        Minecraft minecraft = Minecraft.getMinecraft();
        RenderItem renderer = minecraft.getRenderItem();

        RenderHelper.enableGUIStandardItemLighting();
        {
            renderer.renderItemAndEffectIntoGUI(stack, x, y);
            renderer.renderItemOverlayIntoGUI(minecraft.fontRenderer, stack, x, y, alt);
        }
        GlStateManager.disableLighting();
    }

    public static void drawCompleteItemStack(ItemStack stack, int x, int y) {
        drawItemStack(stack, x, y, null);
    }

    public static void drawItemStackWithoutSize(ItemStack stack, int x, int y) {
        drawItemStack(stack, x, y, "");
    }

}
