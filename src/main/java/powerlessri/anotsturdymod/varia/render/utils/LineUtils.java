package powerlessri.anotsturdymod.varia.render.utils;

import net.minecraft.client.renderer.BufferBuilder;
import powerlessri.anotsturdymod.library.gui.Color;
import powerlessri.anotsturdymod.varia.render.VertexSequencer;

public class LineUtils {

    public static void putVerticalLine(BufferBuilder buffer, int x, int y, int length, Color color) {
        putVerticalLine2P(buffer, x, y, y + length, color);
    }

    public static void putVerticalLine2P(BufferBuilder buffer, int x, int y1, int y2, Color color) {
        VertexSequencer.plainBox(
                buffer,
                x, y1,
                x + 1, y2,
                0,
                color);
    }


    public static void putHorizontalLine(BufferBuilder buffer, int x, int length, int y, Color color) {
        putHorizontalLine2P(buffer, x, x + length, y, color);
    }

    public static void putHorizontalLine2P(BufferBuilder buffer, int x1, int x2, int y, Color color) {
        VertexSequencer.plainBox(
                buffer,
                x1, y,
                x2, y + 1,
                0,
                color);
    }
    
}
