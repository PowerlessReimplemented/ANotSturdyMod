package powerlessri.anotsturdymod.library.enums;

import powerlessri.anotsturdymod.library.INumberSerializable;


public enum EHarvestLevel implements INumberSerializable {
    WOODEN(0), STONE(1), IRON(2), DIAMOND(2);

    private int number;

    private EHarvestLevel(int numericalId) {
        this.number = numericalId;
    }

    @Override
    public int numerical() {
        return this.number;
    }

}
