package powerlessri.anotsturdymod.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powerlessri.anotsturdymod.handlers.init.RegistryBlock;
import powerlessri.anotsturdymod.library.block.base.BlockBase;

import javax.annotation.Nullable;
import java.util.Random;

// TODO light cubes with color
public class BlockLightCube extends BlockBase {

    @RegistryBlock
    public static final BlockLightCube LIGHT_CUBE = new BlockLightCube("light_cube");
    
    public static final AxisAlignedBB LIGHT_CUBE_AABB = new AxisAlignedBB(0.3, 0.3, 0.3, 0.7, 0.7, 0.7);

    
    public BlockLightCube(String name) {
        super(name, Material.CLOTH);
    }


    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return LIGHT_CUBE_AABB;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public boolean isReplaceable(IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean causesSuffocation(IBlockState state) {
        return false;
    }

    @Override
    public int getLightValue(IBlockState state) {
        return 15;
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager particleManager) {
        if (world.getBlockState(pos).getBlock() == this) {
            particleManager.spawnEffectParticle(
                    EnumParticleTypes.REDSTONE.getParticleID(),
                    pos.getX() + 0.5D + world.rand.nextGaussian() / 8,
                    pos.getY() + 0.5D,
                    pos.getZ() + 0.5D + world.rand.nextGaussian() / 8,
                    0, 0, 0);
        }

        return true;
    }

}
