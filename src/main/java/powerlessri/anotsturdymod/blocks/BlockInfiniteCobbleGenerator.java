package powerlessri.anotsturdymod.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.blocks.base.TileBlockBase;
import powerlessri.anotsturdymod.blocks.tile.TileCobbleGenerator;

// TODO different cobblestone generators
public class BlockInfiniteCobbleGenerator extends TileBlockBase {

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
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileCobbleGenerator.class;
    }


    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

}
