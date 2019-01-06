package powerlessri.anotsturdymod.library.block.rotation;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import powerlessri.anotsturdymod.varia.math.ExtendedAABB;

/**
 * Internal implementation for {@link BlockRotationHandler}. Represents a block that can be rotated to be facing all 6 sides.
 */
class HandlerAllRotations extends BlockRotationHandler {

    HandlerAllRotations(ExtendedAABB base) {
        this("facing", base);
    }

    HandlerAllRotations(String propertyName, ExtendedAABB base) {
        this.facing = PropertyDirection.create(propertyName, EBlockRotationType.ALL.getGuavaFilter());
        this.base = base;
        // According to EnumFacing#getIndex, the order is D-U-N-S-W-E
        this.rotatedBoundingBoxes = EBlockRotationType.ALL.rotateForIndex(base);
    }


    @Override
    public EBlockRotationType getRotationType() {
        return EBlockRotationType.ALL;
    }

    @Override
    public ExtendedAABB getBoundingBoxFor(IBlockState state) {
        EnumFacing facing = state.getValue(this.facing);
        return this.rotatedBoundingBoxes.get(facing.getIndex());
    }

}
