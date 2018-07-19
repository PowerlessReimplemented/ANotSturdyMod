package powerlessri.anotsturdymod.utils;

public class Reference {
	
	private Reference() {}
	
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
	
}
