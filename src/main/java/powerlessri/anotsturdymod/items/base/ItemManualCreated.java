package powerlessri.anotsturdymod.items.base;

import org.apache.http.MethodNotSupportedException;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.init.ModItems;
import powerlessri.anotsturdymod.utils.handlers.interfaces.IHasModel;
import powerlessri.anotsturdymod.utils.handlers.interfaces.IHasNoVariants;

/**
 * The main difference of this class is it automatically
 * add the instance to ModItems.ITEMS once you instance it.
 * 
 * @author root-user
 *
 */
@Deprecated
public class ItemManualCreated extends ItemBase implements IHasNoVariants {
	
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
	
	
	
	@Override
	public void registerModel() {
		ANotSturdyMod.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
}
