package powerlessri.anotsturdymod.blocks.transfer.redirect;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.handlers.init.RegistryBlock;
import powerlessri.anotsturdymod.library.block.base.TileBlockBase;
import powerlessri.anotsturdymod.varia.general.Utils;
import powerlessri.anotsturdymod.varia.math.ExtendedAABB;

import javax.annotation.Nullable;

public class BlockRedirector extends TileBlockBase {

    static final ExtendedAABB AABB = new ExtendedAABB(0.3d, 0.3d, 0.3d, 0.7d, 0.7d, 0.7d);
    static final PropertyDirection FACING = PropertyDirection.create("facing");


    @RegistryBlock
    public static final BlockRedirector CAPABILITY_REDIRECTOR = new BlockRedirector("capability_redirector");

    public BlockRedirector(String name) {
        super(name, Material.ROCK);
        this.setHardness(1.5f);
        this.setResistance(5.0f);
        this.setCreativeTab(CreativeTabs.MISC);
    }


    @Override
    public void breakAndDrop(World world, BlockPos pos, IBlockState state) {
        world.setBlockToAir(pos);
        spawnAsEntity(world, pos, new ItemStack(CAPABILITY_REDIRECTOR));
    }


    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileRedirector tile = (TileRedirector) world.getTileEntity(pos);
            Utils.getLogger().info(tile.getTargetPos());
        }
        return true;
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, facing.getOpposite());
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta & 0b111));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileRedirector();
    }

    @Override
    public ExtendedAABB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean hasItemForm() {
        return true;
    }

}
