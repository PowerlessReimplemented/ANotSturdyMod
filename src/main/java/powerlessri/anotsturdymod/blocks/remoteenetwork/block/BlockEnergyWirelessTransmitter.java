package powerlessri.anotsturdymod.blocks.remoteenetwork.block;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.blocks.remoteenetwork.tile.TileENComponentBase;
import powerlessri.anotsturdymod.blocks.remoteenetwork.tile.TileENWirelessTransmitter;
import powerlessri.anotsturdymod.handlers.init.RegistryBlock;
import powerlessri.anotsturdymod.varia.math.ExtendedAABB;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.function.Supplier;

public class BlockEnergyWirelessTransmitter extends BlockEnergyAccessPort {

    @RegistryBlock
    public static final BlockEnergyWirelessTransmitter EN_WIRELESS_TRANSMITTER = new BlockEnergyWirelessTransmitter("energy_network_wireless_transmitter", () -> new TileENWirelessTransmitter(0, 320), "energy_wireless_transmitter");

    /**
     * {facing=up}
     */
    public static final ExtendedAABB BASE_AABB = new ExtendedAABB(0.1875d, 0.0d, 0.1875d, 0.8125d, 0.125d, 0.8125d);
    public static final ExtendedAABB[] ROTATED_AABB = new ExtendedAABB[16];

    /**
     * The block is attached to the opposite facing.
     * <p>
     * When facing=up, the block is attached on its down side.
     * </p>
     */
    public static PropertyDirection FACING = PropertyDirection.create("facing");

    static {
        {
            Arrays.fill(ROTATED_AABB, BASE_AABB);

            ExtendedAABB up = BASE_AABB;
            ExtendedAABB down = up.rotate(Axis.X, 180);
            ExtendedAABB north = up.rotate(Axis.X, -90);
            ExtendedAABB south = up.rotate(Axis.X, 90);
            ExtendedAABB east = up.rotate(Axis.Z, -90);
            ExtendedAABB west = up.rotate(Axis.Z,90);

            ROTATED_AABB[0] = down;
            ROTATED_AABB[1] = up;
            ROTATED_AABB[2] = north;
            ROTATED_AABB[3] = south;
            ROTATED_AABB[4] = west;
            ROTATED_AABB[5] = east;
        }
    }


    public BlockEnergyWirelessTransmitter(String name, Supplier<TileENComponentBase> tileSupplier, String guiName) {
        super(name, tileSupplier, guiName);
    }


    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        // Meta: 0~5, for 6 different facings
        // 5 == 0b101
        return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta & 0b111));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return ROTATED_AABB[this.getMetaFromState(state)];
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return this.getCollisionBoundingBox(state, world, pos);
    }


    @Override
    protected BlockStateContainer createBlockState() {
        FACING = PropertyDirection.create("facing");
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

}
