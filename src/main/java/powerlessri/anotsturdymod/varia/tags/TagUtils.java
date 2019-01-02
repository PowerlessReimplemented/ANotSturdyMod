package powerlessri.anotsturdymod.varia.tags;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TagUtils {

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


    public static final Supplier<? extends INBTSerializable<NBTBase>> NULL_NBT_SERIALIZER_SUPPLIER = () -> null;

    public static <T extends INBTSerializable<NBTBase>> NBTTagList toNBTList(List<T> list) {
        NBTTagList tagList = new NBTTagList();
        for (T item : list) {
            NBTBase tag = item.serializeNBT();
            tagList.appendTag(tag);
        }
        return tagList;
    }


    public static <T extends INBTSerializable<NBTBase>> List<T> toRegularList(NBTTagList tagList, @Nonnull Supplier<T> rawObjectFactory) {
        List<T> base = new ArrayList<>(tagList.tagCount());
        for (int i = 0; i < tagList.tagCount(); i++) {
            base.add(rawObjectFactory.get());
        }

        toRegularList(base, tagList, rawObjectFactory);
        return base;
    }

    public static <T extends INBTSerializable<NBTBase>> List<T> toRegularList(List<T> base, NBTTagList tagList, @Nullable Supplier<T> rawObjectFactory) {
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


    public static NBTTagCompound combineTags(NBTTagCompound... tags) {
        NBTTagCompound resultTag = new NBTTagCompound();
        for (NBTTagCompound tag : tags) {
            resultTag.merge(tag);
        }
        return resultTag;
    }

    @Nonnull
    public static NBTTagCompound getOrCreateTag(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        return stack.getTagCompound();
    }

}
