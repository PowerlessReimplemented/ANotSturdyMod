package powerlessri.anotsturdymod.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.RangeInt;
import powerlessri.anotsturdymod.varia.Reference;

@LangKey("config.ansm:ansm_main.category.energyNetwork")
@Config(modid = Reference.MODID, name = Reference.MAIN_CONFIG_FILE, category = "energy_network")
public class ENetworkConfig {

    @RangeInt(min = 0)
    public static int accessPortReceiveLimit = Integer.MAX_VALUE;

    @RangeInt(min = 0)
    public static int accessPortExtractLimit = 5000;

}
