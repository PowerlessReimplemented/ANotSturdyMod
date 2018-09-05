package powerlessri.anotsturdymod.blocks;

import net.minecraft.block.material.Material;
import powerlessri.anotsturdymod.blocks.basic.BlockBasicBlock;
import powerlessri.anotsturdymod.library.enums.EHarvestLevel;
import powerlessri.anotsturdymod.library.enums.EHarvestTool;


//TODO complete texture/model
public class BlockMetalBlock extends BlockBasicBlock {

    public BlockMetalBlock(String name, Material material) {
        super(name, material);

        this.setHardness(4.5f);
        this.setResistance(15.0f);
        this.setHarvestLevel(EHarvestTool.PICKAXE, EHarvestLevel.IRON);
    }

}