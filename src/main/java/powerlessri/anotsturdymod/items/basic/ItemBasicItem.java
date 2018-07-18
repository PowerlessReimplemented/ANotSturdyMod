package powerlessri.anotsturdymod.items.basic;

import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.items.base.ItemBase;

public class ItemBasicItem extends ItemBase {

	public ItemBasicItem(String registry_name, String unlocalized_name) {
		super(registry_name, unlocalized_name);
	}
	public ItemBasicItem(String name) {
		this(name, name);
	}
	
	
	public void registerModel() {
		ANotSturdyMod.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
}
