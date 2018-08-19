package powerlessri.anotsturdymod.library.utils;

import net.minecraft.crash.CrashReport;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ReportedException;
import powerlessri.anotsturdymod.library.interfaces.IEnumNBTTags;

public class NBTUtils<V> {

	private NBTUtils() {
	}
	
	
	
	public static void setTag(NBTTagCompound tag, IEnumNBTTags<?> data, Object value) {
		try {
			setTag(tag, data, value);
		} catch(Throwable e) {
			CrashReport crashReport = CrashReport.makeCrashReport(e, "Incorrect NBT tag key type");
			throw new ReportedException(crashReport);
		}
	}
	
	public static void setTag(NBTTagCompound tag, IEnumNBTTags<Byte> data, byte value) {
		if(tag != null) {
			tag.setByte(data.getKey(), value);
		}
	}
	
	public static void setTag(NBTTagCompound tag, IEnumNBTTags<Integer> data, int value) {
		if(tag != null) {
			tag.setInteger(data.getKey(), value);
		}
	}
	
	public static void setTag(NBTTagCompound tag, IEnumNBTTags<Byte[]> data, byte[] value) {
		if(tag != null) {
			tag.setByteArray(data.getKey(), value);
		}
	}
	
	public static void setTag(NBTTagCompound tag, IEnumNBTTags<Integer[]> data, int[] value) {
		if(tag != null) {
			tag.setIntArray(data.getKey(), value);
		}
	}
	
	public static void setTag(NBTTagCompound tag, IEnumNBTTags<Short> data, short value) {
		if(tag != null) {
			tag.setShort(data.getKey(), value);
		}
	}
	
	public static void setTag(NBTTagCompound tag, IEnumNBTTags<Long> data, long value) {
		if(tag != null) {
			tag.setLong(data.getKey(), value);
		}
	}
	
	public static void setTag(NBTTagCompound tag, IEnumNBTTags<Boolean> data, boolean value) {
		if(tag != null) {
			tag.setBoolean(data.getKey(), value);
		}
	}
	
	public static void setTag(NBTTagCompound tag, IEnumNBTTags<Float> data, float value) {
		if(tag != null) {
			tag.setFloat(data.getKey(), value);
		}
	}
	
	public static void setTag(NBTTagCompound tag, IEnumNBTTags<Double> data, double value) {
		if(tag != null) {
			tag.setDouble(data.getKey(), value);
		}
	}
	
	public static void setTag(NBTTagCompound tag, IEnumNBTTags<String> data, String value) {
		if(tag != null) {
			tag.setString(data.getKey(), value);
		}
	}
	
	public static void setTag(NBTTagCompound tag, IEnumNBTTags<NBTBase> data, NBTBase value) {
		if(tag != null) {
			tag.setTag(data.getKey(), value);
		}
	}
	
	
	
	
	public static void buildDefaultTag(NBTTagCompound tag, IEnumNBTTags<?>[] data) {
		if(tag == null) {
			return;
		}

		for(int i = 0; i < data.length; i++) {
			setTag(tag, data[i], data[i].getDefaultValue());
		}
	}

}
