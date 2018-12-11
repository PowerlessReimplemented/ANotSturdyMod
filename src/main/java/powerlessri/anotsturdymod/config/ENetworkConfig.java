package powerlessri.anotsturdymod.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.RangeInt;
import powerlessri.anotsturdymod.varia.Reference;

@Config(modid = Reference.MODID, category = "energy_network")
public class ENetworkConfig {
    
    @RangeInt(min = 0)
    public static int accessPortReceiveLimit = Integer.MAX_VALUE;
    
    @RangeInt(min = 0)
    public static int accessPortExtractLimit = 5000;
    
}
