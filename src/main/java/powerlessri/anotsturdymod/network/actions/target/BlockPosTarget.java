package powerlessri.anotsturdymod.network.actions.target;

import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import powerlessri.anotsturdymod.network.actions.EntityExecutor;
import powerlessri.anotsturdymod.network.actions.Target;
import powerlessri.anotsturdymod.network.actions.TaskExecutor;

public class BlockPosTarget implements Target {

    private int dimension;
    private int x;
    private int y;
    private int z;

    private transient BlockPos pos;

    public BlockPosTarget() {
    }

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
    public void write(PacketBuffer buf) {
        buf.writeInt(dimension);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public void read(PacketBuffer buf) {
        this.dimension = buf.readInt();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    @Override
    public TaskExecutor getExecutor() {
        return (target, attachment) -> {
            World world = DimensionManager.getWorld(this.dimension);
            BlockPos pos = this.getBlockPos();
            TileEntity tile = world.getTileEntity(pos);

            if (tile instanceof EntityExecutor) {
                EntityExecutor executor = (EntityExecutor) tile;
                executor.accept(attachment);
                return true;
            }
            throw new IllegalArgumentException("No tile entity that is an EntityExecutor exist at " + pos.toString() + " in dimension " + dimension);
        };
    }


    public BlockPos getBlockPos() {
        if (this.pos == null) {
            this.pos = new BlockPos(x, y, z);
        }
        return pos;
    }

}
