package powerlessri.anotsturdymod.library.keyboard.keys;

import com.google.common.base.MoreObjects;
import net.minecraft.util.IStringSerializable;
import powerlessri.anotsturdymod.library.keyboard.ModifierKey;

public enum LRModifierKey implements ModifierKey, IStringSerializable {

    CTRL("Control", "CTRL", NormalModifierKey.LCTRL, NormalModifierKey.RCTRL),
    SHIFT("Shift", "SHIFT", NormalModifierKey.LSHIFT, NormalModifierKey.RSHIFT),
    ALT("Alt", "MENU", NormalModifierKey.LALT, NormalModifierKey.RALT);


    public final String name;
    public final String code;
    public final ModifierKey leftKey;
    public final ModifierKey rightKey;

    private LRModifierKey(String name, String code, ModifierKey leftKey, ModifierKey rightKey) {
        this.name = name;
        this.code = code;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
    }


    @Override
    public boolean isKeyDown() {
        return this.leftKey.isKeyDown() || this.rightKey.isKeyDown();
    }

    @Override
    public int getKeyCode() {
        return 0;
    }

    @Override
    public boolean isCollectorOf(ModifierKey other) {
        return this.leftKey == other || this.rightKey == other;
    }

    @Override
    public boolean isPartOf(ModifierKey parent) {
        return parent.isCollectorOf(this);
    }

    @Override
    public boolean isRelatedTo(ModifierKey other) {
        return this.isCollectorOf(other) || this.isPartOf(other);
    }

    @Override
    public boolean hasSubKeys() {
        return true;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("code", code)
                .add("leftKey", leftKey)
                .add("rightKey", rightKey)
                .toString();
    }
}