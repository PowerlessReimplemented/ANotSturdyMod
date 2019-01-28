package powerlessri.anotsturdymod.library.network.actions;

import net.minecraft.network.PacketBuffer;
import powerlessri.anotsturdymod.library.network.actions.registry.TargetMapping;

import java.io.Serializable;

/**
 * Optional parameter of an action message that indicates the location of receiver.
 * <p>
 * For world objects like tile entities and entities, instead of storing location in custom data, the use of a locational target object is
 * encouraged. For non world objects, the usage of target object is optional.
 * </p>
 */
public interface ITarget extends Serializable {

    /**
     * Write all target data into the parameter ByteBuf.
     */
    void write(PacketBuffer buf);

    /**
     * Restore from the data stored in the parameter ByteBuf, assuming the read index is in the right place. data.
     */
    void read(PacketBuffer buf);


    ITaskExecutor getExecutor();

    default int getTypeID() {
        return TargetMapping.getInstance().getID(this);
    }

}
