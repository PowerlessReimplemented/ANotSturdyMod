package powerlessri.anotsturdymod.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import powerlessri.anotsturdymod.utils.Reference;
import powerlessri.anotsturdymod.utils.handlers.interfaces.IHasNoVariants;
import powerlessri.anotsturdymod.utils.handlers.interfaces.IHasVariants;

public class ClientProxy extends CommonProxy {
	
	/**
	 * Register an item model using an item object and a meta number.
	 */
	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		super.registerItemRenderer(item, meta, id);
		
		ModelLoader.setCustomModelResourceLocation(item, meta,new ModelResourceLocation(item.getRegistryName(), id));
				//new ModelResourceLocation(new ResourceLocation(Reference.MODID, item.getUnlocalizedName().substring(5)), id));
				
	}
	
	/**
	 * 
	 */
	public void registerItemRenderer(IHasNoVariants item, String id) {
		registerItemRenderer((Item) item, 0, id);
	}
	
	/**
	 * Register all subitems' model of an item.
	 */
	public void registerItemRenderer(IHasVariants item, String id) {
		super.registerItemRenderer(item, id);
		
		ResourceLocation[] locations = new ResourceLocation[item.getVariantAmount()];
		for(int i = 0; i < locations.length; i++) {
			ResourceLocation sub_location = new ResourceLocation(Reference.MODID, item.getVariant(i));
			
			locations[i] = sub_location;
			ModelLoader.setCustomModelResourceLocation((Item) item, i, new ModelResourceLocation(sub_location, id));
		}
		
		ModelBakery.registerItemVariants((Item) item, locations);
	}
	
	
	
	@Override
	public void registerBlockRenderer(Block block, int meta, String id) {
		
	}
	
	
	
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}
	
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
	
}
