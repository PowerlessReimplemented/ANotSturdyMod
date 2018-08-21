package powerlessri.anotsturdymod.library.utils;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public class Reference {

    private Reference() {
    }

    public static final int BYTE_BYTE_LENGTH = 1;
    public static final int FLOAT_BYTE_LENGTH = 4;
    public static final int INT_BYTE_LENGTH = 4;

    // 8 is the rate of 8bit = 1byte
    public static final int BYTE_BIT_LENGTH = BYTE_BYTE_LENGTH * 8;
    public static final int FLOAT_BIT_LENGTH = FLOAT_BYTE_LENGTH * 8;
    public static final int INT_BIT_LENGTH = INT_BYTE_LENGTH * 8;

    /**
     * ANotSturdyMod  => "ANotSturdyMod" (NAME)
     * ^^  ^     ^    => "ansm" (MODID)
     */
    public static final String MODID = "ansm";
    public static final String NAME = "ANotSturdyMod";
    public static final String VERSION = "1.0.2";
    public static final String ACCEPTED_VERSIONS = "[1.12.2]";
    public static final String COMMON_PROXY_CLASS = "powerlessri.anotsturdymod.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "powerlessri.anotsturdymod.proxy.ServerProxy";
    public static final String CLIENT_PROXY_CLASS = "powerlessri.anotsturdymod.proxy.ClientProxy";

    //TODO load these in .lang file
//    public static final String ERR_NON_SUBTYPED_ITEM = "Trying register sub-typed item model with non-sub-typed item.";
//    public static final String ERR_SUBTYPED_ITEM = "Trying register non-sub-typed item model with sub-typed item.";

    public static final String DOMAIN_MINECRAFT = "minecraft";

    public static final String COMMAND_RESOURCE_PATH_PREFIX = "command.ansm:";
    public static final String COMMAND_SUFFIX_KEYWORD = ".name";
    public static final String COMMAND_SUFFIX_USAGE = ".usage";

    public static final Style STYLE_EMPTY = new Style();
    public static final Style STYLE_RED = new Style().setColor(TextFormatting.RED);
    public static final Style STYLE_BLUE = new Style().setColor(TextFormatting.BLUE);
    public static final Style STYLE_DARK_GRAY = new Style().setColor(TextFormatting.DARK_GRAY);
    public static final Style STYLE_LIGHT_GRAY = new Style().setColor(TextFormatting.GRAY);

    /** Actual explanation for what this does */
    public static final Style STYLE_TOOLTIP_DESCRIPTION = new Style().setColor(TextFormatting.GRAY);
    /** Vazkii styled description */
    public static final Style STYLE_TOOLTIP_GRIND = new Style().setColor(TextFormatting.DARK_GRAY).setItalic(true);

    /** Status label on item's tooltip */
    public static final Style STYLE_TOOLTIP_STAT_TYPE = new Style().setColor(TextFormatting.DARK_PURPLE).setBold(true);
    /** Changing value on a status display for item */
    public static final Style STYLE_TOOLTIP_STAT_VALUE = new Style();
    
    


}
