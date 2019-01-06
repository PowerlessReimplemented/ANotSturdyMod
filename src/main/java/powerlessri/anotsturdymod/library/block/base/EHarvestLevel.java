package powerlessri.anotsturdymod.library.block.base;

import powerlessri.anotsturdymod.varia.general.IIntegerSerializable;

public enum EHarvestLevel implements IIntegerSerializable {

    WOODEN(0),
    STONE(1),
    IRON(2),
    DIAMOND(2);


    public final int level;

    private EHarvestLevel(int numericalId) {
        this.level = numericalId;
    }

    @Override
    public int getInt() {
        return this.level;
    }


}
