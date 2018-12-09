package powerlessri.anotsturdymod.varia.render;

import net.minecraft.client.renderer.BufferBuilder;
import powerlessri.anotsturdymod.library.gui.Color;

public class VertexSequencer {

    public static void plainBox(BufferBuilder buffer, int x1, int y1, int x2, int y2, int z, Color color) {
        // Bottom Left -> Top Left -> Top Right -> Bottom Right
        buffer.pos(x2, y1, z);
        addColorToBuffer(buffer, color);
        buffer.endVertex();

        buffer.pos(x1, y1, z);
        addColorToBuffer(buffer, color);
        buffer.endVertex();

        buffer.pos(x1, y2, z);
        addColorToBuffer(buffer, color);
        buffer.endVertex();

        buffer.pos(x2, y2, z);
        addColorToBuffer(buffer, color);
        buffer.endVertex();
    }

    public static void verticalGradientBox(BufferBuilder buffer, int x1, int y1, int x2, int y2, int z, Color top, Color bottom) {
        // Top Right -> Top Left -> Bottom Left -> Bottom Right
        buffer.pos(x2, y1, z);
        addColorToBuffer(buffer, top);
        buffer.endVertex();

        buffer.pos(x1, y1, z);
        addColorToBuffer(buffer, top);
        buffer.endVertex();

        buffer.pos(x1, y2, z);
        addColorToBuffer(buffer, bottom);
        buffer.endVertex();

        buffer.pos(x2, y2, z);
        addColorToBuffer(buffer, bottom);
        buffer.endVertex();
    }

    public static void horizontalGradientBox(BufferBuilder buffer, int x1, int y1, int x2, int y2, int z, Color left, Color right) {
        // Top Left -> Bottom Left -> Bottom Right -> Top Right
        buffer.pos(x1, y1, z);
        addColorToBuffer(buffer, left);
        buffer.endVertex();

        buffer.pos(x1, y2, z);
        addColorToBuffer(buffer, left);
        addColorToBuffer(buffer, left);

        buffer.pos(x2, y2, z);
        addColorToBuffer(buffer, right);
        buffer.endVertex();

        buffer.pos(x2, y1, z);
        addColorToBuffer(buffer, right);
        buffer.endVertex();
    }


    private static void addColorToBuffer(BufferBuilder buffer, Color color) {
        buffer.color(color.getRed(), color.getGreen(), color.getBlue(), 255);
    }
    
}
