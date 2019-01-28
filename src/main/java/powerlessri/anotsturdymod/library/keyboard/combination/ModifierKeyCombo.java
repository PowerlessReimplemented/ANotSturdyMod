package powerlessri.anotsturdymod.library.keyboard.combination;

import com.google.common.collect.ImmutableList;
import powerlessri.anotsturdymod.library.keyboard.IKey;
import powerlessri.anotsturdymod.library.keyboard.IModifierKey;
import powerlessri.anotsturdymod.library.keyboard.IUtilKeyCombination;

import javax.annotation.Nonnull;

public class ModifierKeyCombo implements IUtilKeyCombination {

    private IModifierKey baseKey;
    private IKey secondaryKey;

    private ImmutableList<IKey> keys;
    private ImmutableList<IKey> nonBaseKeys;
    private ImmutableList<IKey> rejectedKeys;

    public ModifierKeyCombo(@Nonnull IModifierKey baseKey, @Nonnull IKey secondaryKey, IKey... rejectedKeys) {
        this.baseKey = baseKey;
        this.secondaryKey = secondaryKey;
        this.keys = ImmutableList.of(baseKey, secondaryKey);
        this.nonBaseKeys = ImmutableList.of(secondaryKey);
        this.rejectedKeys = ImmutableList.copyOf(rejectedKeys);
    }

    @Override
    public boolean isTriggered() {
        return this.baseKey.isKeyDown() && this.secondaryKey.isKeyDown();
    }

    @Nonnull
    @Override
    public IModifierKey getBaseKey() {
        return this.baseKey;
    }

    @Override
    public ImmutableList<IKey> getNonBaseKeys() {
        return this.nonBaseKeys;
    }

    @Override
    public ImmutableList<IKey> getKeys() {
        return this.keys;
    }

    @Override
    public ImmutableList<IKey> getRejectedKeys() {
        return this.rejectedKeys;
    }

}
