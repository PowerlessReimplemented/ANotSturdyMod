package powerlessri.anotsturdymod.utils.handlers.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.MethodNotSupportedException;

public interface IHasVariants extends IHasModel{
	
	final List<String> VARIANT_NAMES = new ArrayList<String>();
	
	@Override
	void registerModel();
	
	@Override
	void registerModel(int meta) throws MethodNotSupportedException;
	
	void addVariant(String unlocalized_name);
	void removeVariant(String unlocalized_name);
	void removeVariant(int index);
	
	int getVariantAmount();
	boolean contains(String unlocalized_name);
	
	String getVariant(int index);
	
}
