package powerlessri.anotsturdymod.library.block.base;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powerlessri.anotsturdymod.varia.Reference;
import powerlessri.anotsturdymod.varia.math.ExtendedAABB;

public abstract class SimpleBlockBase extends Block {

    private ItemBlock itemBlock;

    public SimpleBlockBase(String name, Material material) {
        super(material);

        this.setRegistryName(new ResourceLocation(Reference.MODID, name));
        this.setTranslationKey(this.getRegistryName().toString());
        this.initializeItemBlock();
    }


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
    /**
     * @deprecated Use {@link #EMPTY_AABB} instead
     */
    @Deprecated
    public static ExtendedAABB NULL_AABB = null;

    public ExtendedAABB getBaseBoundingBox() {
        return FULL_BLOCK_AABB;
    }

    @SuppressWarnings("deprecation") // Implementing/overriding is fine
    @Override
    public ExtendedAABB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return this.getBaseBoundingBox();
    }


    public void setHarvestLevel(EHarvestTool tool, EHarvestLevel level) {
        this.setHarvestLevel(tool.getName(), level.getInt());
    }

    public void setMaxStackSize(int size) {
        this.itemBlock.setMaxStackSize(size);
    }


    public abstract boolean hasItemForm();

    private void initializeItemBlock() {
        if (hasItemForm()) {
            this.itemBlock = new ItemBlock(this);
            this.itemBlock.setRegistryName(this.getRegistryName());
            this.itemBlock.setTranslationKey(this.getRegistryName().toString());
        } else {
            this.itemBlock = null;
        }
    }

    public ItemBlock getItemBlock() {
        return this.itemBlock;
    }


    public void breakAndDrop(World world, BlockPos pos, IBlockState state) {
        NonNullList<ItemStack> drops = NonNullList.create();
        this.getDrops(drops, world, pos, state, 0);
        for (ItemStack drop : drops) {
            spawnAsEntity(world, pos, drop);
        }

        world.setBlockToAir(pos);
    }


    /**
     * Controls block shadow for rendering purposes.
     */
    @SuppressWarnings("deprecation") // Implementing/overriding is fine
    @Override
    public boolean isFullCube(IBlockState state) {
        return this.isOpaqueCube(state);
    }

    @SuppressWarnings("deprecation") // Implementing/overriding is fine
    @Override
    public boolean canEntitySpawn(IBlockState state, Entity entity) {
        return this.isOpaqueCube(state);
    }


    /**
     * <p>
     * In most cases this method does not do be override, just override {@link #getBlockFaceShape(IBlockAccess, IBlockState, BlockPos,
     * EnumFacing)}.
     * </p>
     */
    @SuppressWarnings("deprecation") // Backwards compatibility
    @Override
    public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        if (this.isNormalCube(state, world, pos)) {
            return true;
        }
        return this.getBlockFaceShape(world, state, pos, side) == BlockFaceShape.SOLID;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
        // TODO custom face shape conditions
        return super.getBlockFaceShape(world, state, pos, face);
    }


    @SideOnly(Side.CLIENT)
    public void registerModel() {
        if (hasItemForm()) {
            ModelLoader.setCustomModelResourceLocation(this.getItemBlock(), 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
        }
    }

}
