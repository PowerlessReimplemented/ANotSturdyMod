package powerlessri.anotsturdymod.library.block.rotation;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import powerlessri.anotsturdymod.varia.math.ExtendedAABB;

public abstract class BlockRotationHandler {

    public static BlockRotationHandler none(ExtendedAABB boundingBox) {
        return new HandlerNoRotation(boundingBox);
    }

    public static BlockRotationHandler all(ExtendedAABB base) {
        return new HandlerAllRotations(base);
    }

    public static BlockRotationHandler horizontal(ExtendedAABB base) {
        return new HandlerHorizontalRotations(base);
    }


    /**
     * Alias to {@link EBlockRotationType#rotateToSide(ExtendedAABB, EnumFacing)}
     */
    public static ExtendedAABB rotateToSide(ExtendedAABB base, EnumFacing direction) {
        return EBlockRotationType.rotateToSide(base, direction);
    }


    protected PropertyDirection facing;

    protected ExtendedAABB base;
    protected ImmutableList<ExtendedAABB> rotatedBoundingBoxes;


    public abstract EBlockRotationType getRotationType();

    /**
     * The auto created {@link PropertyDirection} for storing sideness.
     * <p>
     * This property should be used for creating {@link net.minecraft.block.state.BlockStateContainer BlockStateContainer} in {@link
     * Block#createBlockState()}.
     * </p>
     */
    public PropertyDirection getPropertyFacing() {
        return this.facing;
    }

    /**
     * @return The bounding box passed to the constructor
     */
    public ExtendedAABB getBoundingBox() {
        return this.base;
    }

    public abstract ExtendedAABB getBoundingBoxFor(IBlockState state);

    /**
     * Get all possible rotated bounding boxes for the specified base bounding box.
     */
    public ImmutableList<ExtendedAABB> getRotatedBoundingBoxes() {
        return this.rotatedBoundingBoxes;
    }


    private String toStringResult = "";

    @Override
    public String toString() {
        if (this.toStringResult.isEmpty()) {
            this.toStringResult = MoreObjects.toStringHelper(this)
                    .add("facing", facing)
                    .add("base", base)
                    .add("rotatedBoundingBoxes", rotatedBoundingBoxes)
                    .toString();
        }
        return this.toStringResult;
    }

}



