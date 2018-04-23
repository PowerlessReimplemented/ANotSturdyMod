package powerlessri.anotsturdymod.utils.handlers;

import java.util.List;

import org.apache.http.MethodNotSupportedException;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import powerlessri.anotsturdymod.init.ModBlocks;
import powerlessri.anotsturdymod.init.ModItems;
import powerlessri.anotsturdymod.utils.IHasModel;
import powerlessri.anotsturdymod.utils.IHasNoVariants;
import powerlessri.anotsturdymod.utils.IHasVariants;

@EventBusSubscriber
public class RegistryHandler {
	
	private RegistryHandler() {}
	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		//TODO support IHasVariants items.
		event.getRegistry()
		     .registerAll(ModItems.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		//TODO register ItemBlock corresponding to the blocks
		event.getRegistry()
		     .registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		for(Item item : ModItems.ITEMS) {
			/*if(item.getHasSubtypes() && item instanceof IHasVariants) {
				registerVariantedItem(item);
			} else if(item instanceof IHasNoVariants) {
				registerNonVariantedItem(item);
			}*/
			
			if(item instanceof IHasNoVariants) {
				((IHasNoVariants) item).registerModel();
			} else if(item instanceof IHasVariants) {
				((IHasVariants) item).registerModel();
			}
		}
	}
	
	/*
	private static void registerVariantedItem(Item item) {
		NonNullList<ItemStack> subitems = NonNullList.create();
		item.getSubItems(null, subitems);
		
		//added for commented out statement change,
		//all subitems inside must belongs to same Item class.
		Item original_item = subitems.get(0).getItem();
		
		for(ItemStack subitem : subitems) {
			//((IHasVariants) subitem.getItem()).registerModel(subitem.getItemDamage());
			((IHasVariants) original_item).registerModel(subitem.getItemDamage());
		}
	}
	
	private static void registerNonVariantedItem(Item item) {
		((IHasNoVariants) item).registerModel();
	}*/
	
}
