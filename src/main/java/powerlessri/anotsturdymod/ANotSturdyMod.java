package powerlessri.anotsturdymod;

import net.minecraft.command.CommandBase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import powerlessri.anotsturdymod.handlers.ModCommands;
import powerlessri.anotsturdymod.handlers.ModGuiHandler;
import powerlessri.anotsturdymod.library.utils.Reference;
import powerlessri.anotsturdymod.library.utils.Utils;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class ANotSturdyMod {

    @Mod.Instance(Reference.MODID)
    public static ANotSturdyMod instance;

    @SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Utils.getLogger().info(Reference.MODID + " excuting preInit");

        proxy.modInit();
        proxy.preInit(event);

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new ModGuiHandler());
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

        for (CommandBase c : ModCommands.COMMANDS) {
            event.registerServerCommand(c);
        }
    }

}
