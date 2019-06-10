package powerlessri.anotsturdymod.library.block.base;

import net.minecraft.util.IStringSerializable;

public enum EHarvestTool implements IStringSerializable {
    PICKAXE("pickaxe"),
    AXE("axe"),
    SHOVEL("shovel"),
    SWORD("sword");


    public final String name;

    private EHarvestTool(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }


}
