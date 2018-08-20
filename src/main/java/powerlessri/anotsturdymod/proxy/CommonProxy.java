package powerlessri.anotsturdymod.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import powerlessri.anotsturdymod.init.ModCommands;
import powerlessri.anotsturdymod.items.handler.WorldTransmutation;

public class CommonProxy {
	
	// Client side only stuffs
	public void registerItemRenderer(Item item, int meta, String id) {
	}
	public void registerBlockRenderer(Block block, int meta, String id) {
	}
	
	
	
	public void preInit(FMLPreInitializationEvent event) {
	}
	
	public void init(FMLInitializationEvent event) {
		WorldTransmutation.init(event);
	}
	
	public void postInit(FMLPostInitializationEvent event) {
	}
	
	public void serverStarting(FMLServerStartingEvent event) {
	    ModCommands.COMMANDS.forEach((c) -> {
	        event.registerServerCommand(c);
	    });
	}
	
}
