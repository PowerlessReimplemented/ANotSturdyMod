package powerlessri.anotsturdymod.items.base;

import net.minecraft.item.ItemStack;

public interface ITagBasedItem {
	
	void buildDefaultTag(ItemStack stack);
	void updateItemTag(ItemStack stack);
	
}
