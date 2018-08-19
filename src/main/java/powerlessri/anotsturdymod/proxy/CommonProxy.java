package powerlessri.anotsturdymod.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import powerlessri.anotsturdymod.init.ModItems;
import powerlessri.anotsturdymod.items.ItemExchanger;
import powerlessri.anotsturdymod.items.ItemTransmutationStone;
import powerlessri.anotsturdymod.items.handler.WorldTransmutation;
import powerlessri.anotsturdymod.library.enums.EMachineLevel;

public class CommonProxy {
	
	// leave to be overridden for ClientProxy
	public void registerItemRenderer(Item item, int meta, String id) {
	}
	public void registerBlockRenderer(Block block, int meta, String id) {
	}
	
	
	
	public void preInit(FMLPreInitializationEvent event) {
		ModItems.ITEMS.add(new ItemTransmutationStone("transmutation_orb"));
		ModItems.ITEMS.add(new ItemExchanger(EMachineLevel.BASIC));
		ModItems.ITEMS.add(new ItemExchanger(EMachineLevel.ADVANCED));
	}
	
	public void init(FMLInitializationEvent event) {
		WorldTransmutation.init(event);
	}
	
	public void postInit(FMLPostInitializationEvent event) {
	}
	
}
