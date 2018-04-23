package powerlessri.anotsturdymod.items;

import net.minecraft.creativetab.CreativeTabs;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.init.ModItems;
import powerlessri.anotsturdymod.utils.IHasModel;

public class ItemManualCreatedItem extends ItemBase implements IHasModel {
	
	public ItemManualCreatedItem(String registry_name, String unlocalized_name, CreativeTabs tab) {
		super(registry_name, unlocalized_name, tab);
		
		ModItems.ITEMS.add(this);
	}
	
	public ItemManualCreatedItem(String common_name, CreativeTabs tab) {
		this(common_name, common_name, tab);
	}
	
	public ItemManualCreatedItem(String registry_name, String unlocalized_name) {
		this(registry_name, unlocalized_name, CreativeTabs.MISC);
	}
	
	public ItemManualCreatedItem(String common_name) {
		this(common_name, common_name);
	}
	
}
