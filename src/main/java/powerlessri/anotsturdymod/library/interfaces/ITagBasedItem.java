package powerlessri.anotsturdymod.library.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface ITagBasedItem {

	@Deprecated
	void updateItemTag(ItemStack stack);

	/** Get the default NBT tag for this item */
	NBTTagCompound getDefaultTag();

	/**
	 * Set the tag of the given {@code stack} to the default state, but keep the
	 * ones that already exists. <br />
	 * 
	 * <b>NOTE</b>: this method should have situations that the stack doesn't have a
	 * NBT tag (null).
	 */
	default void buildDefaultTag(ItemStack stack) {
		if (stack.getTagCompound() != null) {
			stack.getTagCompound().merge(this.getDefaultTag());
		} else {
			stack.setTagCompound(this.getDefaultTag());
		}
	}

	/** Resets the whole tag of the given {@code stack} */
	default void resetDefaultTag(ItemStack stack) {
		stack.setTagCompound(null);
		this.buildDefaultTag(stack);
	}

}
