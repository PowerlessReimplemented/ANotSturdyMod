package powerlessri.anotsturdymod.items.base;

import net.minecraft.item.Item;
import powerlessri.anotsturdymod.library.utils.Reference;

public abstract class ItemBase extends Item {
	
	public ItemBase(String registry_name, String unlocalized_name) {
		///this.setRegistryName(new ResourceLocation(Reference.MODID, registry_name));
		this.setRegistryName(registry_name);
		this.setUnlocalizedName(Reference.MODID + ":" + unlocalized_name);
		//this.setUnlocalizedName(unlocalized_name);
	}

}
