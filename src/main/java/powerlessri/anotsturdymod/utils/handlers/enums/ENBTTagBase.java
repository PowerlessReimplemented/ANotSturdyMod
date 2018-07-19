package powerlessri.anotsturdymod.utils.handlers.enums;

import net.minecraft.nbt.NBTTagCompound;

public enum ENBTTagBase {
    
    EXAMPLE_STR("TagKeyStr", "this is a string"),
    EXAMPLE_INT("TagKeyInt", 0, 100);
    
    private EDataType type;
    private String key;
    private String defaultStr;
    private int defaultVal;
    
    private ENBTTagBase(String key, String defaultVal) {
        this.type = EDataType.STRING;
        this.key = key;
        this.defaultStr = defaultVal;
    }
    private ENBTTagBase(String key, int defaultVal, int max) {
        this.type = EDataType.INT;
        this.key = key;
        this.defaultVal = defaultVal;
    }
    
    public static void resetTag(NBTTagCompound tag, ENBTTagBase data) {
        switch(data.type) {
            case BYTE:
                tag.setByte(data.key, (byte) data.defaultVal);
                break;
            case INT:
                tag.setInteger(data.key, data.defaultVal);
                break;
            case STRING:
                tag.setString(data.key, data.defaultStr);
                break;
            
            default: break;
        }
    }
    
}
