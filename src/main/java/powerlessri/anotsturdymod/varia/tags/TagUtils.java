package powerlessri.anotsturdymod.varia.tags;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TagUtils {

    private static final String DIMENSION = "dimension";
    private static final String X = "x";
    private static final String Y = "y";
    private static final String Z = "z";

    public static int readDimension(CompoundNBT tag) {
        return tag.getInt(DIMENSION);
    }

    public static void writeDimension(CompoundNBT tag, int dimension) {
        tag.putInt(DIMENSION, dimension);
    }


    public static BlockPos readBlockPos(CompoundNBT tag) {
        return readBlockPos(tag, "");
    }

    public static BlockPos readBlockPos(CompoundNBT tag, String baseKey) {
        int x = tag.getInt(baseKey + X);
        int y = tag.getInt(baseKey + Y);
        int z = tag.getInt(baseKey + Z);
        return new BlockPos(x, y, z);
    }

    public static void writeBlockPos(CompoundNBT tag, BlockPos pos) {
        writeBlockPos(tag, "", pos);
    }

    public static void writeBlockPos(CompoundNBT tag, String baseKey, BlockPos pos) {
        writeBlockPos(tag, baseKey, pos.getX(), pos.getY(), pos.getZ());
    }

    public static void writeBlockPos(CompoundNBT tag, String baseKey, int x, int y, int z) {
        tag.putInt(baseKey + X, x);
        tag.putInt(baseKey + Y, y);
        tag.putInt(baseKey + Z, z);
    }


    public static final Supplier<? extends INBTSerializable<INBT>> NULL_NBT_SERIALIZER_SUPPLIER = () -> null;

    public static <T extends INBTSerializable<INBT>> ListNBT toNBTList(List<T> list) {
        ListNBT tagList = new ListNBT();
        for (T item : list) {
            INBT tag = item.serializeNBT();
            tagList.add(tag);
        }
        return tagList;
    }


    public static <T extends INBTSerializable<INBT>> List<T> toRegularList(ListNBT tagList, @Nonnull Supplier<T> rawObjectFactory) {
        List<T> base = new ArrayList<>(tagList.size());
        for (int i = 0; i < tagList.size(); i++) {
            base.add(rawObjectFactory.get());
        }

        toRegularList(base, tagList, rawObjectFactory);
        return base;
    }

    public static <T extends INBTSerializable<INBT>> List<T> toRegularList(List<T> base, ListNBT tagList, @Nullable Supplier<T> rawObjectFactory) {
        for (int i = 0; i < base.size(); i++) {
            T item = base.get(i);

            if (item == null) {
                if (rawObjectFactory != null) {
                    item = rawObjectFactory.get();
                    base.set(i, item);

                    // In case rawObjectSupplier.get() returns null too
                    if (item == null) {
                        continue;
                    }
                } else {
                    // Replacement not available
                    continue;
                }
            }
            item.deserializeNBT(tagList.get(i));
        }

        return base;
    }


    public static CompoundNBT combineTags(CompoundNBT... tags) {
        CompoundNBT resultTag = new CompoundNBT();
        for (CompoundNBT tag : tags) {
            resultTag.merge(tag);
        }
        return resultTag;
    }

}
