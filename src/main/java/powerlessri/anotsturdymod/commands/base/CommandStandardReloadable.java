package powerlessri.anotsturdymod.commands.base;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import powerlessri.anotsturdymod.varia.Reference;
import powerlessri.anotsturdymod.varia.general.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;


public abstract class CommandStandardReloadable extends CommandBase {

    public static final String PREFIX_COMMANDS = "command.ansm:";
    public static final String SUFFIX_KEYWORD = "keyword";
    public static final String SUFFIX_USAGE = "usage";
    public static final String SUFFIX_UNKOWN_SYNTAX = "unkownSyntax";


    protected final Map<String, BiConsumer<ICommandSender, String[]>> options;

    protected final String keyword;

    protected boolean useModIDPrefix;
    protected char prefixSeparator;

    protected String usage = "";
    protected String errorUnkownSyntax = "";
    protected String errorParsing = "";

    public CommandStandardReloadable(String keyword) {
        this.options = new HashMap<String, BiConsumer<ICommandSender, String[]>>();
        this.keyword = keyword;

        this.useModIDPrefix = false;
        this.prefixSeparator = ':';
    }


    @Override
    public String getName() {
        if (useModIDPrefix) {
            return Reference.MODID + prefixSeparator + keyword;
        }

        return keyword;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return this.usage;
    }

    /**
     * Message when option does not exist
     */
    public String getUnkownSyntax(ICommandSender sender) {
        return errorUnkownSyntax.isEmpty() ? usage : errorUnkownSyntax;
    }


    public void sendUnkownSyntax(ICommandSender sender) {
        sender.sendMessage(Utils.createStringRed(this.getUnkownSyntax(sender)));
    }

    public void sendUnkownSyntax(ICommandSender sender, String description) {
        sendUnkownSyntax(sender);
        sender.sendMessage(Utils.createStringRed(description));
    }


    public void reload() {
        setupUsageFromLang();
        setupUnknownSyntaxFromLang();
    }

    protected void setupUsageFromLang() {
        usage = Utils.readFromLang(PREFIX_COMMANDS + keyword + "." + SUFFIX_USAGE);
    }

    protected void setupUnknownSyntaxFromLang() {
        String key = PREFIX_COMMANDS + keyword + "." + SUFFIX_UNKOWN_SYNTAX;
        errorUnkownSyntax = Utils.readFromLang(key);

        if (key.equals(errorUnkownSyntax)) {
            errorUnkownSyntax = usage;
        }
    }

}
