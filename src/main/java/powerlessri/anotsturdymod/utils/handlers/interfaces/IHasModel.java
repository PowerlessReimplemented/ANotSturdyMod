package powerlessri.anotsturdymod.utils.handlers.interfaces;

import org.apache.http.MethodNotSupportedException;

import net.minecraft.item.ItemStack;

/**
 * This interface IHasModel should <strong>NOT</strong> be implemented! This is a base interface.
 * Choose one (or some) of them from rest of interfaces starts with "<i>IHas</i>" 
 * inside <i>powerlessri.anotsturdymod.utils</i>
 * 
 * @author  hnOsmium0001
 *
 */
public abstract interface IHasModel {
	
	default void registerModel() throws MethodNotSupportedException {
		throw new MethodNotSupportedException("");
	}
	default void registerModel(int meta) throws MethodNotSupportedException {
		throw new MethodNotSupportedException("");
	}
	default void registerModel(ItemStack subitem) throws MethodNotSupportedException {
		throw new MethodNotSupportedException("");
	}
	
}
