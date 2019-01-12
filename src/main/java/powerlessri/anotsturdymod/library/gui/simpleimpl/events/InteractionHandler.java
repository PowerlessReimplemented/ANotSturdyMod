package powerlessri.anotsturdymod.library.gui.simpleimpl.events;

import net.minecraft.util.EnumActionResult;
import powerlessri.anotsturdymod.library.gui.api.EEventType;
import powerlessri.anotsturdymod.library.gui.api.EMouseButton;
import powerlessri.anotsturdymod.library.gui.api.IComponent;

/**
 * Defines an event receiver component.
 */
public interface InteractionHandler extends IComponent {

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
     * When player clicked the component, triggers every time the GUI updates as long as player hold his mouse button.
     * 
     * <p>Note that if this event was triggered, {@link #onHovering(int, int)} will not trigger.</p>
     * <p>This event will not bubble up.</p>
     */
    void onClickedDragging(int mouseX, int mouseY, EMouseButton button, long timePressed);

    /**
     * Triggers when player's mouse is hovering above the component and the mouse is pressed.
     * 
     * <p>Only triggers when {@link #onClickedDragging(int, int, EMouseButton, long)} didn't.</p>
     * <p>This event will not bubble up.</p>
     */
    void onHovering(int mouseX, int mouseY);
    
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


    /**
     * If returns {@code false}, events will be ignored.
     * Otherwise all events will be emitted.
     */
    boolean doesReceiveEvents();

}
