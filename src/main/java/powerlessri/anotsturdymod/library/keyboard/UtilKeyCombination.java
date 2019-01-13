package powerlessri.anotsturdymod.library.keyboard;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;

/**
 * <p>
 * All key combinations in format of {@link ModifierKey} + some other {@link Key}'s should implement this interface instead. For example key
 * combo of Ctrl+C, Ctrl+V.
 * </p>
 */
public interface UtilKeyCombination extends KeyCombination {

    @Nonnull
    @Override
    ModifierKey getBaseKey();

    /**
     * All {@link #getKeys()} except {@link #getBaseKey()}.
     */
    ImmutableList<Key> getNonBaseKeys();

}
