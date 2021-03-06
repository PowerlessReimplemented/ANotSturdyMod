package powerlessri.anotsturdymod.varia.general;

import com.google.common.base.Preconditions;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import powerlessri.anotsturdymod.varia.Reference;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


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


    public static TextComponentString prefixComponentFromLang(String key) {
        return new TextComponentString(readFromLang(key) + " ");
    }

    public static TextComponentString stringComponentFromLang(String key) {
        return new TextComponentString(readFromLang(key));
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


    private static ITextComponent textStringWithStyle(String text, Style style) {
        return new TextComponentString(text).setStyle(style);
    }

    public static ITextComponent createStringRed(String description) {
        return textStringWithStyle(description, Reference.STYLE_RED);
    }

    public static ITextComponent createStringBlue(String description) {
        return textStringWithStyle(description, Reference.STYLE_BLUE);
    }

    public static ITextComponent createStringGray(String description) {
        return textStringWithStyle(description, Reference.STYLE_LIGHT_GRAY);
    }

    public static ITextComponent createStringDarkGray(String description) {
        return textStringWithStyle(description, Reference.STYLE_DARK_GRAY);
    }

    public static ITextComponent createToolDescription(String description) {
        return textStringWithStyle(description, Reference.STYLE_TOOLTIP_DESCRIPTION);
    }


    /**
     * <p>Selects the first nonnull value out of two candidates such that at least one of them is nonnull.</p>
     *
     * @param reference The value to be compared.
     * @param backup    Alternative if the parameter {@code reference} is null.
     */
    @Nonnull
    public static <T> T selectNonnull(@Nullable T reference, @Nonnull T backup) {
        if (reference != null) {
            return reference;
        }
        return Preconditions.checkNotNull(backup);
    }


}
