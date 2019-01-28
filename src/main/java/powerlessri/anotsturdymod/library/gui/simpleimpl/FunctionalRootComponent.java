package powerlessri.anotsturdymod.library.gui.simpleimpl;

import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.api.IContainer;

import java.util.List;

public interface FunctionalRootComponent {

    List<IContainer<IComponent>> getComponentTree();

    CursorEventBus getCursorBus();

    GuiEventTrigerer getEventTrigger();

}
