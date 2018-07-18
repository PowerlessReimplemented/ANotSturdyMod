package powerlessri.anotsturdymod.blocks;

import net.minecraft.block.material.Material;
import powerlessri.anotsturdymod.blocks.basic.BlockBasicBlock;
import powerlessri.anotsturdymod.utils.handlers.enums.EHarvestLevel;
import powerlessri.anotsturdymod.utils.handlers.enums.EHarvestTool;

//TODO complete texture/model
public class BlockMetalBlocks extends BlockBasicBlock {
	
	public BlockMetalBlocks(String name, Material material) {
		super(name, material);
		
		this.setHardness(4.5f);
		this.setResistance(15.0f);
		this.setHarvestLevel(EHarvestTool.PICKAXE, EHarvestLevel.IRON);
	}
	
}
