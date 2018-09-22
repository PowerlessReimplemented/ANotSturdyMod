package powerlessri.anotsturdymod.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.blocks.base.TileBlockBase;
import powerlessri.anotsturdymod.tile.TileCobbleGenerator;

// TODO different cobblestone generators
public class BlockInfiniteCobbleGenerator extends TileBlockBase {

    public BlockInfiniteCobbleGenerator(String name) {
        super(name, Material.ROCK);

        this.setHardness(2.0f);
        this.setResistance(10.0f);
        this.setHarvestLevel(EHarvestTool.PICKAXE, EHarvestLevel.STONE);

        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }



    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileCobbleGenerator();
    }
    
    
    
    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileCobbleGenerator.class;
    }

}
