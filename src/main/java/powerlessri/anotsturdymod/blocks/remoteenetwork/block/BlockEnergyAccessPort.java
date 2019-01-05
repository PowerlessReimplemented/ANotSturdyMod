package powerlessri.anotsturdymod.blocks.remoteenetwork.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.blocks.remoteenetwork.tile.TileENAccessPortInput;
import powerlessri.anotsturdymod.blocks.remoteenetwork.tile.TileENAccessPortOutput;
import powerlessri.anotsturdymod.blocks.remoteenetwork.tile.TileENWirelessTransmitter;
import powerlessri.anotsturdymod.config.ENetworkConfig;
import powerlessri.anotsturdymod.handlers.init.RegistryBlock;
import powerlessri.anotsturdymod.library.block.base.TileBlockBase;
import powerlessri.anotsturdymod.blocks.remoteenetwork.tile.TileENComponentBase;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class BlockEnergyAccessPort extends TileBlockBase {

    @RegistryBlock
    public static final BlockEnergyAccessPort EN_INPUT_PORT = new BlockEnergyAccessPort("energy_network_input_port", () -> new TileENAccessPortInput(0, ENetworkConfig.accessPortReceiveLimit), "energy_io_access");
    @RegistryBlock
    public static final BlockEnergyAccessPort EN_OUTPUT_PORT = new BlockEnergyAccessPort("energy_network_output_port", () -> new TileENAccessPortOutput(0, ENetworkConfig.accessPortExtractLimit), "energy_io_access");


    public final Supplier<TileENComponentBase> tileSupplier;
    public final String guiName;
    public final int guiID;

    public BlockEnergyAccessPort(String name, Supplier<TileENComponentBase> tileSupplier, String guiName) {
        super(name, Material.ROCK);

        this.tileSupplier = tileSupplier;
        this.guiName = guiName;
        this.guiID = ANotSturdyMod.gui.keys.get(guiName);

        this.setHardness(1.5f);
        this.setResistance(8.0f);
        this.setHarvestLevel(EHarvestTool.PICKAXE, EHarvestLevel.IRON);
        this.setCreativeTab(CreativeTabs.MISC);
    }


    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }

        player.openGui(ANotSturdyMod.instance, guiID, world, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }


    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return this.tileSupplier.get();
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

}
