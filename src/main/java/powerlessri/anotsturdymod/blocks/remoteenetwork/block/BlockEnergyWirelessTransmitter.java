package powerlessri.anotsturdymod.blocks.remoteenetwork.block;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.blocks.remoteenetwork.tile.TileENComponentBase;
import powerlessri.anotsturdymod.blocks.remoteenetwork.tile.TileENWirelessTransmitter;
import powerlessri.anotsturdymod.handlers.init.RegistryBlock;
import powerlessri.anotsturdymod.library.block.rotation.BlockRotationHandler;
import powerlessri.anotsturdymod.varia.math.ExtendedAABB;

import java.util.function.Supplier;

public class BlockEnergyWirelessTransmitter extends BlockEnergyAccessPort {

    /**
     * The block is attached to the opposite side of side the property indicates.
     * <p>
     * Example: When propertyFacing=up, the block is attached to the block on its bottom.
     * </p>
     */
    public static BlockRotationHandler rotationHandler = BlockRotationHandler.all(new ExtendedAABB(0.1875d, 0.0d, 0.1875d, 0.8125d, 0.125d, 0.8125d));
    /**
     * Reference to {@code rotationHandler.getPropertyFacing()}.
     */
    private static PropertyDirection propertyFacing = rotationHandler.getPropertyFacing();

    @RegistryBlock
    public static final BlockEnergyWirelessTransmitter EN_WIRELESS_TRANSMITTER = new BlockEnergyWirelessTransmitter("energy_network_wireless_transmitter", () -> new TileENWirelessTransmitter(0, 320), "energy_wireless_transmitter");


    public BlockEnergyWirelessTransmitter(String name, Supplier<TileENComponentBase> tileSupplier, String guiName) {
        super(name, tileSupplier, guiName);
    }


    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(propertyFacing, facing);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        // Meta: 0~5, for 6 different facings
        // 5 == 0b101
        return this.getDefaultState().withProperty(propertyFacing, EnumFacing.byIndex(meta & 0b111));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(propertyFacing).getIndex();
    }


    @Override
    public ExtendedAABB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return rotationHandler.getBoundingBoxFor(state);
    }


    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, propertyFacing);
    }


    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
        if (face == state.getValue(propertyFacing).getOpposite()) {
            return BlockFaceShape.CENTER;
        }
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

}
