package powerlessri.anotsturdymod.library.utils;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;


public class PosExtractor {

    private PosExtractor() {
    }

    /**
     * Get all blocks on a plane with {@code radius} * 2 + 1 side length, parallel
     * with {@code sideHit}. <br />
     * 
     * e.g. {@code sideHit} = <b>UP/DOWN</b>: BlockPos with same y <br />
     * {@code sideHit} = <b>NORTH/SOUTH</b>: BlockPos with same z <br />
     * {@code sideHit} = <b>EAST/WEST</b>: BlockPos with same x <br />
     */
    public static Iterable<BlockPos> posOnPlane(BlockPos originalPos, EnumFacing faceHit, int radius) {
        switch(faceHit) {
        case UP:
        case DOWN:
            return BlockPos.getAllInBox(originalPos.add(radius, 0, radius), originalPos.add(-radius, 0, -radius));

        case NORTH:
        case SOUTH:
            return BlockPos.getAllInBox(originalPos.add(radius, radius, 0), originalPos.add(-radius, -radius, 0));

        case EAST:
        case WEST:
            return BlockPos.getAllInBox(originalPos.add(0, radius, radius), originalPos.add(0, -radius, -radius));
        }

        return BlockPos.getAllInBox(originalPos, originalPos);
    }

}
