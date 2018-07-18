package powerlessri.anotsturdymod.items.base;

import net.minecraft.creativetab.CreativeTabs;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.init.ModItems;

/**
 * The main difference of this class is it automatically
 * add the instance to ModItems.ITEMS once you instance it.
 * 
 * @author root-user
 *
 */
@Deprecated
public class ItemManualCreated extends ItemBase {
	
	public ItemManualCreated(String registry_name, String unlocalized_name, CreativeTabs tab) {
		super(registry_name, unlocalized_name);
		
		this.setCreativeTab(tab);
		
		ModItems.ITEMS.add(this);
	}
	public ItemManualCreated(String common_name, CreativeTabs tab) {
		this(common_name, common_name, tab);
	}
	public ItemManualCreated(String registry_name, String unlocalized_name) {
		this(registry_name, unlocalized_name, CreativeTabs.MISC);
	}
	public ItemManualCreated(String common_name) {
		this(common_name, common_name);
	}
	
	
	
	public void registerModel() {
		ANotSturdyMod.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
}
