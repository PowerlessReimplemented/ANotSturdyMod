package powerlessri.anotsturdymod.varia.general;

public class MathUtils {

    private MathUtils() {
    }

    public static int arrayIndex2D(int x, int y, int width) {
        return x + y * width;
    }

    public static int pow(int base, int exponent) {
        return (int) Math.pow(base, exponent);
    }

    public static int pow(int base) {
        return base * base;
    }


    /**
     * Loop the number around until it is inside the given range 0 ~ (arrayLength - 1).
     *
     * @param index       The index to be adjusted
     * @param arrayLength Upper limit for the index
     * @return The adjusted index
     */
    public static int loopIndexAround(int index, int arrayLength) {
        if (index >= arrayLength) {
            return loopIndexAround(index - arrayLength, arrayLength);
        }
        if (index < 0) {
            // + because index would have a negative sign
            return loopIndexAround(arrayLength + index, arrayLength);
        }
        return index;
    }

}
