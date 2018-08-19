package powerlessri.anotsturdymod.items;

import net.minecraft.creativetab.CreativeTabs;
import powerlessri.anotsturdymod.items.basic.ItemBasicItem;
import powerlessri.anotsturdymod.library.enums.EMachineLevel;

public class ItemExchanger extends ItemBasicItem {
	
	public ItemExchanger(String name, EMachineLevel level) {
		super(level.getName() + "_" + name);
		
		this.setCreativeTab(CreativeTabs.TOOLS);
		this.setMaxStackSize(1);
	}
	
}
