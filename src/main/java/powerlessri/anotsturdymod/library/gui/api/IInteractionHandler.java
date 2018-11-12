package powerlessri.anotsturdymod.library.gui.api;

/**
 * Pre-made type, combination of {@link IComponent}
 */
public interface IInteractionHandler extends IComponent {

    void onClicked(int mouseX, int mouseY, EMouseButton button, EEventType type);

    void onReleased(int mouseX, int mouseY, EMouseButton button, EEventType type);

}
