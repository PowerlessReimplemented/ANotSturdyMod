package powerlessri.anotsturdymod.library.keyboard.keys;

import com.google.common.base.MoreObjects;
import net.minecraft.util.IStringSerializable;
import org.lwjgl.input.Keyboard;
import powerlessri.anotsturdymod.library.keyboard.ModifierKey;

public enum NormalModifierKey implements ModifierKey, IStringSerializable {

    LCTRL("Left Control", "LCTRL", Keyboard.KEY_LCONTROL),
    RCTRL("Right Control", "RCTRL", Keyboard.KEY_RCONTROL),

    LSHIFT("Left Shift", "LSHIFT", Keyboard.KEY_LSHIFT),
    RSHIFT("Right Shift", "RSHIFT", Keyboard.KEY_RSHIFT),

    LALT("Left Alt", "LMENU", Keyboard.KEY_LMENU),
    RALT("Right Alt", "RMENU", Keyboard.KEY_RMENU),

    ENTER("Enter", "RETURN", Keyboard.KEY_RETURN),
    BACKSPACE("Backspace", "BACK", Keyboard.KEY_BACK),
    TAB("Tab", "TAB", Keyboard.KEY_TAB),
    ESCAPE("Escape", "ESC", Keyboard.KEY_ESCAPE),

    HOME("Home", "HOME", Keyboard.KEY_HOME),
    END("End", "END", Keyboard.KEY_END),
    PAGE_UP("Page Up", "PRIOR", Keyboard.KEY_PRIOR),
    PAGE_DOWN("Page Down", "NEXT", Keyboard.KEY_NEXT),
    INSERT("Insert", "INS", Keyboard.KEY_INSERT),
    DELETE("Delete", "DEL", Keyboard.KEY_DELETE);


    public final String name;
    public final String code;
    public final int keyCode;

    private NormalModifierKey(String name, String code, int keyCode) {
        this.name = name;
        this.code = code;
        this.keyCode = keyCode;
    }


    @Override
    public int getKeyCode() {
        return this.keyCode;
    }

    @Override
    public boolean isCollectorOf(ModifierKey other) {
        return false;
    }

    @Override
    public boolean isPartOf(ModifierKey parent) {
        return parent.isCollectorOf(this);
    }

    @Override
    public boolean isRelatedTo(ModifierKey other) {
        return this.isPartOf(other);
    }

    @Override
    public boolean hasSubKeys() {
        return false;
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
                .add("keyCode", keyCode)
                .toString();
    }

}
