package powerlessri.anotsturdymod.library.keyboard.combination;

import com.google.common.collect.ImmutableList;
import powerlessri.anotsturdymod.library.keyboard.IKey;
import powerlessri.anotsturdymod.library.keyboard.IModifierKey;
import powerlessri.anotsturdymod.library.keyboard.IUtilKeyCombination;
import powerlessri.anotsturdymod.library.keyboard.keys.LRModifierKey;

import javax.annotation.Nonnull;

/**
 * IKey combination to revert an {@link IUtilKeyCombination} with shift key down.
 */
public class RevertOperationCombo implements IUtilKeyCombination {

    private IUtilKeyCombination operation;
    private ImmutableList<IKey> keys;

    public RevertOperationCombo(IUtilKeyCombination operation) {
        this.operation = operation;
        this.keys = ImmutableList.<IKey>builder()
                .add(LRModifierKey.SHIFT)
                .addAll(operation.getKeys())
                .build();
    }


    @Override
    public boolean isTriggered() {
        return LRModifierKey.SHIFT.isKeyDown() && this.operation.isTriggered();
    }

    @Override
    public ImmutableList<IKey> getKeys() {
        return this.keys;
    }

    @Override
    public ImmutableList<IKey> getNonBaseKeys() {
        return this.operation.getKeys();
    }

    @Nonnull
    @Override
    public IModifierKey getBaseKey() {
        return LRModifierKey.SHIFT;
    }

    @Override
    public ImmutableList<IKey> getRejectedKeys() {
        return this.operation.getRejectedKeys();
    }

    public IUtilKeyCombination getOriginalOperation() {
        return this.operation;
    }

}
