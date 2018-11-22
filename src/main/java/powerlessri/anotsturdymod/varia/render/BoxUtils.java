package powerlessri.anotsturdymod.varia.render;

import net.minecraft.client.renderer.BufferBuilder;

public class BoxUtils {
    
    public static void addOutlinedBox(BufferBuilder buffer, int x1, int y1, int x2, int y2, int borderWidth, ColorApplier background, ColorApplier colorFirst, ColorApplier colorSecond) {
        addBorderVertexes(buffer, x1, y1, x2, y2, borderWidth, colorFirst, colorSecond);
        addBodyVertexes(buffer, x1, y1, x2, y2, borderWidth, background);
    }
    
    private static void addBodyVertexes(BufferBuilder buffer, int x1, int y1, int x2, int y2, int borderWidth, ColorApplier color) {
        VertexSequenceUtils.plainBox(buffer, x1 + borderWidth, y1 + borderWidth, x2 - borderWidth, y2 - borderWidth, 0, color);
    }
    
    private static void addBorderVertexes(BufferBuilder buffer, int x1, int y1, int x2, int y2, int borderWidth, ColorApplier colorFirst, ColorApplier colorSecond) {
        int innerX1 = x1 + borderWidth;
        int innerY1 = y1 + borderWidth;
        int innerX2 = x2 - borderWidth;
        int innerY2 = y2 - borderWidth;

        // Border top
        VertexSequenceUtils.plainBox(buffer, x1, y1, innerX2, innerY1, 0, colorFirst);
        // Border left
        VertexSequenceUtils.plainBox(buffer, x1, innerY1, innerX1, innerY2, 0, colorFirst);

        // Border bottom
        VertexSequenceUtils.plainBox(buffer, x1, innerY2, x2, y2, 0, colorSecond);
        // Border right
        VertexSequenceUtils.plainBox(buffer, innerX2, y1, x2, innerY2, 0, colorSecond);
    }
    
}
