package powerlessri.anotsturdymod.world.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class AbstractSubStorage implements IStringSerializable, INBTSerializable<CompoundNBT> {

    protected WorldSavedData parentData;

    public AbstractSubStorage(WorldSavedData parentData) {
        this.parentData = parentData;
    }

    public void readFromNBT(CompoundNBT tag) {
    }

    public void writeToNBT(CompoundNBT tag) {
    }

    @Override
    public void deserializeNBT(CompoundNBT tag) {
        readFromNBT(tag);
    }

    /**
     * Deserialize data from the parent tag. <br /><br />
     * <p>
     * Equivalent to get actual tag: {@code tag.getCompoundTag(getName())} <br />
     * and then call AbstractSubStorage#deserializeNBT.
     *
     * @param tag The parent of actual data tag
     */
    public void deserializeParentNBT(CompoundNBT tag) {
        deserializeNBT(tag.getCompound(getName()));
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        writeToNBT(tag);
        return tag;
    }

    /**
     * Write data compound to another tag. <br />
     * Equivalent to {@code tag.setTag(getName(), serializeNBT())}.
     *
     * @param tag Data compound will be written into this tag
     */
    // Sorry for the trash method name...
    public void serializeParentNBT(CompoundNBT tag) {
        tag.put(getName(), serializeNBT());
    }

}
