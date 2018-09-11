package powerlessri.anotsturdymod.blocks;

import net.minecraft.block.material.Material;
import powerlessri.anotsturdymod.blocks.base.BasicBlockBase;


//TODO complete texture/model
public class BlockMetalBlock extends BasicBlockBase {

    public BlockMetalBlock(String name, Material material) {
        super(name, material);

        this.setHardness(4.5f);
        this.setResistance(15.0f);
        this.setHarvestLevel(EHarvestTool.PICKAXE, EHarvestLevel.IRON);
    }

}
