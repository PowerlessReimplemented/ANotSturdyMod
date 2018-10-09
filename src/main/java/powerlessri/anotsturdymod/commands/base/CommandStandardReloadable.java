package powerlessri.anotsturdymod.commands.base;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.handlers.reloaders.IResourcesReloadable;
import powerlessri.anotsturdymod.library.Reference;
import powerlessri.anotsturdymod.library.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;


public abstract class CommandStandardReloadable extends CommandBase implements IResourcesReloadable {

    public static final String SUFFIX_KEYWORD = "keyword";
    public static final String SUFFIX_USAGE = "usage";
    public static final String SUFFIX_UNKOWN_SYNTAX = "unkownSyntax";


    protected final Map<String, BiConsumer<ICommandSender, String[]>> options;

    protected final String keyword;

    protected boolean useModIDPrefix;
    protected char prefixSeparator;

    protected String usage;
    protected String errorUnkownSyntax;
    protected String errorParsing;

    public CommandStandardReloadable(String keyword) {
        this.options = new HashMap<String, BiConsumer<ICommandSender, String[]>>();
        this.keyword = keyword;

        this.useModIDPrefix = false;
        this.prefixSeparator = ':';

        ANotSturdyMod.proxy.reloadHandler.addLang(this);
    }


    @Override
    public String getName() {
        if (this.useModIDPrefix) {
            return Reference.MODID + this.prefixSeparator + this.keyword;
        }

        return this.keyword;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return this.usage;
    }

    /**
     * Message when option does not exist
     */
    public String getUnkownSyntax(ICommandSender sender) {
        return this.errorUnkownSyntax;
    }


    public void sendUnkownSyntax(ICommandSender sender) {
        sender.sendMessage(Utils.createStringRed(this.getUnkownSyntax(sender)));
    }

    public void sendUnkownSyntax(ICommandSender sender, String description) {
        sendUnkownSyntax(sender);
        sender.sendMessage(Utils.createStringRed(description));
    }


    @Override
    public void reload() {
        this.setupUsageFromLang();
        this.setupUnkownSyntaxFromLang();
    }

    protected void setupUsageFromLang() {
        this.usage = Utils.readCommand(this.keyword, SUFFIX_USAGE);
    }

    protected void setupUnkownSyntaxFromLang() {
        this.errorUnkownSyntax = Utils.readCommand(this.keyword, SUFFIX_UNKOWN_SYNTAX);
    }

}
