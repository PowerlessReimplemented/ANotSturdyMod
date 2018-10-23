package powerlessri.anotsturdymod.world.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class AbstractSubStorage implements IStringSerializable, INBTSerializable<NBTTagCompound> {

    protected WorldSavedData parentData;

    public AbstractSubStorage(WorldSavedData parentData) {
        this.parentData = parentData;
    }


    public void readFromNBT(NBTTagCompound tag) {
    }

    public void writeToNBT(NBTTagCompound tag) {
    }


    @Override
    public void deserializeNBT(NBTTagCompound tag) {
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
    public void deserializeParentNBT(NBTTagCompound tag) {
        deserializeNBT(tag.getCompoundTag(getName()));
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
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
    public void serializeParentNBT(NBTTagCompound tag) {
        tag.setTag(getName(), serializeNBT());
    }

}
