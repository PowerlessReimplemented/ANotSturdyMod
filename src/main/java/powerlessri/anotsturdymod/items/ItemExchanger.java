package powerlessri.anotsturdymod.items;

import net.minecraft.creativetab.CreativeTabs;
import powerlessri.anotsturdymod.items.basic.ItemBasicItem;
import powerlessri.anotsturdymod.utils.handlers.enums.EMachineLevel;

public class ItemExchanger extends ItemBasicItem {
	
	public ItemExchanger(EMachineLevel level) {
		super(level.getName() + "_exchanger");
		
		this.setCreativeTab(CreativeTabs.TOOLS);
		this.setMaxStackSize(1);
	}
	
}
