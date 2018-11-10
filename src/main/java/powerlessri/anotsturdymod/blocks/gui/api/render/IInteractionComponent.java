package powerlessri.anotsturdymod.blocks.gui.api.render;

import javafx.scene.input.MouseButton;
import org.lwjgl.input.Mouse;
import powerlessri.anotsturdymod.blocks.gui.api.IComponent;

/**
 * Pre-made type, combination of {@link IComponent} and {@link IDisplayEntry}
 */
public interface IInteractionComponent extends IComponent, IDisplayEntry {

    void onClicked(int mouseX, int mouseY, MouseButton button, EEventType type);

    void onReleased(int mouseX, int mouseY, MouseButton button, EEventType type);

}
