package powerlessri.anotsturdymod.commands;

import java.util.Arrays;
import java.util.function.BiConsumer;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;


/**
 * General debug tool for mod/map/modpack development.
 * 
 * @author root-user
 */
public class CommandDebug extends CommandStandardReloadable {

    public CommandDebug() {
        super("debug");

        this.options.put("hand", (sender, rest) -> {
            EntityPlayer player = (EntityPlayer) sender;
            ItemStack mainHand = player.getHeldItemMainhand();

            if(rest.length == 0 || rest[0].equals("id"))
                sender.sendMessage(new TextComponentString("Item CodeId: " + mainHand.getItem().getRegistryName()));
            if(rest.length == 0 || rest[0].equals("meta"))
                sender.sendMessage(new TextComponentString("Item Metadata: " + mainHand.getItemDamage()));
            if(rest.length == 0 || rest[0].equals("nbt"))
                sender.sendMessage(new TextComponentString("Item NBT: " + mainHand.getTagCompound()));
        });

        this.useModIDPrefix = true;
        this.prefixSeparator = '_';
        this.reload();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(sender.getCommandSenderEntity() instanceof EntityPlayer) {
            matchOptions(sender, args[0], Arrays.copyOfRange(args, 1, args.length));
        }
    }

    private void matchOptions(ICommandSender sender, String option, String[] rest) {
        BiConsumer<ICommandSender, String[]> excution = this.options.get(option);

        if(excution == null) {
            this.sendUnkownUsage(sender);
            return;
        }

        excution.accept(sender, rest);
    }

}
