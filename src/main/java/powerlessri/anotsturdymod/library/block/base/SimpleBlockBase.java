package powerlessri.anotsturdymod.library.block.base;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.varia.math.ExtendedAABB;

public abstract class SimpleBlockBase extends Block {

    // TODO add support for auto setup for block rotations
    // Trash Mojang requests BlockStateContainer in the constructor... Might have to do a ClassTransformer or something on it
    // ^^^^ THAT IS A BAD IDEA
//    private List<IProperty<?>> includedProperties = new ArrayList<>();
//
//    @Override
//    protected BlockStateContainer createBlockState() {
//        return new BlockStateContainer(this, includedProperties.toArray(new IProperty<?>[0]));
//    }
//
//
//    private BlockRotationHandler rotationHandler;
//
//    @Nullable
//    public BlockRotationHandler getRotationHandler() {
//        return this.rotationHandler;
//    }
//
//    public void setBlockRotation(EBlockRotationType type) {
//        if (this.rotationHandler != null) {
//            throw new IllegalStateException("Already set block rotation to " + this.rotationHandler);
//        }
//
//        switch (type) {
//            case NONE:
//                break;
//            case ALL:
//                this.rotationHandler = BlockRotationHandler.all(this.getBaseBoundingBox());
//            case HORIZONTAL:
//                this.rotationHandler = BlockRotationHandler.all(this.getBaseBoundingBox());
//            case VERTICAL:
//                throw new UnsupportedOperationException();
//        }
//        this.includedProperties.add(rotationHandler.getPropertyFacing());
//    }

    public static ExtendedAABB FULL_BLOCK_AABB = ExtendedAABB.from(Block.FULL_BLOCK_AABB);
    public static ExtendedAABB EMPTY_AABB = new ExtendedAABB(0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d);


    public SimpleBlockBase(Properties properties) {
        super(properties);
    }

    public ExtendedAABB getBaseBoundingBox() {
        return FULL_BLOCK_AABB;
    }

    @SuppressWarnings("deprecation") // Implementing/overriding is fine
    @Override
    public ExtendedAABB getBoundingBox(BlockState state, IWorldReader world, BlockPos pos) {
        return this.getBaseBoundingBox();
    }

}
