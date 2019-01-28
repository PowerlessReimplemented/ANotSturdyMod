package powerlessri.anotsturdymod.library.keyboard;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;

/**
 * <p>
 * All key combinations in format of {@link IModifierKey} + some other {@link IKey}'s should implement this interface instead. For example key
 * combo of Ctrl+C, Ctrl+V.
 * </p>
 */
public interface IUtilKeyCombination extends IKeyCombination {

    @Nonnull
    @Override
    IModifierKey getBaseKey();

    /**
     * All {@link #getKeys()} except {@link #getBaseKey()}.
     */
    ImmutableList<IKey> getNonBaseKeys();

}
