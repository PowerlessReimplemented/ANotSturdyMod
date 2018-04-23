package powerlessri.anotsturdymod.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import powerlessri.anotsturdymod.utils.IHasVariants;
import powerlessri.anotsturdymod.utils.Reference;

public class ClientProxy extends CommonProxy {
	
	/**
	 * In most of case, "meta" parameter is 0.
	 * Simplify method arguments.
	 * 
	 * @param item
	 * @param id
	 */
	public void registerItemRenderer(Item item, String id) {
		registerItemRenderer(item, 0, id);
	}
	
	/**
	 * Register an item model using item object and a meta number.
	 * 
	 * @param item
	 * @param meta
	 * @param id
	 */
	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		super.registerItemRenderer(item, meta, id);
		
		ModelLoader.setCustomModelResourceLocation(item, meta,
				new ModelResourceLocation(new ResourceLocation(Reference.MODID, item.getUnlocalizedName()), id));
	}
	
	/**
	 * Register all subitems' model of an item.
	 * 
	 * @param item
	 * @param id
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
	
}
