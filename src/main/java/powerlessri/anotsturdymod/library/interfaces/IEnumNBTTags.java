package powerlessri.anotsturdymod.library.interfaces;

import powerlessri.anotsturdymod.library.enums.EDataType;

/**
 * Support for all NBT-related classes 
 * 
 * @param <T> The type of value the tag would have
 */
public interface IEnumNBTTags<T> {
    
    default EDataType getType () {
    	return EDataType.UNKOWN;
    };
    
    String getKey();
    T getDefaultValue();
    
}
