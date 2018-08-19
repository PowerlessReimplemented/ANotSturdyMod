package powerlessri.anotsturdymod.library.utils;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public class Reference {
	
	private Reference() {}
	
	public static final int BYTE_BYTE_LENGTH = 1;
    public static final int FLOAT_BYTE_LENGTH = 4;
    public static final int INT_BYTE_LENGTH = 4;
    
    // 4 is the rate of 4bit = 1byte
    public static final int BYTE_BIT_LENGTH = BYTE_BYTE_LENGTH * 4;
    public static final int FLOAT_BIT_LENGTH = FLOAT_BYTE_LENGTH * 4;
    public static final int INT_BIT_LENGTH = INT_BYTE_LENGTH * 4;
	
    /**
     * ANotSturdyMod  => "ANotSturdyMod" (NAME)
     * ^^  ^     ^    => "ansm" (MODID)
     */
	public static final String MODID = "ansm";
	public static final String NAME = "ANotSturdyMod";
	public static final String VERSION = "1.0.2";
	public static final String ACCEPTED_VERSIONS = "[1.12.2]";
	public static final String SERVER_PROXY_CLASS = "powerlessri.anotsturdymod.proxy.CommonProxy";
	public static final String CLIENT_PROXY_CLASS = "powerlessri.anotsturdymod.proxy.ClientProxy";
	
	public static final String ERR_NON_SUBTYPED_ITEM = "Trying register sub-typed item model with non-sub-typed item.";
	public static final String ERR_SUBTYPED_ITEM = "Trying register non-sub-typed item model with sub-typed item.";
	
	public static final String COMMAND_RESOURCE_PATH_PREFIX = "command.ansm:";
	public static final String COMMAND_SUFFIX_KEYWORD = ".name";
	public static final String COMMAND_SUFFIX_USAGE = ".usage";
	
	public static final Style STYLE_RED = new Style().setColor(TextFormatting.RED);
	
}
