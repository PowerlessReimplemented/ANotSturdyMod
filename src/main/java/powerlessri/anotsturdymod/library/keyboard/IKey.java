package powerlessri.anotsturdymod.library.keyboard;

import org.lwjgl.input.Keyboard;

public interface IKey {

    /**
     * The the key code used by {@link Keyboard}.
     */
    int getKeyCode();

    /**
     * If the key is pressed by player or not.
     * <p>Implementations should use {@link org.lwjgl.input.Keyboard#isKeyDown(int)} when overriding</p>
     *
     * @see #getKeyCode()
     */
    default boolean isKeyDown() {
        return Keyboard.isKeyDown(this.getKeyCode());
    }

}
