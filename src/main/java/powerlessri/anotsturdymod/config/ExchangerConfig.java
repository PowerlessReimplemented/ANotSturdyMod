package powerlessri.anotsturdymod.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.Config.Type;
import powerlessri.anotsturdymod.varia.Reference;

@Config(modid = Reference.MODID, category = "exchangers")

public class ExchangerConfig {
    
    @Comment("Maximum range for a basic exchanger")
    @RangeInt(min = 0, max = 32)
    public static int basicExchangerRadius = 2;
    
    @Comment("Maximum range for a advanced exchanger")
    @RangeInt(min = 0, max = 32)
    public static int advancedExchangerRadius = 7;
    
}
