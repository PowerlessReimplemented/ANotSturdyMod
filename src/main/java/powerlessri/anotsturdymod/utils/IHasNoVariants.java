package powerlessri.anotsturdymod.utils;

import org.apache.http.MethodNotSupportedException;

public interface IHasNoVariants extends IHasModel{
	
	@Override
	void registerModel();
	
	@Override
	void registerModel(int meta) throws MethodNotSupportedException;
	
}
