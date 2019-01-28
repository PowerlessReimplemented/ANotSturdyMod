package powerlessri.anotsturdymod.library.gui.simpleimpl;

import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.api.IContainer;

import java.util.List;

public interface IFunctionalRootComponent {

    List<IContainer<IComponent>> getComponentTree();

    ICursorEventBus getCursorBus();

    IGuiEventTrigger getEventTrigger();

}
