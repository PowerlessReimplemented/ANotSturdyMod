package powerlessri.anotsturdymod.varia.render;

import net.minecraft.client.renderer.BufferBuilder;

public class VertexSequenceUtils {

    public static void plainBox(BufferBuilder buffer, int x1, int y1, int x2, int y2, int z, ColorApplier color) {
        // Bottom Left -> Top Left -> Top Right -> Bottom Right
        buffer.pos(x2, y1, z);
        color.applyToVertex(buffer);
        buffer.endVertex();

        buffer.pos(x1, y1, z);
        color.applyToVertex(buffer);
        buffer.endVertex();

        buffer.pos(x1, y2, z);
        color.applyToVertex(buffer);
        buffer.endVertex();

        buffer.pos(x2, y2, z);
        color.applyToVertex(buffer);
        buffer.endVertex();
    }

    public static void verticalGradientBox(BufferBuilder buffer, int x1, int y1, int x2, int y2, int z, ColorApplier top, ColorApplier bottom) {
        // Top Right -> Top Left -> Bottom Left -> Bottom Right
        buffer.pos(x2, y1, z);
        top.applyToVertex(buffer);
        buffer.endVertex();
        
        buffer.pos(x1, y1, z);
        top.applyToVertex(buffer);
        buffer.endVertex();
        
        buffer.pos(x1, y2, z);
        bottom.applyToVertex(buffer);
        buffer.endVertex();
        
        buffer.pos(x2, y2, z);
        bottom.applyToVertex(buffer);
        buffer.endVertex();
    }

    public static void horizontalGradientBox(BufferBuilder buffer, int x1, int y1, int x2, int y2, int z, ColorApplier left, ColorApplier right) {
        // Top Left -> Bottom Left -> Bottom Right -> Top Right
        buffer.pos(x1, y1, z);
        left.applyToVertex(buffer);
        buffer.endVertex();

        buffer.pos(x1, y2, z);
        left.applyToVertex(buffer);
        buffer.endVertex();

        buffer.pos(x2, y2, z);
        right.applyToVertex(buffer);
        buffer.endVertex();

        buffer.pos(x2, y1, z);
        right.applyToVertex(buffer);
        buffer.endVertex();
    }
    
}
