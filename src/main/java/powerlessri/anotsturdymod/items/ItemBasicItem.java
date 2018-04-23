package powerlessri.anotsturdymod.items;

import org.apache.http.MethodNotSupportedException;

import net.minecraft.creativetab.CreativeTabs;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.utils.IHasNoVariants;
import powerlessri.anotsturdymod.utils.Reference;
import powerlessri.anotsturdymod.utils.Utils;

public class ItemBasicItem extends ItemBase implements IHasNoVariants {

	public ItemBasicItem(String registry_name, String unlocalized_name, CreativeTabs tab) {
		super(registry_name, unlocalized_name, tab);
	}
	
	@Override
	public void registerModel() {
		ANotSturdyMod.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	@Override
	public void registerModel(int meta) throws MethodNotSupportedException {
		Utils.getLogger().error("Item " + this.getRegistryName() + "does not support register sub-typed model.");
		throw new MethodNotSupportedException(Reference.ERR_SUBTYPED_ITEM);
	}

}
