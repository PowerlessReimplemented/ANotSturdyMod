package powerlessri.anotsturdymod.utils.handlers.enums;

import powerlessri.anotsturdymod.utils.handlers.interfaces.INumberSerializable;

public enum EHarvestLevel implements INumberSerializable {
	WOODEN(0),
	STONE(1),
	IRON(2),
	DIAMOND(2);
	
	private int number;
	
	private EHarvestLevel(int numericalId) {
		this.number = numericalId;
	}
	
	@Override
	public int numerical() {
		return 0;
	}
	
}
