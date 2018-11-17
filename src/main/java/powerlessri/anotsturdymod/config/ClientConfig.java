package powerlessri.anotsturdymod.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Type;
import powerlessri.anotsturdymod.varia.Reference;

@Config(modid = Reference.MODID, name = Reference.NAME + "Config.client", type = Type.INSTANCE)
public class ClientConfig {
    
    @Comment("Color that will be used on start of a gradient button")
    public static int gradientBtnSNormal = 0xffffff;

    @Comment("Color that will be used on end of a gradient button")
    public static int gradientBtnENormal = 0x939393;
    
    public static int gradientBtnSHover = 0xb2c8ff;
    
    public static int gradientBtnEHover = 0x6b95ff;
    
    public static int gradientBtnSPressed = 0x6d95ff;
    
    public static int gradientBtnEPressed = 0x0048ff;
    
}
