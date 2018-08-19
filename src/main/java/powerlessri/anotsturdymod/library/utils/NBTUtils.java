package powerlessri.anotsturdymod.library.utils;

import net.minecraft.crash.CrashReport;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ReportedException;
import powerlessri.anotsturdymod.library.interfaces.IEnumNBTTags;

public class NBTUtils<V> {

	private NBTUtils() {
	}
	
	
	
	public static void setTag(NBTTagCompound tag, IEnumNBTTags<?> data, byte value) {
		if(tag != null) {
			tag.setByte(data.getKey(), value);
		}
	}
	
	public static void setTag(NBTTagCompound tag, IEnumNBTTags<?> data, int value) {
		if(tag != null) {
			tag.setInteger(data.getKey(), value);
		}
	}
	
	public static void setTag(NBTTagCompound tag, IEnumNBTTags<?> data, byte[] value) {
		if(tag != null) {
			tag.setByteArray(data.getKey(), value);
		}
	}
	
	public static void setTag(NBTTagCompound tag, IEnumNBTTags<?> data, int[] value) {
		if(tag != null) {
			tag.setIntArray(data.getKey(), value);
		}
	}
	
	public static void setTag(NBTTagCompound tag, IEnumNBTTags<?> data, short value) {
		if(tag != null) {
			tag.setShort(data.getKey(), value);
		}
	}
	
	public static void setTag(NBTTagCompound tag, IEnumNBTTags<?> data, long value) {
		if(tag != null) {
			tag.setLong(data.getKey(), value);
		}
	}
	
	public static void setTag(NBTTagCompound tag, IEnumNBTTags<?> data, boolean value) {
		if(tag != null) {
			tag.setBoolean(data.getKey(), value);
		}
	}
	
	public static void setTag(NBTTagCompound tag, IEnumNBTTags<?> data, float value) {
		if(tag != null) {
			tag.setFloat(data.getKey(), value);
		}
	}
	
	public static void setTag(NBTTagCompound tag, IEnumNBTTags<?> data, double value) {
		if(tag != null) {
			tag.setDouble(data.getKey(), value);
		}
	}
	
	public static void setTag(NBTTagCompound tag, IEnumNBTTags<?> data, String value) {
		if(tag != null) {
			tag.setString(data.getKey(), value);
		}
	}
	
	public static void setTag(NBTTagCompound tag, IEnumNBTTags<?> data, NBTBase value) {
		if(tag != null) {
			tag.setTag(data.getKey(), value);
		}
	}
	
	
	
	
	public static <T> void buildDefaultTag(NBTTagCompound tag, IEnumNBTTags<T>[] data) {
		if(tag == null) {
			return;
		}

		for(int i = 0; i < data.length; i++) {
			 data[i].setTag(tag, data[i].getDefaultValue());
		}
	}

}
