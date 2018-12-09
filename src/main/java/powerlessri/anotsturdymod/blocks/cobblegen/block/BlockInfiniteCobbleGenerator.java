package powerlessri.anotsturdymod.blocks.cobblegen.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import powerlessri.anotsturdymod.handlers.init.RegistryBlock;
import powerlessri.anotsturdymod.library.block.base.TileBlockBase;
import powerlessri.anotsturdymod.blocks.cobblegen.tile.TileCobbleGenerator;

// TODO different cobblestone generators
public class BlockInfiniteCobbleGenerator extends TileBlockBase {

    @RegistryBlock
    public static final BlockInfiniteCobbleGenerator INFINTE_COBBLE_GENERATOR = new BlockInfiniteCobbleGenerator("infinite_cobble_generator");

    
    public BlockInfiniteCobbleGenerator(String name) {
        super(name, Material.ROCK);

        setHardness(2.0f);
        setResistance(10.0f);
        setHarvestLevel(EHarvestTool.PICKAXE, EHarvestLevel.STONE);
        setCreativeTab(CreativeTabs.DECORATIONS);
    }


    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileCobbleGenerator();
    }


    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

}
