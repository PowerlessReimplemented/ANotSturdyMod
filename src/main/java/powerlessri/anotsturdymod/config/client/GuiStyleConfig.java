package powerlessri.anotsturdymod.config.client;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;
import powerlessri.anotsturdymod.varia.Reference;

@LangKey("config.ansm:client.category.guiStyle")
@Config(modid = Reference.MODID, name = Reference.CLIENT_CONFIG_FILE, category = "gui_style")
public class GuiStyleConfig {
    
    public static int gradientBtnSNormal = 0xffffff;

    public static int gradientBtnENormal = 0x939393;

    public static int gradientBtnSHover = 0xb2c8ff;
    
    public static int gradientBtnEHover = 0x6b95ff;
    
    public static int gradientBtnSPressed = 0x6d95ff;
    
    public static int gradientBtnEPressed = 0x0048ff;
    
    public static int gradientBtnTextNormal = 0x000000;
    
    public static int gradientBtnTextDisabled = 0xbababa;
    
}
