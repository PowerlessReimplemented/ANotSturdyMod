package powerlessri.anotsturdymod.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.Config.Type;
import powerlessri.anotsturdymod.varia.Reference;

@Config(modid = Reference.MODID)
public class MainConfig {
    
    @Comment({
            "If true, many OP items/blocks will be registered into the game.",
            "From ancient ages..."
    })
    public static boolean enableMiscItems = false;

}
