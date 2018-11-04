package powerlessri.anotsturdymod.mechanics.remote_enetwork.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.blocks.block.base.TileBlockBase;
import powerlessri.anotsturdymod.mechanics.remote_enetwork.tile.TileENComponentBase;

import java.util.function.Supplier;

public class BlockEnergyAccessPort extends TileBlockBase {

    private final Supplier<TileENComponentBase> tileCreator;
    private final int guiId;


    public BlockEnergyAccessPort(String name, Supplier<TileENComponentBase> tileCreator, int guiId) {
        super(name, Material.ROCK);

        this.tileCreator = tileCreator;
        this.guiId = guiId;

        setHardness(1.5f);
        setResistance(8.0f);
        setHarvestLevel(EHarvestTool.PICKAXE, EHarvestLevel.IRON);
        setCreativeTab(CreativeTabs.MISC);
    }


    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }

        player.openGui(ANotSturdyMod.instance, guiId, world, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return tileCreator.get();
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
