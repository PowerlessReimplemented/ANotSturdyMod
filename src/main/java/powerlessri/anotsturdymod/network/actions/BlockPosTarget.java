package powerlessri.anotsturdymod.network.actions;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;

class BlockPosTarget extends Target {

    private int dimension;
    private int x;
    private int y;
    private int z;

    private BlockPos pos;

    public BlockPosTarget(int dimension, BlockPos pos) {
        this(dimension, pos.getX(), pos.getY(), pos.getZ());
        this.pos = pos;
    }

    public BlockPosTarget(int dimension, int x, int y, int z) {
        this.dimension = dimension;
        this.x = x;
        this.y = y;
        this.z = z;
    }


    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(Target.BLOCK_POS_TARGET);
        buf.writeInt(dimension);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public void read(ByteBuf buf) {
        this.dimension = buf.readInt();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }


    public BlockPos getBlockPos() {
        if (this.pos == null) {
            this.pos = new BlockPos(x, y, z);
        }
        return pos;
    }

}
