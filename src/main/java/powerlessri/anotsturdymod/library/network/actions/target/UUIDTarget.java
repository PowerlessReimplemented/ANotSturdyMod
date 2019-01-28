package powerlessri.anotsturdymod.library.network.actions.target;

import net.minecraft.network.PacketBuffer;
import powerlessri.anotsturdymod.library.network.actions.ITarget;
import powerlessri.anotsturdymod.library.network.actions.ITaskExecutor;

import java.util.UUID;

public abstract class UUIDTarget implements ITarget {

    private UUID uuid;

    public UUIDTarget() {
    }

    public UUIDTarget(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeUniqueId(uuid);
    }

    @Override
    public void read(PacketBuffer buf) {
        this.uuid = buf.readUniqueId();
    }

    @Override
    public ITaskExecutor getExecutor() {
        return (target, attachment) -> true;
    }

}
