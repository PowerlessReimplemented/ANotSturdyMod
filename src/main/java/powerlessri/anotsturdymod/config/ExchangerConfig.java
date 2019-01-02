package powerlessri.anotsturdymod.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.RangeInt;
import powerlessri.anotsturdymod.varia.Reference;

@LangKey("config.ansm:ansm_main.category.exchangers")
@Config(modid = Reference.MODID, name = Reference.MAIN_CONFIG_FILE, category = "exchangers")
public class ExchangerConfig {
    
    @Comment("Maximum range for a basic exchanger")
    @RangeInt(min = 0, max = 32)
    public static int basicExchangerRadius = 2;
    
    @Comment("Maximum range for a advanced exchanger")
    @RangeInt(min = 0, max = 32)
    public static int advancedExchangerRadius = 7;
    
}
