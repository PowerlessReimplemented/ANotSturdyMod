package powerlessri.anotsturdymod.library.gui;

import net.minecraft.client.renderer.BufferBuilder;

public class ExtendedColor extends Color {

    public static ExtendedColor hexSolid(int hex) {
        return hex(hex, 255);
    }

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
        return new ExtendedColor(red, green, blue, alpha);
    }
    
    
    private int alpha;

    public ExtendedColor(int red, int green, int blue, int alpha) {
        super(red, green, blue);
        this.alpha = alpha;
    }
    

    @Override
    public void applyToVertex(BufferBuilder buffer) {
        buffer.color(getRed(), getGreen(), getBlue(), getAlpha());
    }


    public int getAlpha() {
        return alpha;
    }
    
}
