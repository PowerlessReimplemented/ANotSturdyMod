package powerlessri.anotsturdymod.utils;

import org.apache.http.MethodNotSupportedException;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * This interface IHasModel should <strong>NOT</strong> be implemented! This is a base interface.
 * Choose one (or some) of them from rest of interfaces starts with "<i>IHas</i>" 
 * inside <i>powerlessri.anotsturdymod.utils</i>
 * 
 * @author  hnOsmium0001
 *
 */
public interface IHasModel {
	
	void registerModel() throws MethodNotSupportedException;
	void registerModel(int meta) throws MethodNotSupportedException;
	void registerModel(ItemStack subitem) throws MethodNotSupportedException;
	
}
