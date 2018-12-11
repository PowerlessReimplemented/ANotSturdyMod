package powerlessri.anotsturdymod.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.RequiresMcRestart;
import powerlessri.anotsturdymod.varia.Reference;

@LangKey("config.ansm:ansm_main.category.general")
@Config(modid = Reference.MODID, name = Reference.MAIN_CONFIG_FILE)
public class MainConfig {

    @Comment({
            "If true, many OP items/blocks will be registered into the game.",
            "From ancient ages..."
    })
    @RequiresMcRestart
    public static boolean enableMiscItems = false;

}
