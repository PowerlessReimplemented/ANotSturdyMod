package powerlessri.anotsturdymod.library.gui;

import net.minecraft.client.renderer.BufferBuilder;

public class ColorApplier {

    public static ColorApplier hexSolid(int hex) {
        return hex(hex, 255);
    }
    
    public static ColorApplier hex(int hex) {
        int alpha = hex & 255;
        // Remove alpha value at the end of color
        return hex(hex >> 8, alpha);
    }
    
    public static ColorApplier hex(int hex, int alpha) {
        int red = hex >> 16 & 255;
        int green = hex >> 8 & 255;
        int blue = hex & 255;
        return rgb(red, green, blue, alpha);
    }
    
    public static ColorApplier rgb(int red, int green, int blue) {
        return rgb(red, green, blue, 255);
    }

    public static ColorApplier rgb(int red, int green, int blue, int alpha) {
        ColorApplier applier = new ColorApplier();
        applier.red = red;
        applier.green = green;
        applier.blue = blue;
        applier.alpha = alpha;
        return applier;
    }
    

    private int red;
    private int green;
    private int blue;
    private int alpha;
    
    public void applyToVertex(BufferBuilder buffer) {
        buffer.color(red, green, blue, alpha);
    }
    
}
