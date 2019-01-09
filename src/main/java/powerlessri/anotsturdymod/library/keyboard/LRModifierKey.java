package powerlessri.anotsturdymod.library.keyboard;

public enum LRModifierKey implements ModifierKey {

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

}
