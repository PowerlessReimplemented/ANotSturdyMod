package powerlessri.anotsturdymod.network.actions;

import net.minecraft.util.math.BlockPos;
import powerlessri.anotsturdymod.network.actions.target.BlockPosTarget;
import powerlessri.anotsturdymod.network.actions.target.DimensionalUUIDTarget;
import powerlessri.anotsturdymod.network.actions.target.GenericTarget;

import java.util.UUID;

public final class DefaultTargets {

    // Type ID's for target implementations
    // Used as header of target object
    static final byte GENERIC_TARGET = 0;
    static final byte BLOCK_POS_TARGET = 1;
    static final byte UUID_TARGET = 2;
    static final byte DIMENSINAL_UUID_TARGET = 3;

    static Target createRawFromType(int type) {
        switch (type) {
            case 0:
                return new GenericTarget();
            case 1:
                return new BlockPosTarget();
            case 3:
                return new DimensionalUUIDTarget();
            default:
                throw new IllegalArgumentException("Unknown target type");
        }
    }

    public static Target create(int dimension, BlockPos pos) {
        return new BlockPosTarget(dimension, pos);
    }

    public static Target create(int dimension, int x, int y, int z) {
        return new BlockPosTarget(dimension, x, y, z);
    }

    public static Target create(int dimension, UUID uuid) {
        return new DimensionalUUIDTarget(dimension, uuid);
    }

}
