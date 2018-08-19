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
    			NBTUtils.setTag(tag, this, (boolean) value);
    			break;
    		case BYTE:
    			NBTUtils.setTag(tag, this, (byte) value);
    			break;
    		case INT:
    			NBTUtils.setTag(tag, this, (int) value);
    			break;
    		case BYTE_ARRAY:
    			NBTUtils.setTag(tag, this, (byte[]) value);
    			break;
    		case INT_ARRAY:
    			NBTUtils.setTag(tag, this, (int[]) value);
    			break;
    		case SHORT:
    			NBTUtils.setTag(tag, this, (short) value);
    			break;
    		case LONG:
    			NBTUtils.setTag(tag, this, (long) value);
    			break;
    		case FLOAT:
    			NBTUtils.setTag(tag, this, (float) value);
    			break;
    		case DOUBLE:
    			NBTUtils.setTag(tag, this, (double) value);
    			break;
    		case STRING:
    			NBTUtils.setTag(tag, this, (String) value);
    			break;
    		case NBT_TAG:
    			NBTUtils.setTag(tag, this, (NBTBase) value);
    			break;
    		}
    	} catch(Throwable e) {
    		CrashReport crashReport = CrashReport.makeCrashReport(e,
    				e instanceof UnexpectedTypeException ? "Didn't specify tag type" :
    				e instanceof ClassCastException ? "Specifyed type mismatched with real type" :
    				"Unkown issue on default setTag method");
    		
    		throw new ReportedException(crashReport);
    	}
    }
    
}
