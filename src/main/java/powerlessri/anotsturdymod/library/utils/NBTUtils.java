package powerlessri.anotsturdymod.library.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import powerlessri.anotsturdymod.library.tags.IEnumNBTTags;
import powerlessri.anotsturdymod.library.tags.ITagBasedItem;


public class NBTUtils {

    private NBTUtils() {
    }

    public static void setTagEnum(NBTTagCompound tag, IEnumNBTTags<?> data, byte value) {
        if (tag != null) {
            tag.setByte(data.getKey(), value);
        }
    }

    public static void setTagEnum(NBTTagCompound tag, IEnumNBTTags<?> data, int value) {
        if (tag != null) {
            tag.setInteger(data.getKey(), value);
        }
    }

    public static void setTagEnum(NBTTagCompound tag, IEnumNBTTags<?> data, byte[] value) {
        if (tag != null) {
            tag.setByteArray(data.getKey(), value);
        }
    }

    public static void setTagEnum(NBTTagCompound tag, IEnumNBTTags<?> data, int[] value) {
        if (tag != null) {
            tag.setIntArray(data.getKey(), value);
        }
    }

    public static void setTagEnum(NBTTagCompound tag, IEnumNBTTags<?> data, short value) {
        if (tag != null) {
            tag.setShort(data.getKey(), value);
        }
    }

    public static void setTagEnum(NBTTagCompound tag, IEnumNBTTags<?> data, long value) {
        if (tag != null) {
            tag.setLong(data.getKey(), value);
        }
    }

    public static void setTagEnum(NBTTagCompound tag, IEnumNBTTags<?> data, boolean value) {
        if (tag != null) {
            tag.setBoolean(data.getKey(), value);
        }
    }

    public static void setTagEnum(NBTTagCompound tag, IEnumNBTTags<?> data, float value) {
        if (tag != null) {
            tag.setFloat(data.getKey(), value);
        }
    }

    public static void setTagEnum(NBTTagCompound tag, IEnumNBTTags<?> data, double value) {
        if (tag != null) {
            tag.setDouble(data.getKey(), value);
        }
    }

    public static void setTagEnum(NBTTagCompound tag, IEnumNBTTags<?> data, String value) {
        if (tag != null) {
            tag.setString(data.getKey(), value);
        }
    }

    public static void setTagEnum(NBTTagCompound tag, IEnumNBTTags<?> data, NBTBase value) {
        if (tag != null) {
            tag.setTag(data.getKey(), value);
        }
    }

    public static NBTTagCompound combineTags(NBTTagCompound... tags) {
        NBTTagCompound resultTag = new NBTTagCompound();

        for (int i = 0; i < tags.length; i++) {
            resultTag.merge(tags[i]);
        }

        return resultTag;
    }

    public static <T> void buildTagWithDefault(NBTTagCompound tag, IEnumNBTTags<T>[] data) {
        if (tag == null) {
            return;
        }

        for (int i = 0; i < data.length; i++) {
            data[i].setTag(tag, data[i].getDefaultValue());
        }
    }

    public static NBTTagCompound getTagSafe(ItemStack stack) {
        if (stack.getTagCompound() != null) {
            return stack.getTagCompound();
        }

        if (stack.getItem() instanceof ITagBasedItem) {
            return ((ITagBasedItem) stack.getItem()).getDefaultTag();
        }

        return new NBTTagCompound();
    }


    // ================================ //


    public static final String DIMENSION = "dimension";
    public static final String X = "x";
    public static final String Y = "y";
    public static final String Z = "z";

    public static int readDimension(NBTTagCompound tag) {
        return tag.getInteger(DIMENSION);
    }

    public static void writeDimension(NBTTagCompound tag, int dimension) {
        tag.setInteger(DIMENSION, dimension);
    }


    public static BlockPos readBlockPos(NBTTagCompound tag) {
        int x = tag.getInteger(X);
        int y = tag.getInteger(Y);
        int z = tag.getInteger(Z);
        return new BlockPos(x, y, z);
    }

    public static void writeBlockPos(NBTTagCompound tag, BlockPos pos) {
        writeBlockPos(tag, pos.getX(), pos.getY(), pos.getZ());
    }

    public static void writeBlockPos(NBTTagCompound tag, int x, int y, int z) {
        tag.setInteger(X, x);
        tag.setInteger(Y, y);
        tag.setInteger(Z, z);
    }

}
