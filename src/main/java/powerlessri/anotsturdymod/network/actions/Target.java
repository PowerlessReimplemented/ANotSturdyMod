package powerlessri.anotsturdymod.network.actions;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import powerlessri.anotsturdymod.network.actions.registry.TargetMapping;
import powerlessri.anotsturdymod.network.actions.target.BlockPosTarget;
import powerlessri.anotsturdymod.network.actions.target.DimensionalUUIDTarget;
import powerlessri.anotsturdymod.network.actions.target.GenericTarget;
import powerlessri.anotsturdymod.network.actions.target.UUIDTarget;

import java.io.Serializable;
import java.util.UUID;

/**
 * Optional parameter of an action message that indicates the location of receiver.
 * <p>
 * For world objects like tile entities and entities, instead of storing location in custom data, the use of a locational target object is
 * encouraged. For non world objects, the usage of target object is optional.
 * </p>
 */
public interface Target extends Serializable {

    /**
     * Write all target data into the parameter ByteBuf.
     */
    void write(PacketBuffer buf);

    /**
     * Restore from the data stored in the parameter ByteBuf, assuming the read index is in the right place. data.
     */
    void read(PacketBuffer buf);


    TaskExecutor getExecutor();

    default int getTypeID() {
        return TargetMapping.getInstance().getID(this);
    }

}
