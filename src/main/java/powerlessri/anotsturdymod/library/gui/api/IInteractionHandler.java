package powerlessri.anotsturdymod.library.gui.api;

import javafx.scene.input.MouseButton;

/**
 * Pre-made type, combination of {@link IComponent}
 */
public interface IInteractionHandler extends IComponent {

    void onClicked(int mouseX, int mouseY, MouseButton button, EEventType type);

    void onReleased(int mouseX, int mouseY, MouseButton button, EEventType type);

}
