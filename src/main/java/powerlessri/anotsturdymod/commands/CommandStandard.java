package powerlessri.anotsturdymod.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import powerlessri.anotsturdymod.init.ModCommands;
import powerlessri.anotsturdymod.library.utils.Reference;
import powerlessri.anotsturdymod.library.utils.Utils;


public abstract class CommandStandard extends CommandBase {

    protected final Map<String, BiConsumer<ICommandSender, String[]>> options;

    protected final String keyword;
    protected final String langPath;
    
    protected boolean useModIDPrefix;
    protected char prefixSeparator;
    
    protected String usage;
    protected String errorUnkownSyntax;
    protected String errorParsing;

    public CommandStandard(String keyword) {
        this.options = new HashMap<String, BiConsumer<ICommandSender, String[]>>();
        this.keyword = keyword;
        this.langPath = Reference.COMMAND_RESOURCE_PATH_PREFIX + this.keyword;
        
        this.useModIDPrefix = false;
        this.prefixSeparator = ':';
        
        ModCommands.COMMANDS.add(this);
    }
    
    protected void setupUsageFromLang() {
        this.usage = Utils.readFromLang(this.langPath + Reference.COMMAND_SUFFIX_USAGE);
    }
    
    protected void setupUnkownSyntaxFromLang() {
        this.errorUnkownSyntax = Utils.readFromLang(this.langPath + Reference.COMMAND_SUFFIX_USAGE);
    }


    @Override
    public String getName() {
        if(this.useModIDPrefix) {
            return Reference.MODID + this.prefixSeparator + this.keyword;
        }
        return this.keyword;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return this.usage;
    }
    
    public String getUnkownUsage(ICommandSender sender) {
        return this.errorUnkownSyntax;
    }



    public void sendUnkownUsage(ICommandSender sender) {
        sender.sendMessage(Utils.createStringRed(this.getUnkownUsage(sender)));
    }

    public void sendUnkownUsage(ICommandSender sender, String description) {
        sendUnkownUsage(sender);
        sender.sendMessage(Utils.createStringRed(description));
    }

}
