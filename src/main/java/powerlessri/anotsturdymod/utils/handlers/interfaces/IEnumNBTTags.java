package powerlessri.anotsturdymod.utils.handlers.interfaces;

import powerlessri.anotsturdymod.utils.handlers.enums.EDataType;

/**
 * Support for all NBT-related classes 
 * 
 * @param <T> The type of value the tag would have
 */
public interface IEnumNBTTags<T> {
    
    EDataType getType();
    
    String getKey();
    T getDefaultValue();
    
}
