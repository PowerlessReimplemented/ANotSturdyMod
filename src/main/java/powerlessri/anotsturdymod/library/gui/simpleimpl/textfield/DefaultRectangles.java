package powerlessri.anotsturdymod.library.gui.simpleimpl.textfield;

import net.minecraft.client.renderer.BufferBuilder;
import powerlessri.anotsturdymod.library.gui.Color;
import powerlessri.anotsturdymod.library.gui.simpleimpl.IRectangleRenderer;
import powerlessri.anotsturdymod.varia.render.TESRStateManager;
import powerlessri.anotsturdymod.varia.render.VertexSequencer;

public final class DefaultRectangles {

    private DefaultRectangles() {
    }


    public static final Color BLACK = Color.hex(0x000000);
    public static final Color TEXTFIELD_GRAY = Color.hex(0xA0A0A0);


    public static final IRectangleRenderer VANILLA_BLACK_GRAY_BOX = (x, y, width, height) -> {
        BufferBuilder buffer = TESRStateManager.getColorVBuffer();
        int x2 = x + width;
        int y2 = y + height;
        VertexSequencer.plainBox(buffer, x + 1, y + 1, x2 - 1, y2 - 1, 0, TEXTFIELD_GRAY);
        VertexSequencer.plainBox(buffer, x, y, x2, y2, 0, BLACK);
        TESRStateManager.finish();
    };

}
