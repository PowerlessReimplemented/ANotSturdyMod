package powerlessri.anotsturdymod.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import powerlessri.anotsturdymod.commands.base.CommandStandardReloadable;
import powerlessri.anotsturdymod.handlers.init.RegistryCommandBase;
import powerlessri.anotsturdymod.varia.general.Utils;

import java.util.Arrays;
import java.util.function.BiConsumer;


/**
 * General debug tool for mod/map/modpack development.
 *
 * @author root-user
 */
public class CommandAnsmUtils extends CommandStandardReloadable {

    @RegistryCommandBase
    public static final CommandAnsmUtils INSTANCE = new CommandAnsmUtils();

    public static final String CMD_HAND = "hand";
    public static final String OUTPUT_DESCRIPTION = ".outputDescription";

    
    public final String LANG_HAND_ID = PREFIX_COMMANDS + keyword + "." + CMD_HAND + ".id" + OUTPUT_DESCRIPTION;
    public final String LANG_HAND_META = PREFIX_COMMANDS + keyword + "." + CMD_HAND + ".meta" + OUTPUT_DESCRIPTION;
    public final String LANG_HAND_NBT = PREFIX_COMMANDS + keyword + "." + CMD_HAND + ".nbt" + OUTPUT_DESCRIPTION;

    public CommandAnsmUtils() {
        super("utils");

        this.options.put(CMD_HAND, (sender, rest) -> {
            EntityPlayer player = (EntityPlayer) sender;
            ItemStack mainHand = player.getHeldItemMainhand();

            if (rest.length == 0 || rest[0].equals("id")) {
                sender.sendMessage(new TextComponentString(
                        Utils.readFromLang(LANG_HAND_ID) +
                                mainHand.getItem().getRegistryName()));
            }

            if (rest.length == 0 || rest[0].equals("meta")) {
                sender.sendMessage(new TextComponentString(
                        Utils.readFromLang(LANG_HAND_META) +
                                mainHand.getItemDamage()));
            }

            if (rest.length == 0 || rest[0].equals("nbt")) {
                sender.sendMessage(new TextComponentString(
                        Utils.readFromLang(LANG_HAND_NBT) +
                                mainHand.getTagCompound()));
            }
        });

        this.useModIDPrefix = true;
        this.prefixSeparator = '_';
        this.reload();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender.getCommandSenderEntity() instanceof EntityPlayer) {
            if (args.length > 0) {
                matchOptions(sender, args[0], Arrays.copyOfRange(args, 1, args.length));
            } else {
                sendUnknownSyntax(sender);
            }
        }
    }

    private void matchOptions(ICommandSender sender, String option, String[] rest) {
        BiConsumer<ICommandSender, String[]> excution = this.options.get(option);

        if (excution == null) {
            sendUnknownSyntax(sender);
            return;
        }

        excution.accept(sender, rest);
    }

}
