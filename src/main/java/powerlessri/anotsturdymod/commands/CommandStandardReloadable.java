package powerlessri.anotsturdymod.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.library.interfaces.IResourcesReloadable;
import powerlessri.anotsturdymod.library.utils.Reference;
import powerlessri.anotsturdymod.library.utils.Utils;

public abstract class CommandStandardReloadable extends CommandBase implements IResourcesReloadable {

	protected final Map<String, BiConsumer<ICommandSender, String[]>> options;

	protected final String keyword;
	protected final String langKey;

	protected boolean useModIDPrefix;
	protected char prefixSeparator;

	protected String usage;
	protected String errorUnkownSyntax;
	protected String errorParsing;

	public CommandStandardReloadable(String keyword) {
		this.options = new HashMap<String, BiConsumer<ICommandSender, String[]>>();
		this.keyword = keyword;
		this.langKey = Reference.COMMAND_RESOURCE_PATH_PREFIX + this.keyword;

		this.useModIDPrefix = false;
		this.prefixSeparator = ':';

		ANotSturdyMod.instance.reloadHandler.addLang(this);
	}

	protected void setupUsageFromLang() {
		this.usage = Utils.readFromLang(this.langKey + Reference.COMMAND_SUFFIX_USAGE);
	}

	protected void setupUnkownSyntaxFromLang() {
		this.errorUnkownSyntax = Utils.readFromLang(this.langKey + Reference.COMMAND_SUFFIX_USAGE);
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

	@Override
	public void reload() {
		this.setupUsageFromLang();
		this.setupUnkownSyntaxFromLang();
	}

}
