package powerlessri.anotsturdymod.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Type;
import powerlessri.anotsturdymod.varia.Reference;

@Config(modid = Reference.MODID, name = Reference.NAME + "Config.client", type = Type.INSTANCE)
public class ClientConfig {
    
    @Comment("Color that will be used on start of a gradient button")
    public static int gradientBtnStart = 0xffffff;

    @Comment("Color that will be used on end of a gradient button")
    public static int gradientBtnEnd = 0x101010;
    
}
