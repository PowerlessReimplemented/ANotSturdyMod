package powerlessri.anotsturdymod.varia.render.style;

import net.minecraft.client.renderer.BufferBuilder;
import powerlessri.anotsturdymod.library.gui.Color;
import powerlessri.anotsturdymod.varia.render.BoxUtils;

public class GLGrayScale {

    public static final int BORDER_WIDTH = 1;
    public static final Color BACKGROUND_COLOR = Color.hexSolid(0x8a8a8a);
    public static final Color BORDER_COLOR_DARK = Color.hexSolid(0x383838);
    public static final Color BORDER_COLOR_LIGHT = Color.hexSolid(0xffffff);

    public static void addConvexBox(BufferBuilder buffer, int x1, int y1, int x2, int y2) {
        BoxUtils.outlinedBox(buffer, x1, y1, x2, y2, BORDER_WIDTH, BACKGROUND_COLOR, BORDER_COLOR_LIGHT, BORDER_COLOR_DARK);
    }

    public static void addConcaveBox(BufferBuilder buffer, int x1, int y1, int x2, int y2) {
        BoxUtils.outlinedBox(buffer, x1, y1, x2, y2, BORDER_WIDTH, BACKGROUND_COLOR, BORDER_COLOR_DARK, BORDER_COLOR_LIGHT);
    }


    public static void vanillaBorderedBox(BufferBuilder buffer, int x1, int y1, int x2, int y2, boolean invert) {
        if (invert) {
            addConcaveBox(buffer, x1, y1, x2, y2);
        } else {
            addConvexBox(buffer, x1, y1, x2 ,y2);
        } 
    }

    public static void vanillaBorder(BufferBuilder buffer, int x1, int y1, int x2, int y2, boolean invert) {
        if (invert) { 
            BoxUtils.addBorderVertexes(buffer, x1, y1, x2, y2, 1, GLGrayScale.BORDER_COLOR_DARK, GLGrayScale.BORDER_COLOR_LIGHT);
        } else {
            BoxUtils.addBorderVertexes(buffer, x1, y1, x2, y2, 1, GLGrayScale.BORDER_COLOR_LIGHT, GLGrayScale.BORDER_COLOR_DARK);
        }
    }

}
