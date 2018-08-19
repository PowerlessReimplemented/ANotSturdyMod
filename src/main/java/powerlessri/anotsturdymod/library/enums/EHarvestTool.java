package powerlessri.anotsturdymod.library.enums;

import net.minecraft.util.IStringSerializable;

public enum EHarvestTool implements IStringSerializable {
	PICKAXE("pickaxe"),
	AXE("axe"),
	SHOVEL("shovel"),
	SWORD("sword");
	
	private String name;
	
	private EHarvestTool(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
}
