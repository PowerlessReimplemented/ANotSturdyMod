package powerlessri.anotsturdymod.handlers.init;

import net.minecraft.command.CommandBase;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import powerlessri.anotsturdymod.varia.general.Utils;
import powerlessri.anotsturdymod.varia.reflection.AnnotationRetentionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ModCommands {

    public static final List<CommandBase> COMMANDS = new ArrayList<>();

    
    public static void registerCommands(FMLServerStartingEvent event) {
        for (CommandBase command : COMMANDS) {
            event.registerServerCommand(command);
        }
    }

    public static void preInit(FMLPreInitializationEvent event) {
        loadCommands(event.getAsmData());
    }

    private static void loadCommands(ASMDataTable table) {
        List<Field> fields = AnnotationRetentionUtils.getAllAnnotatedFields(table, RegistryCommandBase.class);
        for (Field field : fields) {
            CommandBase command;
            try {
                command = (CommandBase) field.get(null);
            } catch (ClassCastException e) {
                Utils.report("Field specified as a RegistryCommandRood, but has a different type than CommandBase. ", e);
                continue;
            } catch (IllegalAccessException e) {
                // This should not happen since getAllAnnotatedFields sets the accessibility to true.
                Utils.getLogger().error("Cannot access the field " + field.getName() + " during command instance searching.");
                continue;
            }

            COMMANDS.add(command);
        }
    }
    
}
