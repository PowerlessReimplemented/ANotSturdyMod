package powerlessri.anotsturdymod.library.block.rotation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import powerlessri.anotsturdymod.varia.math.ExtendedAABB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public enum EBlockRotationType {

    NONE(facing -> false, facing -> 0),
    ALL(facing -> true, EnumFacing::getIndex),
    HORIZONTAL(facing -> facing.getAxis().isHorizontal(), EnumFacing::getHorizontalIndex),
    VERTICAL(facing -> facing.getAxis().isVertical(), facing -> facing == EnumFacing.UP ? 0 : 1);


    /**
     * Rotate the bounding by by multiple of 90 to
     * <p>
     * Equivalent to {@code EBlockRotationType.ROTATION_PROCESSORS.get(face).apply(base)}.
     * </p>
     *
     * @param base Base bounding box considered facing up
     * @param side Target side to be rotated
     * @return Rotated bounding box facing the given side
     * @see #ROTATION_PROCESSORS
     */
    public static ExtendedAABB rotateToSide(ExtendedAABB base, EnumFacing side) {
        switch (side) {
            case DOWN:
                return base.rotate(Axis.X, 180);
            case UP:
                return base;
            case NORTH:
                return base.rotate(Axis.X, -90);
            case SOUTH:
                return base.rotate(Axis.X, 90);
            case WEST:
                return base.rotate(Axis.Z, 90);
            case EAST:
                return base.rotate(Axis.Z, -90);
        }

        // This should never happen
        return base;
    }

    /**
     * Series of Functions for rotating a bounding box considered up to other facings.
     * <p>Use {@link #rotateToSide(ExtendedAABB, EnumFacing)} when possible.</p>
     *
     * @see #rotateToSide(ExtendedAABB, EnumFacing)
     */
    public static final ImmutableMap<EnumFacing, Function<ExtendedAABB, ExtendedAABB>> ROTATION_PROCESSORS =
            Maps.immutableEnumMap(
                    ImmutableMap.<EnumFacing, Function<ExtendedAABB, ExtendedAABB>>builder()
                            .put(EnumFacing.UP, base -> base)
                            .put(EnumFacing.DOWN, base -> base.rotate(Axis.X, 180))
                            .put(EnumFacing.NORTH, base -> base.rotate(Axis.X, -90))
                            .put(EnumFacing.SOUTH, base -> base.rotate(Axis.X, 90))
                            .put(EnumFacing.EAST, base -> base.rotate(Axis.Z, -90))
                            .put(EnumFacing.WEST, base -> base.rotate(Axis.Z, 90))
                            .build()
            );


    public final Predicate<EnumFacing> filter;
    public final Function<EnumFacing, Integer> indexGetter;
    public final Comparator<EnumFacing> indexSorter;

    /**
     * Ordered as in {@link EnumFacing} since Stream.filter(Predicate<?>) is ordered.
     */
    public final EnumFacing[] includedFaces;
    public final EnumFacing[] sortedFaces;

    private EBlockRotationType(Predicate<EnumFacing> filter, Function<EnumFacing, Integer> indexGetter) {
        this.filter = filter;
        this.indexGetter = indexGetter;
        this.indexSorter = Comparator.comparing(indexGetter);

        this.includedFaces = Arrays.stream(EnumFacing.VALUES)
                .filter(filter)
                .toArray(EnumFacing[]::new);

        EnumFacing[] sortedFaces = Arrays.copyOf(includedFaces, includedFaces.length);
        Arrays.sort(sortedFaces, indexSorter);
        this.sortedFaces = sortedFaces;
    }


    /**
     * Rotate the given bounding box to simulate it facing different directions.
     * <p>Directions used for rotation depend on the enum choice.</p>
     *
     * @param base Bounding box considered facing up
     * @return An unsorted array of rotated bounding boxes
     */
    public ImmutableList<ExtendedAABB> rotate(ExtendedAABB base) {
        return ImmutableList.copyOf(this.rotateInternal(includedFaces, base));
    }

    /**
     * Sort the rotated bounding boxes so the index match to the index given in {@link EnumFacing}.
     *
     * @param base Bounding box considered facing up
     * @return Sorted version of {@link #rotate(ExtendedAABB)}
     */
    public ImmutableList<ExtendedAABB> rotateForIndex(ExtendedAABB base) {
        return ImmutableList.copyOf(this.rotateInternal(sortedFaces, base));
    }

    private List<ExtendedAABB> rotateInternal(EnumFacing[] faces, ExtendedAABB base) {
        List<ExtendedAABB> rotated = new ArrayList<>();
        for (EnumFacing face : includedFaces) {
            rotated.add(rotateToSide(base, face));
        }
        return rotated;
    }


    public Predicate<EnumFacing> getFilter() {
        return filter;
    }

    /**
     * Predicate in form of Guava's API, used for {@link net.minecraft.block.properties.PropertyDirection} to simply code.
     *
     * @return Guava's {@link com.google.common.base.Predicate Predicate} equivalent to {@link #getFilter()}.
     * @deprecated Use {@link #getFilter()} whenever possible
     */
    @Deprecated
    public com.google.common.base.Predicate<EnumFacing> getGuavaFilter() {
        return filter::test;
    }

}
