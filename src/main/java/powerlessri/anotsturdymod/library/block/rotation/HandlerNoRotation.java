package powerlessri.anotsturdymod.library.block.rotation;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockState;
import powerlessri.anotsturdymod.varia.math.ExtendedAABB;

/**
 * Internal implementation for {@link BlockRotationHandler}. Represents a block with locked one state.
 */
class HandlerNoRotation extends BlockRotationHandler {

    protected HandlerNoRotation(ExtendedAABB boundingBox) {
        this("facing", boundingBox);
    }

    protected HandlerNoRotation(String propertyName, ExtendedAABB base) {
        // There is no property for the block
        this.facing = null;
        this.base = base;
        this.rotatedBoundingBoxes = ImmutableList.of(base);
    }

    @Override
    public EBlockRotationType getRotationType() {
        return EBlockRotationType.NONE;
    }

    @Override
    public ExtendedAABB getBoundingBoxFor(BlockState state) {
        return this.base;
    }

}
