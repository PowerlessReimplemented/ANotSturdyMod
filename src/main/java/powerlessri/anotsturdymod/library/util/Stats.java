package powerlessri.anotsturdymod.library.util;

import java.util.Comparator;
import java.util.stream.Collector;

/**
 * <p>
 * Copied and modified from <i><a href="https://stackoverflow.com/questions/41816264/concise-way-to-get-both-min-and-max-value-of-java-8-stream">Stack
 * Overflow</a></i>
 * </p>
 */
public class Stats<T> {

    public static <T> Collector<T, Stats<T>, Stats<T>> collector(Comparator<? super T> comparator) {
        return Collector.of(
                () -> new Stats<>(comparator),
                Stats::accept,
                Stats::combine,
                Collector.Characteristics.UNORDERED, Collector.Characteristics.IDENTITY_FINISH
        );
    }

    public static <T extends Comparable<? super T>> Collector<T, Stats<T>, Stats<T>> collector() {
        return collector(Comparator.naturalOrder());
    }


    private int count;

    private final Comparator<? super T> comparator;
    private T min;
    private T max;

    public Stats(Comparator<? super T> comparator) {
        this.comparator = comparator;
    }

    /**
     * Number of accepted ({@link #accept(Object)}) other elements.
     */
    public int count() {
        return this.count;
    }

    /**
     * Comparator received in the constructor.
     */
    public Comparator<? super T> comparator() {
        return this.comparator;
    }

    /**
     * Element currently considered minimum by the comparator.
     */
    public T min() {
        return this.min;
    }

    /**
     * Element currently considered maximum by the comparator.
     */
    public T max() {
        return this.max;
    }

    /**
     * Update the minimum and maximum elements with a new value.
     */
    public void accept(T val) {
        if (count == 0) {
            min = val;
            max = val;
        } else if (comparator.compare(val, min) < 0) {
            min = val;
        } else if (comparator.compare(val, max) > 0) {
            max = val;
        }

        count++;
    }

    /**
     * Combine the status of the two status objects.
     * <p>
     * The returned objects depends on the status of the object get invoked. If the current object hasn't been invoked on {@link
     * #accept(Object)} yet, this method will return the parameter instead of modifying and return current object.
     * </p>
     *
     * @param that The other Stat object
     */
    public Stats<T> combine(Stats<T> that) {
        if (this.count == 0) {
            return that;
        }
        if (that.count == 0) {
            return this;
        }

        this.count += that.count;
        if (comparator.compare(that.min, this.min) < 0) {
            this.min = that.min;
        }
        if (comparator.compare(that.max, this.max) > 0) {
            this.max = that.max;
        }

        return this;
    }

}
