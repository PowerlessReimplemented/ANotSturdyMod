package powerlessri.anotsturdymod.network.actions;

import net.minecraft.network.PacketBuffer;

import java.util.UUID;

class UUIDTarget extends Target {

    private UUID uuid;

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

}
