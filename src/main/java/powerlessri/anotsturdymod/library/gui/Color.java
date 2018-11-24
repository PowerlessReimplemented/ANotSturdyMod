package powerlessri.anotsturdymod.library.gui;

import net.minecraft.client.renderer.BufferBuilder;

public class Color {

    /**
     * Color objects, does not include alpha value
     */
    private static Color[] colors = new Color[0xffffff + 1];
    

    public static Color hexSolid(int hex) {
        return hex(hex, 255);
    }
    
    public static Color hex(int hex) {
        int alpha = hex & 255;
        // Remove alpha value at the end of color
        return hex(hex >> 8, alpha);
    }
    
    public static Color hex(int hex, int alpha) {
        if (colors[hex] != null) {
            return colors[hex];
        }
        
        int red = hex >> 16 & 255;
        int green = hex >> 8 & 255;
        int blue = hex & 255;
        // Caching is done in the method already
        return rgb(red, green, blue, alpha);
    }
    
    public static Color rgb(int red, int green, int blue) {
        return rgb(red, green, blue, 255);
    }

    public static Color rgb(int red, int green, int blue, int alpha) {
        int hex = getHex(red, green, blue);
        if (colors[hex] != null) {
            return colors[hex];
        }
        
        Color color = new Color();
        color.red = red;
        color.green = green;
        color.blue = blue;
        color.alpha = alpha;
        
        colors[hex] = color;
        return color;
    }
    
    
    private static int getHex(int red, int green, int blue) {
        return (red & 255) << 16 |
                (green & 255) << 8 |
                (blue & 255);
    }
    

    private int red;
    private int green;
    private int blue;
    private int alpha;
    
    public void applyToVertex(BufferBuilder buffer) {
        buffer.color(red, green, blue, alpha);
    }
    
}
