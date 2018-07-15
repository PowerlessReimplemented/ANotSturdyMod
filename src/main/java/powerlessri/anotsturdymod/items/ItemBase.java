package powerlessri.anotsturdymod.items;

import org.apache.http.MethodNotSupportedException;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.init.ModItems;
import powerlessri.anotsturdymod.utils.IHasModel;
import powerlessri.anotsturdymod.utils.Reference;
import powerlessri.anotsturdymod.utils.Utils;

public class ItemBase extends Item implements IHasModel{
	
	public ItemBase(String registry_name, String unlocalized_name, CreativeTabs tab) {
		this.setRegistryName(new ResourceLocation(Reference.MODID, registry_name));
		this.setUnlocalizedName(Reference.MODID + ":" + registry_name);
		this.setCreativeTab(tab);
	}

	@Override
	public void registerModel() throws MethodNotSupportedException {
		throw new MethodNotSupportedException("");
	}

	@Override
	public void registerModel(int meta) throws MethodNotSupportedException {
		throw new MethodNotSupportedException("");
	}

	@Override
	public void registerModel(ItemStack subitem) throws MethodNotSupportedException {
		throw new MethodNotSupportedException("");
	}

}
