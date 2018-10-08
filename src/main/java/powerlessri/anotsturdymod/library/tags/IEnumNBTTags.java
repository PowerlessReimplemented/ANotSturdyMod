package powerlessri.anotsturdymod.library.tags;

import net.minecraft.crash.CrashReport;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ReportedException;
import net.minecraftforge.common.util.Constants;
import powerlessri.anotsturdymod.library.IIntegerSerializable;
import powerlessri.anotsturdymod.library.exceptions.UnexpectedTypeException;
import powerlessri.anotsturdymod.library.utils.NBTUtils;


/**
 * Support for all NBT-related classes
 *
 * @param <T> The type of value the tag would have
 */
public interface IEnumNBTTags<T> {

    public static enum EDataType implements IIntegerSerializable {

        UNKOWN(-1),

        BOOLEAN(Constants.NBT.TAG_BYTE),

        BYTE(Constants.NBT.TAG_BYTE),
        INT(Constants.NBT.TAG_INT),
        SHORT(Constants.NBT.TAG_SHORT),
        LONG(Constants.NBT.TAG_LONG),

        FLOAT(Constants.NBT.TAG_FLOAT),
        DOUBLE(Constants.NBT.TAG_DOUBLE),

        BYTE_ARRAY(Constants.NBT.TAG_BYTE_ARRAY),
        INT_ARRAY(Constants.NBT.TAG_INT_ARRAY),
        LONG_ARRAY(Constants.NBT.TAG_LONG_ARRAY),

        STRING(Constants.NBT.TAG_STRING),

        NBT_TAG(Constants.NBT.TAG_COMPOUND),
        NBT_LIST(Constants.NBT.TAG_LIST);

        public final int id;

        private EDataType(int id) {
            this.id = id;
        }

        @Override
        public int getInt() {
            return this.id;
        }

    }


    default EDataType getType() {
        return EDataType.UNKOWN;
    }

    ;

    String getKey();

    T getDefaultValue();

    default void setTag(NBTTagCompound tag, T value) {
        try {
            switch (this.getType()) {
                case UNKOWN:
                    throw new UnexpectedTypeException();

                case BOOLEAN:
                    NBTUtils.setTagEnum(tag, this, (Boolean) value);
                    break;
                case BYTE:
                    NBTUtils.setTagEnum(tag, this, (Byte) value);
                    break;
                case INT:
                    NBTUtils.setTagEnum(tag, this, (Integer) value);
                    break;
                case BYTE_ARRAY:
                    NBTUtils.setTagEnum(tag, this, (byte[]) value);
                    break;
                case INT_ARRAY:
                    NBTUtils.setTagEnum(tag, this, (int[]) value);
                    break;
                case SHORT:
                    NBTUtils.setTagEnum(tag, this, (Short) value);
                    break;
                case LONG:
                    NBTUtils.setTagEnum(tag, this, (Long) value);
                    break;
                case FLOAT:
                    NBTUtils.setTagEnum(tag, this, (Float) value);
                    break;
                case DOUBLE:
                    NBTUtils.setTagEnum(tag, this, (Double) value);
                    break;
                case STRING:
                    NBTUtils.setTagEnum(tag, this, (String) value);
                    break;
                case NBT_LIST:
                case NBT_TAG:
                    NBTUtils.setTagEnum(tag, this, (NBTBase) value);
                    break;
                case LONG_ARRAY:

                default:
                    break;
            }
        } catch (Throwable e) {
            CrashReport crashReport = CrashReport.makeCrashReport(e,
                    e instanceof UnexpectedTypeException ? "Didn't specify tag type"
                            : e instanceof ClassCastException ? "Specified type mismatched with real type"
                            : "Unkown issue occured on default setTag method");

            throw new ReportedException(crashReport);
        }
    }

}
