package powerlessri.anotsturdymod.varia.render.style;

import net.minecraft.client.renderer.BufferBuilder;
import powerlessri.anotsturdymod.library.gui.Color;
import powerlessri.anotsturdymod.varia.render.utils.BoxUtils;

public class VanillaPresets {

    public static final int BORDER_WIDTH = 1;
    public static final Color BACKGROUND_COLOR = Color.hex(0x8a8a8a);
    public static final Color BORDER_COLOR_DARK = Color.hex(0x383838);
    public static final Color BORDER_COLOR_LIGHT = Color.hex(0xffffff);

    public static void putConvexBox(BufferBuilder buffer, int x1, int y1, int x2, int y2) {
        BoxUtils.putBorderedBox(buffer, x1, y1, x2, y2, BORDER_WIDTH, BACKGROUND_COLOR, BORDER_COLOR_LIGHT, BORDER_COLOR_DARK);
    }

    public static void putConcaveBox(BufferBuilder buffer, int x1, int y1, int x2, int y2) {
        BoxUtils.putBorderedBox(buffer, x1, y1, x2, y2, BORDER_WIDTH, BACKGROUND_COLOR, BORDER_COLOR_DARK, BORDER_COLOR_LIGHT);
    }

}
