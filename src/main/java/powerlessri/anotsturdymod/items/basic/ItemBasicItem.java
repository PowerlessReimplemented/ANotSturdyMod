package powerlessri.anotsturdymod.items.basic;

import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.items.base.ItemBase;
import powerlessri.anotsturdymod.utils.handlers.interfaces.IHasNoVariants;

public class ItemBasicItem extends ItemBase implements IHasNoVariants {

	public ItemBasicItem(String registry_name, String unlocalized_name) {
		super(registry_name, unlocalized_name);
	}
	
	@Override
	public void registerModel() {
		ANotSturdyMod.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
}
