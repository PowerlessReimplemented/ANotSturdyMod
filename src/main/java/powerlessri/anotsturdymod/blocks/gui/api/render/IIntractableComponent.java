package powerlessri.anotsturdymod.blocks.gui.api.render;

import powerlessri.anotsturdymod.blocks.gui.api.IComponent;

/**
 * Pre-made type, combination of {@link IComponent} and {@link IDisplayEntry}
 */
public interface IIntractableComponent extends IComponent, IDisplayEntry {

    void onClicked(int mouseX, int mouseY, EEventType type);

    void onReleased(int mouseX, int mouseY);

}
