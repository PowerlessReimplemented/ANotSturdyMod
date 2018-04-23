package powerlessri.anotsturdymod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import powerlessri.anotsturdymod.init.ModBlocks;
import powerlessri.anotsturdymod.init.ModItems;
import powerlessri.anotsturdymod.proxy.ClientProxy;
import powerlessri.anotsturdymod.proxy.CommonProxy;
import powerlessri.anotsturdymod.utils.Reference;
import powerlessri.anotsturdymod.utils.Utils;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class ANotSturdyMod {
	
	@Mod.Instance(Reference.MODID)
	public static ANotSturdyMod instance;
	
	@SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//Utils.getLogger().info("preinit...");
		
		ModItems.registerItems(new String[] {
			"null_item",
			"obsidian_ingot",
			"redstone_ingot",
			"glowstone_ingot",
		}, CreativeTabs.MISC);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		//Utils.getLogger().info("init...");
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		//Utils.getLogger().info("postinit...");
	}
	
}
