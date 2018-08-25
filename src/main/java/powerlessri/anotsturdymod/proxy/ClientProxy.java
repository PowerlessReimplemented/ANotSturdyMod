package powerlessri.anotsturdymod.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.library.handlers.ClientReloadHandler;

public class ClientProxy extends CommonProxy {

	/**
	 * Register an item model using an item object and a meta number.
	 */
	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		super.registerItemRenderer(item, meta, id);

		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));

	}

	@Override
	public void registerBlockRenderer(Block block, int meta, String id) {

	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		ANotSturdyMod.instance.reloadHandler = new ClientReloadHandler();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

	@Override
	public void serverStarting(FMLServerStartingEvent event) {
		super.serverStarting(event);
	}

}
