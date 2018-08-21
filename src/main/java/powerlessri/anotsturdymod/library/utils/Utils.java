package powerlessri.anotsturdymod.library.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;

@SuppressWarnings("deprecation")
public class Utils {

    private Utils() {}



    private static Logger logger;
    public static Logger getLogger() {
        if(logger == null) {
            logger = LogManager.getFormatterLogger(Reference.MODID);
        }
        return logger;
    }

    public static String readFromLang(String key) {
        String result = I18n.translateToLocal(key);
        return result == null ? "" : result;
    }

    private static TextComponentString textStringWithStyle(String text, Style style) {
        TextComponentString result = new TextComponentString(text);
        result.setStyle(style);
        return result;
    }

    public static TextComponentString createStringRed(String description) {
        return textStringWithStyle(description, Reference.STYLE_RED);
    }


	public static ResourceLocation locationOf(String path) {
        return locationOf(Reference.DOMAIN_MINECRAFT, path);
    }

	public static ResourceLocation locationOf(String domain, String path) {
        return new ResourceLocation(domain, path);
    }

	public static String formatRegistryId(String id) {
		// I'm not sure why do I chose this way...
		// Maybe for compability if someday Mojang decided to change resource path format
		return locationOf(Reference.MODID, id).toString();
	}



	public static int arrayIndex2D(int x, int y, int width) {
	    return x + y * width;
	}

    public static TextComponentString createStringBlue(String description) {
        return textStringWithStyle(description, Reference.STYLE_BLUE);
    }

    public static TextComponentString createStringGray(String description) {
        return textStringWithStyle(description, Reference.STYLE_LIGHT_GRAY);
    }

    public static TextComponentString createStringDarkGray(String description) {
        return textStringWithStyle(description, Reference.STYLE_DARK_GRAY);
    }

    public static TextComponentString createToolDescription(String description) {
        return textStringWithStyle(description, Reference.STYLE_TOOLTIP_DESCRIPTION);
    }

}
