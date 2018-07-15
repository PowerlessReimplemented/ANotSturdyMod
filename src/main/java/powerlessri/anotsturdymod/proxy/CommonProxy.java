package powerlessri.anotsturdymod.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import powerlessri.anotsturdymod.utils.handlers.interfaces.IHasNoVariants;
import powerlessri.anotsturdymod.utils.handlers.interfaces.IHasVariants;

public class CommonProxy {
	
	public void registerItemRenderer(Item item, int meta, String id) {
	}
	
	public void registerItemRenderer(IHasNoVariants item, String id) {
	}
	
	public void registerItemRenderer(IHasVariants item, String id) {
	}
	
	public void registerBlockRenderer(Block block, int meta, String id) {
	}
	
}
