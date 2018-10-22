package powerlessri.anotsturdymod.varia.general;

public class MathUtils {

    private MathUtils() {
    }

    public static int arrayIndex2D(int x, int y, int width) {
        return x + y * width;
    }

    public static int pow(int base, int exponent) {
        // TODO own pow() implementation
        return (int) Math.pow(base, exponent);
    }

    public static int pow(int base) {
        return base * base;
    }

}
