package powerlessri.anotsturdymod.library.interfaces;

import net.minecraft.crash.CrashReport;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ReportedException;
import powerlessri.anotsturdymod.library.enums.EDataType;
import powerlessri.anotsturdymod.library.exceptions.UnexpectedTypeException;
import powerlessri.anotsturdymod.library.utils.NBTUtils;

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
    
    default void setTag(NBTTagCompound tag, T value) {
    	try {
    		switch(this.getType()) {
    		case UNKOWN:
    			throw new UnexpectedTypeException();

    		case BOOLEAN:
    			NBTUtils.setTagEnum(tag, this, (boolean) value);
    			break;
    		case BYTE:
    			NBTUtils.setTagEnum(tag, this, (byte) value);
    			break;
    		case INT:
    			NBTUtils.setTagEnum(tag, this, (int) value);
    			break;
    		case BYTE_ARRAY:
    			NBTUtils.setTagEnum(tag, this, (byte[]) value);
    			break;
    		case INT_ARRAY:
    			NBTUtils.setTagEnum(tag, this, (int[]) value);
    			break;
    		case SHORT:
    			NBTUtils.setTagEnum(tag, this, (short) value);
    			break;
    		case LONG:
    			NBTUtils.setTagEnum(tag, this, (long) value);
    			break;
    		case FLOAT:
    			NBTUtils.setTagEnum(tag, this, (float) value);
    			break;
    		case DOUBLE:
    			NBTUtils.setTagEnum(tag, this, (double) value);
    			break;
    		case STRING:
    			NBTUtils.setTagEnum(tag, this, (String) value);
    			break;
    		case NBT_LIST:
    		case NBT_TAG:
    			NBTUtils.setTagEnum(tag, this, (NBTBase) value);
    			break;
    		}
    	} catch(Throwable e) {
    		CrashReport crashReport = CrashReport.makeCrashReport(e,
    				e instanceof UnexpectedTypeException ? "Didn't specify tag type" :
    				e instanceof ClassCastException ? "Specified type mismatched with real type" :
    				"Unkown issue on default setTag method");
    		
    		throw new ReportedException(crashReport);
    	}
    }
    
}
