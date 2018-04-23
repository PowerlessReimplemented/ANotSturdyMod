package powerlessri.anotsturdymod.utils;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {
	
	private Utils() {}
	
    public static final int BYTE_BYTE_LENGTH = 1;
    public static final int FLOAT_BYTE_LENGTH = 4;
    public static final int INT_BYTE_LENGTH = 4;
    
    // 4 is the rate of 4bit = 1byte
    public static final int BYTE_BIT_LENGTH = BYTE_BYTE_LENGTH * 4;
    public static final int FLOAT_BIT_LENGTH = FLOAT_BYTE_LENGTH * 4;
    public static final int INT_BIT_LENGTH = INT_BYTE_LENGTH * 4;
	
	private static Logger logger;
	
	public static Logger getLogger() {
		if(logger == null) {
			logger = LogManager.getFormatterLogger(Reference.MODID);
		}
		return logger;
	}
	
	
	public static int arrayIndex2D(int x, int y, int width) {
	    return x + y * width;
	}
    
    public static int pow(int base, int exponent) {
        //temporary
        return (int) Math.pow(base, exponent);
    }
    public static int pow(int base) {
        return base * base;
    }
}
