package powerlessri.anotsturdymod.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import powerlessri.anotsturdymod.blocks.BlockMetalBlock;

public class ModBlocks {
	
	private ModBlocks() {
	}
	
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	
	public static final BlockMetalBlock copperBlock = new BlockMetalBlock("copper_block", Material.IRON);
	
}
