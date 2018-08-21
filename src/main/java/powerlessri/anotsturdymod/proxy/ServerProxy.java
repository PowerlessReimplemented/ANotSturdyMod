package powerlessri.anotsturdymod.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import powerlessri.anotsturdymod.commands.CommandAnsm;
import powerlessri.anotsturdymod.commands.CommandDebug;
import powerlessri.anotsturdymod.init.ModCommands;
import powerlessri.anotsturdymod.items.handler.WorldTransmutation;

public class ServerProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }
    
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        
        ModCommands.COMMANDS.add(new CommandDebug());
        ModCommands.COMMANDS.add(new CommandAnsm());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        
        WorldTransmutation.init(event);
    }
    
    
    
    @Override
    public void serverStarting(FMLServerStartingEvent event) {
        super.serverStarting(event);
        
        ModCommands.COMMANDS.forEach((c) -> {
            event.registerServerCommand(c);
        });
    }
    
    
}
