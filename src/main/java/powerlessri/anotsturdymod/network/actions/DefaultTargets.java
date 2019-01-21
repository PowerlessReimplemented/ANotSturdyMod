package powerlessri.anotsturdymod.network.actions;

import net.minecraft.util.math.BlockPos;
import powerlessri.anotsturdymod.network.actions.registry.TargetMapping;
import powerlessri.anotsturdymod.network.actions.target.BlockPosTarget;
import powerlessri.anotsturdymod.network.actions.target.DimensionalUUIDTarget;
import powerlessri.anotsturdymod.network.actions.target.GenericTarget;
import powerlessri.anotsturdymod.network.actions.target.UUIDTarget;

import java.util.UUID;

public final class DefaultTargets {

    public static void init() {
        TargetMapping mapping = TargetMapping.getInstance();
        mapping.register(GenericTarget.class, GenericTarget::new);
        mapping.register(BlockPosTarget.class, BlockPosTarget::new);
        mapping.register(UUIDTarget.class, () -> {
            throw new IllegalStateException("Trying to initialize an abstract target class.");
        });
        mapping.register(DimensionalUUIDTarget.class, DimensionalUUIDTarget::new);
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
