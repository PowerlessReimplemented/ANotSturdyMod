package powerlessri.anotsturdymod.network.actions;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

/**
 * Optional parameter of an action message that indicates the location of receiver.
 * <p>
 * For world objects like tile entities and entities, instead of storing location in custom data, the use of a locational target object is
 * encouraged. For non world objects, the usage of target object is optional.
 * </p>
 */
public abstract class Target {

    // Type ID's for target implementations
    // Used as header of target object
    static final byte GENERIC_TARGET = 0;
    static final byte BLOCK_POS_TARGET = 1;
    static final byte UUID_TARGET = 2;
    static final byte DIMENSINAL_UUID_TARGET = 3;


    public static Target create(World dimension, BlockPos pos) {
        return create(dimension.provider.getDimension(), pos);
    }

    public static Target create(int dimension, BlockPos pos) {
        return new BlockPosTarget(dimension, pos);
    }

    public static Target create(World dimension, int x, int y, int z) {
        return create(dimension.provider.getDimension(), x, y, z);
    }

    public static Target create(int dimension, int x, int y, int z) {
        return new BlockPosTarget(dimension, x, y, z);
    }

    public static Target create(UUID uuid) {
        return new UUIDTarget(uuid);
    }


    /**
     * Write all target data into the parameter ByteBuf. This also includes type data, which should be at front of everything else.
     */
    public abstract void write(ByteBuf buf);

    /**
     * Restore from the data stored in the parameter ByteBuf, assuming the read index is in the right place. This does not include type
     * data.
     */
    public abstract void read(ByteBuf buf);

}
