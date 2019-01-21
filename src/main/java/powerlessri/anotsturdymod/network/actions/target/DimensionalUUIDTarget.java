package powerlessri.anotsturdymod.network.actions.target;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import powerlessri.anotsturdymod.network.actions.EntityExecutor;
import powerlessri.anotsturdymod.network.actions.Target;
import powerlessri.anotsturdymod.network.actions.TaskExecutor;

import java.util.UUID;

public class DimensionalUUIDTarget implements Target {

    private int dimension;
    private UUID uuid;

    public DimensionalUUIDTarget() {
    }

    public DimensionalUUIDTarget(World world, UUID uuid) {
        this(world.provider.getDimension(), uuid);
    }

    public DimensionalUUIDTarget(int dimension, UUID uuid) {
        this.uuid = uuid;
    }


    @Override
    public void write(PacketBuffer buf) {
        buf.writeInt(dimension);
        buf.writeUniqueId(uuid);
    }

    @Override
    public void read(PacketBuffer buf) {
        this.dimension = buf.readInt();
        this.uuid = buf.readUniqueId();
    }

    @Override
    public TaskExecutor getExecutor() {
        return (target, attachment) -> {
            WorldServer world = DimensionManager.getWorld(this.dimension);
            Entity entity = world.getEntityFromUuid(this.uuid);

            if (entity instanceof EntityExecutor) {
                EntityExecutor executor = (EntityExecutor) entity;
                executor.accept(attachment);
                return true;
            }
            throw new IllegalArgumentException("No such entity " + uuid.toString() + " exist in dimension " + dimension);
        };
    }

    @Override
    public byte getTypeID() {
        return 3;
    }

}
