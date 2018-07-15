package powerlessri.anotsturdymod.items.base;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.utils.Reference;

public abstract class ItemBase extends Item {
	
	public ItemBase(String registry_name, String unlocalized_name, CreativeTabs tab) {
		this.setRegistryName(new ResourceLocation(Reference.MODID, registry_name));
		this.setUnlocalizedName(Reference.MODID + ":" + registry_name);
		this.setCreativeTab(tab);
	}

}
