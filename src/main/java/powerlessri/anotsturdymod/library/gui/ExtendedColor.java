package powerlessri.anotsturdymod.library.gui;

import net.minecraft.client.renderer.BufferBuilder;

public class ExtendedColor extends Color {

    public static ExtendedColor hex(int hex) {
        int alpha = hex & 255;
        // Remove alpha at the lower end
        return hex(hex >> 8, alpha);
    }

    public static ExtendedColor hex(int hex, int alpha) {
        int red = hex >> 16 & 255;
        int green = hex >> 8 & 255;
        int blue = hex & 255;
        return rgb(red, green, blue, alpha);
    }

    public static ExtendedColor rgb(int red, int green, int blue, int alpha) {
        ExtendedColor color = new ExtendedColor();
        color.red = red;
        color.green = green;
        color.blue = blue;
        color.alpha = alpha;
        return color;
    }
    
    
    protected int alpha;


    @Override
    public void applyToVertex(BufferBuilder buffer) {
        buffer.color(red, green, blue, alpha);
    }
    
}
