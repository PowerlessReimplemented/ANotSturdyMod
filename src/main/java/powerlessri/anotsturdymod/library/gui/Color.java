package powerlessri.anotsturdymod.library.gui;

/**
 * A specific, ranged color.
 * <p>
 * Any child class must make sure that all of the fields {@link #red}, {@link #green}, and {@link #blue} are between
 * 0~255, inclusive.
 * </p>
 */
public class Color {

    /**
     * Color objects, does not include alpha value
     */
    private static Color[] colors = new Color[0xffffff + 1];


    public static Color hex(int hex) {
        if (colors[hex] != null) {
            return colors[hex];
        }

        Color color = new Color(hex);
        colors[hex] = color;
        return color;
    }

    public static Color rgb(int red, int green, int blue) {
        int hexValue = (red & 255) << 16 |
                (green & 255) << 8 |
                (blue & 255);
        return hex(hexValue);
    }


    // TODO add HSV and HUE support
    private int hex;

    private int red;
    private int green;
    private int blue;

    public Color(int hex) {
        this.hex = hex & 0xffffff;
        this.updateRGBFromHex();
    }

    public Color(int red, int green, int blue) {
        this.red = red & 0xff;
        this.green = green & 0xff;
        this.blue = blue & 0xff;
        this.updateHexFromRGB();
    }


    protected void updateRGBFromHex() {
        this.red = hex >> 16 & 0xff;
        this.green = hex >> 8 & 0xff;
        this.blue = hex & 0xff;
    }

    protected void updateHexFromRGB() {
        this.hex = red << 16 | green << 8 | blue;
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
    
    @Override
    public String toString() {
        return "Color{" + toStringContents() + "}";
    }
    
    protected String toStringContents() {
        return "hex=" + Integer.toHexString(getHex()) + ", " +
                "rgb=(" + getRed() + ", " + getGreen() + ", " + getBlue() + "), ";
    }
    
}
