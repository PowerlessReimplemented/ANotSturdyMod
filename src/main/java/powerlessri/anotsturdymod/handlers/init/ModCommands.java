package powerlessri.anotsturdymod.handlers.init;

import net.minecraft.command.CommandBase;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.util.ArrayList;
import java.util.List;

public class ModCommands {
    
    public static void registerCommands(FMLServerStartingEvent event) {
        for (CommandBase c : ModCommands.COMMANDS) {
            event.registerServerCommand(c);
        }
    }
    

    private ModCommands() {
    }

    public static final List<CommandBase> COMMANDS = new ArrayList<>();
    
}
