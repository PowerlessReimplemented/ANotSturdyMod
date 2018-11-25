package powerlessri.anotsturdymod.library.gui;

import net.minecraft.client.renderer.BufferBuilder;

/**
 * Extra alpha value support for {@link Color} objects.
 * <p>Hex color value is stored in form of RGBA.</p>
 */
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


    /**
     * Hex color in form of RGBA, replaces hex color in RGB in parent code.
     */
    private int alphaHex;
    
    private int alpha;

    public ExtendedColor(int red, int green, int blue, int alpha) {
        super(red, green, blue);
        this.alpha = alpha;
        this.updateHexFromRGB();
    }
    

    @Override
    public void applyToVertex(BufferBuilder buffer) {
        buffer.color(getRed(), getGreen(), getBlue(), getAlpha());
    }

    @Override
    protected void updateRGBFromHex() {
        super.updateRGBFromHex();
        alpha = alphaHex & 255;
    }

    /**
     * Syncs both {@link #alphaHex} and (in parent code) hex from stored RGB value.
     */
    @Override
    protected void updateHexFromRGB() {
        // Compress RGB into hexadecimal
        super.updateHexFromRGB();
        // Append alpha onto hex color
        alphaHex = super.getHex() << 8 | alpha;
    }


    @Override
    public int getHex() {
        return alphaHex;
    }
    
    public int getAlpha() {
        return alpha;
    }
    
}
