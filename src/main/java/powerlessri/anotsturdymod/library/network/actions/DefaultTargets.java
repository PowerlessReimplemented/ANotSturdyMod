package powerlessri.anotsturdymod.library.network.actions;

import net.minecraft.util.math.BlockPos;
import powerlessri.anotsturdymod.library.network.actions.registry.TargetMapping;
import powerlessri.anotsturdymod.library.network.actions.target.BlockPosTarget;
import powerlessri.anotsturdymod.library.network.actions.target.DimensionalUUIDTarget;
import powerlessri.anotsturdymod.library.network.actions.target.GenericTarget;
import powerlessri.anotsturdymod.library.network.actions.target.UUIDTarget;

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


    public static ITarget create(int dimension, BlockPos pos) {
        return new BlockPosTarget(dimension, pos);
    }

    public static ITarget create(int dimension, int x, int y, int z) {
        return new BlockPosTarget(dimension, x, y, z);
    }

    public static ITarget create(int dimension, UUID uuid) {
        return new DimensionalUUIDTarget(dimension, uuid);
    }

}
