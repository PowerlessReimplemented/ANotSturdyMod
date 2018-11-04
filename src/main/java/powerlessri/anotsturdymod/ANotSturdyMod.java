package powerlessri.anotsturdymod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import powerlessri.anotsturdymod.handlers.ComponentizedGuiHandler;
import powerlessri.anotsturdymod.handlers.VanillaGuiHandler;
import powerlessri.anotsturdymod.handlers.proxy.CommonProxy;
import powerlessri.anotsturdymod.varia.Reference;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class ANotSturdyMod {

    @Mod.Instance(Reference.MODID)
    public static ANotSturdyMod instance;

    @SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    public static SimpleNetworkWrapper genericChannel;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new VanillaGuiHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new ComponentizedGuiHandler()); // Ensuring all linked classes are loaded by FML before start searching for @TemplateProvider's
        genericChannel = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID + "main");

        proxy.modInit();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }

}
