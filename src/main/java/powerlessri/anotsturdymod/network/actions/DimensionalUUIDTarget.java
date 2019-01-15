package powerlessri.anotsturdymod.network.actions;

import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

import java.util.UUID;

public class DimensionalUUIDTarget extends Target {

    private int dimension;
    private UUID uuid;

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

}
