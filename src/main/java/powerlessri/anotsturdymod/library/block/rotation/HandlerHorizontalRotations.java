package powerlessri.anotsturdymod.library.block.rotation;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import powerlessri.anotsturdymod.varia.math.ExtendedAABB;

/**
 * Internal implementation for {@link BlockRotationHandler}. Represents a block that can be rotated to be facing all 4 horizontal sides.
 */
class HandlerHorizontalRotations extends BlockRotationHandler {

    HandlerHorizontalRotations(ExtendedAABB base) {
        this("facing", base);
    }

    HandlerHorizontalRotations(String propertyName, ExtendedAABB base) {
        this.facing = PropertyDirection.create(propertyName, EBlockRotationType.HORIZONTAL.getGuavaFilter());
        this.base = base;
        // According to EnumFacing#getHorizontalIndex, the order is S-W-N-E
        this.rotatedBoundingBoxes = EBlockRotationType.HORIZONTAL.rotateForIndex(base);
    }


    @Override
    public EBlockRotationType getRotationType() {
        return EBlockRotationType.HORIZONTAL;
    }

    @Override
    public ExtendedAABB getBoundingBoxFor(IBlockState state) {
        EnumFacing facing = state.getValue(this.facing);
        return this.rotatedBoundingBoxes.get(facing.getHorizontalIndex());
    }

}
