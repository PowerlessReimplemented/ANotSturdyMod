package powerlessri.anotsturdymod.varia.general;

import net.minecraft.util.IStringSerializable;

public enum EMachineLevel implements IStringSerializable {

    BASIC("basic"),
    ADVANCED("advanced"),
    ULTIMATE("ultimate");

    public final String name;

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
