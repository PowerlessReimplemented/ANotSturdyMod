package powerlessri.anotsturdymod.network.actions;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

class UUIDTarget extends Target {

    private UUID uuid;

    public UUIDTarget(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
    }

    @Override
    public void read(ByteBuf buf) {
        long mostBits = buf.readLong();
        long leastBits = buf.readLong();
        this.uuid = new UUID(mostBits, leastBits);
    }

}
