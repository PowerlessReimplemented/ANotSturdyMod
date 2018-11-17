package powerlessri.anotsturdymod.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.Config.Type;
import powerlessri.anotsturdymod.varia.Reference;

@Config(modid = Reference.MODID, name = Reference.NAME + "Config.main", type = Type.INSTANCE)
public class MainConfig {
    
    @Comment("Maximum range for a basic exchanger")
    @RangeInt(min = 0, max = 32)
    public static int basicExchangerRadius = 2;

    @Comment("Maximum range for a advanced exchanger")
    @RangeInt(min = 0, max = 32)
    public static int advancedExchangerRadius = 7;
    
    
    @RangeInt(min = 0)
    public static int accessPortReceiveLimit = Integer.MAX_VALUE;
    
    @RangeInt(min = 0)
    public static int accessPortExtractLimit = 5000;
    
}
