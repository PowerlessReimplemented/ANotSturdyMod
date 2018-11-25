package powerlessri.anotsturdymod.library.gui;

import net.minecraft.client.renderer.BufferBuilder;

public class Color {

    /**
     * Color objects, does not include alpha value
     */
    private static Color[] colors = new Color[0xffffff + 1];

    private static Color createAndCache(int index, int red, int green, int blue) {
        Color color = new Color(red, green, blue);
        colors[index] = color;
        return color;
    }


    public static Color hex(int hex) {
        if (colors[hex] != null) {
            return colors[hex];
        }
        
        int red = hex >> 16 & 255;
        int green = hex >> 8 & 255;
        int blue = hex & 255;

        return createAndCache(hex, red, green, blue);
    }

    public static Color rgb(int red, int green, int blue) {
        int hex = getHex(red, green, blue);
        if (colors[hex] != null) {
            return colors[hex];
        }

        return createAndCache(hex, red, green, blue);
    }


    public static int getHex(int red, int green, int blue) {
        return (red & 255) << 16 |
                (green & 255) << 8 |
                (blue & 255);
    }
    
    // TODO add HSV and HUE support
    private int hex;
    
    private int red;
    private int green;
    private int blue;

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.updateHexFromRGB();
    }
    

    public void applyToVertex(BufferBuilder buffer) {
        buffer.color(getRed(), getGreen(), getBlue(), 255);
    }
    
    protected void updateRGBFromHex() {
        red = hex >> 16 & 255;
        green = hex >> 8 & 255;
        blue = hex & 255;
    }
    
    protected void updateHexFromRGB() {
        hex = getHex(red, green, blue);
    }
    
    
    public int getHex() {
        return hex;
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
