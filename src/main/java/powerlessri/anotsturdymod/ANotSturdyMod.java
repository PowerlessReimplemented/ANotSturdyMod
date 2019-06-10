package powerlessri.anotsturdymod;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import powerlessri.anotsturdymod.handlers.ComponentizedGuiHandler;
import powerlessri.anotsturdymod.varia.Reference;

@Mod(Reference.MODID)
public class ANotSturdyMod {

    public static ANotSturdyMod instance;

    public static  network;

    public static ComponentizedGuiHandler gui;

    public ANotSturdyMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::setup);
        eventBus.addListener(this::serverStarting);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            eventBus.addListener(this::setupClient);
        });
    }

    private void setup(FMLCommonSetupEvent event) {

    }

    private void setupClient(FMLClientSetupEvent event) {

    }
    
//    @EventHandler
//    public void preInit(FMLPreInitializationEvent event) {
//        network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID + "main");
//        gui = new ComponentizedGuiHandler(this);
//
//        proxy.preInit(event);
//    }

    private void serverStarting(FMLServerStartingEvent event) {

    }

//    @EventHandler
//    public void serverStarting(FMLServerStartingEvent event) {
//        proxy.serverStarting(event);
//    }

}
