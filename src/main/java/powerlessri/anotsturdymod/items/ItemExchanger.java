package powerlessri.anotsturdymod.items;

import net.minecraft.creativetab.CreativeTabs;
import powerlessri.anotsturdymod.items.basic.ItemBasicItem;

public class ItemExchanger extends ItemBasicItem {
	
	public ItemExchanger(String name) {
		super(name, name);
		
		this.setCreativeTab(CreativeTabs.TOOLS);
		this.setMaxStackSize(1);
	}
	
}
