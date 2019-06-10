package powerlessri.anotsturdymod.handlers.proxy;

import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.blocks.remoteenetwork.tile.*;
import powerlessri.anotsturdymod.handlers.init.ModBlocks;
import powerlessri.anotsturdymod.handlers.init.ModCommands;
import powerlessri.anotsturdymod.handlers.init.ModItems;
import powerlessri.anotsturdymod.handlers.init.ModTileEntities;
import powerlessri.anotsturdymod.items.transmutations.WorldTransmutation;
import powerlessri.anotsturdymod.library.network.notification.PacketNotification;
import powerlessri.anotsturdymod.network.actions.PacketLocationalGuiAction;
import powerlessri.anotsturdymod.network.PacketServerCommand;

public class CommonProxy {

    public void construct(FMLConstructionEvent event) {
    }

    public void preInit(FMLPreInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(ANotSturdyMod.instance, ANotSturdyMod.gui);
        ANotSturdyMod.gui.init(event.getAsmData());
        
        ModBlocks.preInit(event);
        ModTileEntities.preInit(event);
        ModItems.preInit(event);
        ModCommands.preInit(event);

        int packetId = 0;
        ANotSturdyMod.network.registerMessage(PacketServerCommand.Handler.class, PacketServerCommand.class, packetId++, Side.SERVER);
        ANotSturdyMod.network.registerMessage(PacketNotification.Handler.class, PacketNotification.class, packetId++, Side.SERVER);
        ANotSturdyMod.network.registerMessage(PacketLocationalGuiAction.Handler.class, PacketLocationalGuiAction.class, packetId++, Side.SERVER);
        
        TileENComponentBase.initNetwork();
        TileENWirelessTransmitter.initNetwork();
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
        WorldTransmutation.init(event);
    }

    public void serverStarting(FMLServerStartingEvent event) {
        ModCommands.registerCommands(event);
    }
    
}
