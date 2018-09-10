package powerlessri.anotsturdymod.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.blocks.basic.BlockBasicBlock;
import powerlessri.anotsturdymod.blocks.tile.TileCobbleGenerator;
import powerlessri.anotsturdymod.library.enums.EHarvestLevel;
import powerlessri.anotsturdymod.library.enums.EHarvestTool;

public class BlockInfiniteCobbleGenerator extends BlockBasicBlock {

    public BlockInfiniteCobbleGenerator(String name) {
        super(name, Material.ROCK);
        
        this.setHardness(2.0f);
        this.setResistance(10.0f);
        this.setHarvestLevel(EHarvestTool.PICKAXE, EHarvestLevel.STONE);
        
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.hasTileEntity = true;
    }
    
    
    
    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileCobbleGenerator();
    }

}
