package powerlessri.anotsturdymod.varia.render;

import net.minecraft.client.renderer.BufferBuilder;
import powerlessri.anotsturdymod.library.gui.Color;

public class BoxUtils {

    public static void gradientOutlinedBox(BufferBuilder buffer, int x1, int y1, int x2, int y2, int borderWidth, Color top, Color bottom, Color colorFirst, Color colorSecond) {
        addBorderVertexes(buffer, x1, y1, x2, y2, borderWidth, colorFirst, colorSecond); 
        VertexSequencer.verticalGradientBox(buffer, x1 + borderWidth, y1 + borderWidth, x2 - borderWidth, y2 - borderWidth, 0, top, bottom);
    }
    
    public static void outlinedBox(BufferBuilder buffer, int x1, int y1, int x2, int y2, int borderWidth, Color background, Color colorFirst, Color colorSecond) {
        addBorderVertexes(buffer, x1, y1, x2, y2, borderWidth, colorFirst, colorSecond);
        VertexSequencer.plainBox(buffer, x1 + borderWidth, y1 + borderWidth, x2 - borderWidth, y2 - borderWidth, 0, background);
    }
    
    
    public static void addBorderVertexes(BufferBuilder buffer, int x1, int y1, int x2, int y2, int borderWidth, Color colorFirst, Color colorSecond) {
        int innerX1 = x1 + borderWidth;
        int innerY1 = y1 + borderWidth;
        int innerX2 = x2 - borderWidth;
        int innerY2 = y2 - borderWidth;

        // Border top
        VertexSequencer.plainBox(buffer, x1, y1, innerX2, innerY1, 0, colorFirst);
        // Border left
        VertexSequencer.plainBox(buffer, x1, innerY1, innerX1, innerY2, 0, colorFirst);

        // Border bottom
        VertexSequencer.plainBox(buffer, x1, innerY2, x2, y2, 0, colorSecond);
        // Border right
        VertexSequencer.plainBox(buffer, innerX2, y1, x2, innerY2, 0, colorSecond);
    }
    
}
