package powerlessri.anotsturdymod.library.keyboard;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;

public interface KeyCombination {

    /**
     * If the key combination can be considered triggered.
     * <p>
     * In order for the key combination to be triggered, all keys in {@link #getKeys()} must be pressed and all keys in {@link
     * #getRejectedKeys()} must be not pressed.
     * </p>
     * <p>
     * Implementation might want to override this method for better readability and (possibly) performance.
     * </p>
     *
     * @see #getKeys()
     * @see #getRejectedKeys()
     */
    default boolean isTriggered() {
        for (Key key : this.getKeys()) {
            if (!key.isKeyDown()) {
                return false;
            }
        }
        for (Key rejectedKey : this.getRejectedKeys()) {
            if (rejectedKey.isKeyDown()) {
                return false;
            }
        }
        return true;
    }

    /**
     * The list of keys that must be pressed in order for the combination to be triggered.
     */
    ImmutableList<Key> getKeys();

    /**
     * The base key for the key combination.
     * <p>Should be the first element in {@link #getKeys()}</p>
     */
    @Nonnull
    Key getBaseKey();

    /**
     * The list of keys where cannot be pressed in order for the combination to be triggered.
     */
    ImmutableList<Key> getRejectedKeys();

}
