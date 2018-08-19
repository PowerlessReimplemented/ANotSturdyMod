package powerlessri.anotsturdymod.library.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.ResourceLocation;

public class Utils {
	
	private Utils() {}
	
    
	
	private static Logger logger;
	
	public static Logger getLogger() {
		if(logger == null) {
			logger = LogManager.getFormatterLogger(Reference.MODID);
		}
		return logger;
	}
	
	public static String formatRegistryId(String id) {
		// I'm not sure why do I chose this way...
		// Maybe for compability if someday Mojang decided to change resource path format
		return new ResourceLocation(Reference.MODID, id).toString();
	}
	
	
	
	public static int arrayIndex2D(int x, int y, int width) {
	    return x + y * width;
	}
    
    public static int pow(int base, int exponent) {
        //TODO own pow() implementation
        return (int) Math.pow(base, exponent);
    }
    public static int pow(int base) {
        return base * base;
    }
}
