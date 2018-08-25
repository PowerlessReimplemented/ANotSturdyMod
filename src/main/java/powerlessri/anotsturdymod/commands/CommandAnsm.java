package powerlessri.anotsturdymod.commands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandAnsm extends CommandStandardReloadable {

	public CommandAnsm() {
		super("ansm");

		this.reload();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

	}

}
