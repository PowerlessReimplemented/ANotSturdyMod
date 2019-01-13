package powerlessri.anotsturdymod.library.keyboard.combination;

import powerlessri.anotsturdymod.library.keyboard.keys.LRModifierKey;
import powerlessri.anotsturdymod.library.keyboard.keys.LetterKey;

// TODO detailed javadoc
public final class KeyCombos {

    /**
     * Copy
     */
    public static final ModifierKeyCombo CTRL_C = new ModifierKeyCombo(LRModifierKey.CTRL, LetterKey.C);

    /**
     * Paste
     */
    public static final ModifierKeyCombo CTRL_V = new ModifierKeyCombo(LRModifierKey.CTRL, LetterKey.V);

    /**
     * Cut
     */
    public static final ModifierKeyCombo CTRL_X = new ModifierKeyCombo(LRModifierKey.CTRL, LetterKey.X);

    /**
     * Select all
     */
    public static final ModifierKeyCombo CTRL_A = new ModifierKeyCombo(LRModifierKey.CTRL, LetterKey.A);

    /**
     * Undo
     */
    public static final ModifierKeyCombo CTRL_Z = new ModifierKeyCombo(LRModifierKey.CTRL, LetterKey.Z);

    /**
     * Alternative Redo
     */
    public static final RevertOperationCombo CTRL_SHIFT_Z = new RevertOperationCombo(CTRL_Z);

    /**
     * Redo
     * <p><i>Preferred key combo</i></p>
     */
    public static final ModifierKeyCombo CTRL_Y = new ModifierKeyCombo(LRModifierKey.CTRL, LetterKey.Y);


    /**
     * Special key combination for custom uses.
     */
    public static final ModifierKeyCombo CTRL_SHIFT = new ModifierKeyCombo(LRModifierKey.CTRL, LRModifierKey.SHIFT);

}
