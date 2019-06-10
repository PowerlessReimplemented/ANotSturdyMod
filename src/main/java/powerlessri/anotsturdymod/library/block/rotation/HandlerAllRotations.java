package powerlessri.anotsturdymod.library.block.rotation;

import net.minecraft.block.BlockState;
import net.minecraft.state.DirectionProperty;
import net.minecraft.util.Direction;
import powerlessri.anotsturdymod.varia.math.ExtendedAABB;

/**
 * Internal implementation for {@link BlockRotationHandler}. Represents a block that can be rotated to be facing all 6 sides.
 */
class HandlerAllRotations extends BlockRotationHandler {

    HandlerAllRotations(ExtendedAABB base) {
        this("facing", base);
    }

    HandlerAllRotations(String propertyName, ExtendedAABB base) {
        this.facing = DirectionProperty.create(propertyName, EBlockRotationType.ALL.getGuavaFilter());
        this.base = base;
        // According to EnumFacing#getIndex, the order is D-U-N-S-W-E
        this.rotatedBoundingBoxes = EBlockRotationType.ALL.rotateForIndex(base);
    }


    @Override
    public EBlockRotationType getRotationType() {
        return EBlockRotationType.ALL;
    }

    @Override
    public ExtendedAABB getBoundingBoxFor(BlockState state) {
        Direction facing = state.get(this.facing);
        return this.rotatedBoundingBoxes.get(facing.getIndex());
    }

}
