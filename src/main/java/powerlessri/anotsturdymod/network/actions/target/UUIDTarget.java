package powerlessri.anotsturdymod.network.actions.target;

import net.minecraft.network.PacketBuffer;
import powerlessri.anotsturdymod.network.actions.Target;
import powerlessri.anotsturdymod.network.actions.TaskExecutor;

import java.util.UUID;

public abstract class UUIDTarget implements Target {

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
    public TaskExecutor getExecutor() {
        return (target, attachment) -> true;
    }

}
