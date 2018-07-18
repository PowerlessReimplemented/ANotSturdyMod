package powerlessri.anotsturdymod.blocks.basic;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.blocks.base.BlockBase;
import powerlessri.anotsturdymod.utils.handlers.enums.EHarvestLevel;
import powerlessri.anotsturdymod.utils.handlers.enums.EHarvestTool;

public class BlockBasicBlock extends BlockBase {

	public BlockBasicBlock(String name, Material material) {
		super(name, name, material);
	}

	public void registerModel() {
		ANotSturdyMod.proxy.registerBlockRenderer(this, 0, "");
		ANotSturdyMod.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
	
	
	
	public void setHarvestLevel(EHarvestTool tool, EHarvestLevel level) {
		this.setHarvestLevel(tool.getName(), level.numerical());
	}
	
}
