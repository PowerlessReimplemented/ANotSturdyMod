package powerlessri.anotsturdymod.varia.render.utils;

import net.minecraft.client.renderer.BufferBuilder;
import powerlessri.anotsturdymod.library.gui.Color;
import powerlessri.anotsturdymod.varia.render.VertexSequencer;

public class LineUtils {

    public static void verticalLine(BufferBuilder buffer, int x, int y, int height, Color color) {
        verticalLine2P(buffer, x, y, y + height, color);
    }

    public static void verticalLine2P(BufferBuilder buffer, int x, int y1, int y2, Color color) {
        VertexSequencer.plainBox(
                buffer,
                x, y1,
                x + 1, y2,
                0,
                color);
    }


    public static void horizontalLine(BufferBuilder buffer, int x, int width, int y, Color color) {
        horizontalLine2P(buffer, x, x + width, y, color);
    }

    public static void horizontalLine2P(BufferBuilder buffer, int x1, int x2, int y, Color color) {
        VertexSequencer.plainBox(
                buffer,
                x1, y,
                x2, y + 1,
                0,
                color);
    }
    
}
