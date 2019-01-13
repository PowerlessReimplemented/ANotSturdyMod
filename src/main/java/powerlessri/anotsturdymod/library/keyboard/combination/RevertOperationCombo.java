package powerlessri.anotsturdymod.library.keyboard.combination;

import com.google.common.collect.ImmutableList;
import powerlessri.anotsturdymod.library.keyboard.Key;
import powerlessri.anotsturdymod.library.keyboard.ModifierKey;
import powerlessri.anotsturdymod.library.keyboard.UtilKeyCombination;
import powerlessri.anotsturdymod.library.keyboard.keys.LRModifierKey;

import javax.annotation.Nonnull;

/**
 * Key combination to revert an {@link UtilKeyCombination} with shift key down.
 */
public class RevertOperationCombo implements UtilKeyCombination {

    private UtilKeyCombination operation;
    private ImmutableList<Key> keys;

    public RevertOperationCombo(UtilKeyCombination operation) {
        this.operation = operation;
        this.keys = ImmutableList.<Key>builder()
                .add(LRModifierKey.SHIFT)
                .addAll(operation.getKeys())
                .build();
    }


    @Override
    public boolean isTriggered() {
        return LRModifierKey.SHIFT.isKeyDown() && this.operation.isTriggered();
    }

    @Override
    public ImmutableList<Key> getKeys() {
        return this.keys;
    }

    @Override
    public ImmutableList<Key> getNonBaseKeys() {
        return this.operation.getKeys();
    }

    @Nonnull
    @Override
    public ModifierKey getBaseKey() {
        return LRModifierKey.SHIFT;
    }

    @Override
    public ImmutableList<Key> getRejectedKeys() {
        return this.operation.getRejectedKeys();
    }

    public UtilKeyCombination getOriginalOperation() {
        return this.operation;
    }

}
