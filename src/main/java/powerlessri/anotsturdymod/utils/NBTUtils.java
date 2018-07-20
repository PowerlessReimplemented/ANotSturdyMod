package powerlessri.anotsturdymod.utils;

import net.minecraft.nbt.NBTTagCompound;
import powerlessri.anotsturdymod.utils.handlers.interfaces.IEnumNBTTags;

public class NBTUtils {
    
    private NBTUtils() {
    }
    
    
    /** Set tag's value to given data's default value */
    public static void setTag(NBTTagCompound tag, IEnumNBTTags<?> data) {
        setTag(tag, data, data.getDefaultValue());
    }
    
    /** Set tag's value to the given value */
    public static void setTag(NBTTagCompound tag, IEnumNBTTags<?> data, Object to) {
        switch(data.getType()) {
            case BYTE:
                tag.setByte(data.getKey(), (Byte) to);
                break;
            case SHORT:
                tag.setShort(data.getKey(), (Short) to);
                break;
            case INT:
                tag.setInteger(data.getKey(), (Integer) to);
                break;
            case LONG:
                tag.setLong(data.getKey(), (Long) to);
                break;
            
            case FLOAT:
                tag.setFloat(data.getKey(), (Float) to);
                break;
            case DOUBLE:
                tag.setDouble(data.getKey(), (Double) to);
                break;
            
            case STRING:
                tag.setString(data.getKey(), (String) to);
                break;
            
            default: break;
        }
    }
    
}
