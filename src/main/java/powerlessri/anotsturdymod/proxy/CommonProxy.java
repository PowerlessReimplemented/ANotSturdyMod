package powerlessri.anotsturdymod.proxy;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import powerlessri.anotsturdymod.init.ModItems;
import powerlessri.anotsturdymod.items.ItemExchanger;
import powerlessri.anotsturdymod.items.ItemTransmutator;
import powerlessri.anotsturdymod.items.handler.WorldTransmutation;
import powerlessri.anotsturdymod.utils.handlers.interfaces.IHasNoVariants;
import powerlessri.anotsturdymod.utils.handlers.interfaces.IHasVariants;

public class CommonProxy {
	
	// leave to be overridden for ClientProxy
	public void registerItemRenderer(Item item, int meta, String id) {
	}
	public void registerItemRenderer(IHasNoVariants item, String id) {
	}
	public void registerItemRenderer(IHasVariants item, String id) {
	}
	public void registerBlockRenderer(Block block, int meta, String id) {
	}
	
	
	
	public void preInit(FMLPreInitializationEvent event) {
		ModItems.registerItems(new String[] {
				"null_item",
				"obsidian_ingot",
				"redstone_ingot",
				"glowstone_ingot",
			}, CreativeTabs.MISC);
		
		ModItems.ITEMS.add(new ItemTransmutator("transmutator"));
		ModItems.ITEMS.add(new ItemExchanger("exchanger"));
	}
	
	public void init(FMLInitializationEvent event) {
		WorldTransmutation.init(event);
	}
	
	public void postInit(FMLPostInitializationEvent event) {
	}
	
}
