package powerlessri.anotsturdymod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import powerlessri.anotsturdymod.library.handlers.CommonReloadHandler;
import powerlessri.anotsturdymod.library.utils.Reference;
import powerlessri.anotsturdymod.library.utils.Utils;


@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class ANotSturdyMod {

    @Mod.Instance(Reference.MODID)
    public static ANotSturdyMod instance;

    @SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    // Side-dependent start //

    public CommonReloadHandler reloadHandler;

    // Side-dependent end //

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Utils.getLogger().info(Reference.MODID + " excuting preInit");

        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        Utils.getLogger().info(Reference.MODID + " excuting init");

        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Utils.getLogger().info(Reference.MODID + " excuting postInit");

        proxy.postInit(event);
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        Utils.getLogger().info(Reference.MODID + " excuting serverStarting");

        proxy.serverStarting(event);
    }

}
