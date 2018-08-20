package powerlessri.anotsturdymod.items.basic;

import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.init.ModItems;
import powerlessri.anotsturdymod.items.base.ItemBase;

public class ItemBasicItem extends ItemBase {

	public ItemBasicItem(String registryName, String unlocalizedName) {
		super(registryName, unlocalizedName);
		ModItems.ITEMS.add(this);
	}
	public ItemBasicItem(String name) {
		this(name, name);
	}
	
	
	public void registerModel() {
		ANotSturdyMod.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
}
