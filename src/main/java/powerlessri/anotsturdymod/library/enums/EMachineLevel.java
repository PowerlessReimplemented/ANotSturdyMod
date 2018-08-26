package powerlessri.anotsturdymod.library.enums;

import net.minecraft.util.IStringSerializable;


public enum EMachineLevel implements IStringSerializable {

    BASIC("basic"), ADVANCED("advanced"), ULTIMATE("ultimate");

    private String name;

    private EMachineLevel(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
