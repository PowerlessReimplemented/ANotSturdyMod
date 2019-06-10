package powerlessri.anotsturdymod.library.block.rotation;

import net.minecraft.block.BlockState;
import net.minecraft.state.DirectionProperty;
import net.minecraft.util.Direction;
import powerlessri.anotsturdymod.varia.math.ExtendedAABB;

/**
 * Internal implementation for {@link BlockRotationHandler}. Represents a block that can be rotated to be facing all 4 horizontal sides.
 */
class HandlerHorizontalRotations extends BlockRotationHandler {

    HandlerHorizontalRotations(ExtendedAABB base) {
        this("facing", base);
    }

    HandlerHorizontalRotations(String propertyName, ExtendedAABB base) {
        this.facing = DirectionProperty.create(propertyName, EBlockRotationType.HORIZONTAL.getGuavaFilter());
        this.base = base;
        // According to EnumFacing#getHorizontalIndex, the order is S-W-N-E
        this.rotatedBoundingBoxes = EBlockRotationType.HORIZONTAL.rotateForIndex(base);
    }


    @Override
    public EBlockRotationType getRotationType() {
        return EBlockRotationType.HORIZONTAL;
    }

    @Override
    public ExtendedAABB getBoundingBoxFor(BlockState state) {
        Direction facing = state.get(this.facing);
        return this.rotatedBoundingBoxes.get(facing.getHorizontalIndex());
    }

}
