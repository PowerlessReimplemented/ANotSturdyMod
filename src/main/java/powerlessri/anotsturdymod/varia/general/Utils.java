package powerlessri.anotsturdymod.varia.general;

import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import powerlessri.anotsturdymod.varia.Reference;


/**
 * General utilities for Forge Mod developing.
 */
@SuppressWarnings("deprecation")
public class Utils {

    private Utils() {
    }

    private static Logger logger;

    public static Logger getLogger() {
        if (logger == null) {
            logger = LogManager.getFormatterLogger(Reference.MODID);
        }
        return logger;
    }

    public static void report(Throwable cause) {
        report(cause.getMessage(), cause);
    }

    public static void report(String message, Throwable cause) {
        CrashReport crashReport = CrashReport.makeCrashReport(cause, message);
        throw new ReportedException(crashReport);
    }


    public static String readFromLang(String key) {
        String result = I18n.translateToLocal(key);
        return result == null ? "" : result;
    }


    public static ResourceLocation locationOf(String path) {
        return locationOf(Reference.DOMAIN_MINECRAFT, path);
    }

    public static ResourceLocation locationOf(String domain, String path) {
        return new ResourceLocation(domain, path);
    }

    public static String formatRegistryId(String id) {
        // I'm not sure why do I chose this way...
        // Maybe for compatibility if someday Mojang decided to change resource path format.
        return locationOf(Reference.MODID, id).toString();
    }


    private static TextComponentString textStringWithStyle(String text, Style style) {
        TextComponentString result = new TextComponentString(text);
        result.setStyle(style);
        return result;
    }

    public static TextComponentString createStringRed(String description) {
        return textStringWithStyle(description, Reference.STYLE_RED);
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


    /**
     * Loop the number around until it is inside the given range 0 ~ (arrayLength - 1).
     *
     * @param index       The index to be adjusted
     * @param arrayLength Upper limit for the index
     * @return The adjusted index
     */
    public static int loopIndexAround(int index, int arrayLength) {
        if (index >= arrayLength) {
            return loopIndexAround(index - arrayLength, arrayLength);
        }
        if (index < 0) {
            // + because index would have a negative sign
            return loopIndexAround(arrayLength + index, arrayLength);
        }
        return index;
    }

}
