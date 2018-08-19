package powerlessri.anotsturdymod.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import powerlessri.anotsturdymod.library.utils.Reference;
import powerlessri.anotsturdymod.library.utils.Utils;


public abstract class CommandStandard extends CommandBase {

    protected final Map<String, BiConsumer<ICommandSender, String[]>> options;

    protected String keyword;
    protected boolean useModIDPrefix;
    
    protected String usage;
    protected String errorUnkownSyntax;
    protected String errorParsing;

    public CommandStandard() {
        this.options = new HashMap<String, BiConsumer<ICommandSender, String[]>>();
    }
    
    protected void setUsageFromLang() {
        
    }
    
    protected void setUnkownSyntaxFromLang() {
        
    }


    @Override
    public String getName() {
        if(this.useModIDPrefix) {
            return Reference.MODID + "_" + this.keyword;
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
