package powerlessri.anotsturdymod.library.gui;

import net.minecraft.client.renderer.BufferBuilder;

public class Color {

    /**
     * Color objects, does not include alpha value
     */
    private static Color[] colors = new Color[0xffffff + 1];
    
    
    public static Color hex(int hex) {
        if (colors[hex] != null) {
            return colors[hex];
        }
        
        int red = hex >> 16 & 255;
        int green = hex >> 8 & 255;
        int blue = hex & 255;
        // Caching is done in the method already
        return rgb(red, green, blue);
    }
    
    public static Color rgb(int red, int green, int blue) {
        int hex = getHex(red, green, blue);
        if (colors[hex] != null) {
            return colors[hex];
        }
        
        Color color = new Color();
        color.red = red;
        color.green = green;
        color.blue = blue;
        
        colors[hex] = color;
        return color;
    }
    
    
    private static int getHex(int red, int green, int blue) {
        return (red & 255) << 16 |
                (green & 255) << 8 |
                (blue & 255);
    }
    

    protected int red;
    protected int green;
    protected int blue;
    
    public void applyToVertex(BufferBuilder buffer) {
        buffer.color(red, green, blue, 255);
    }


    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }
    
}
