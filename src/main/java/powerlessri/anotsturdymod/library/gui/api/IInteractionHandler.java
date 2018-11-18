package powerlessri.anotsturdymod.library.gui.api;

import net.minecraft.util.EnumActionResult;

/**
 * Pre-made type, combination of {@link IComponent}
 */
public interface IInteractionHandler extends IComponent {

    /**
     * Triggers when player clicked on the component.
     * <p>
     * Conditions:
     * <ol>
     * <li>{@link #isPointInside(int, int)} of mouse position returns {@code true}.</li>
     * <li>{@link #isLeafComponent()} returns {@code true}</li>
     * <li>{@link #doesReceiveEvents()} return {@code true}</li>
     * </ol>
     * </p>
     * <p>Return {@link EnumActionResult#FAIL} to cancel the bubble up process</p>
     */
    EnumActionResult onClicked(int mouseX, int mouseY, EMouseButton button, EEventType type);

    /**
     * Triggers when player released the mouse button. Called on last clicked component.
     *
     * <p>See {@link #onClicked(int, int, EMouseButton, EEventType)} for more information.</p>
     * <p>
     * Note: If the component is clicked when it's not disabled, and gets disabled, it will still receive this event.
     * If this method returns {@link EnumActionResult#FAIL} when the parameter {@code type} is ORIGINAL,
     * the event bubbling process will be canceled.
     * </p>
     */
    EnumActionResult onReleased(int mouseX, int mouseY, EMouseButton button, EEventType type);


    default boolean doesReceiveEvents() {
        return getDisplay() != EDisplayMode.DISABLED;
    }

}
