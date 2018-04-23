package powerlessri.anotsturdymod.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.items.ItemVariantedItem;
import powerlessri.anotsturdymod.utils.IHasNoVariants;
import powerlessri.anotsturdymod.utils.IHasVariants;

public class CommonProxy {
	
	public void registerItemRenderer(Item item, int meta, String id) {
	}
	
	public void registerItemRenderer(IHasVariants item, String id) {
	}
	
	public void registerItemRenderere(IHasNoVariants item, String id) {
	}
	
	public void registerBlockRenderer(Block block, int meta, String id) {
	}
	
}
