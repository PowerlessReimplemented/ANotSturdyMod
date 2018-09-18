package powerlessri.anotsturdymod.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import powerlessri.anotsturdymod.blocks.base.TileBlockBase;
import powerlessri.anotsturdymod.blocks.base.SimpleBlockBase.EHarvestLevel;
import powerlessri.anotsturdymod.blocks.base.SimpleBlockBase.EHarvestTool;

public class BlockRemtoeEnergyCell extends TileBlockBase {

    public BlockRemtoeEnergyCell(String name) {
        super(name, Material.IRON);
        
        this.setHardness(2.0f);
        this.setResistance(15.0f);
        this.setHarvestLevel(EHarvestTool.PICKAXE, EHarvestLevel.IRON);

        // TODO add own creative tab
        this.setCreativeTab(CreativeTabs.MISC);
    }

    
    
    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return null;
    }

}
