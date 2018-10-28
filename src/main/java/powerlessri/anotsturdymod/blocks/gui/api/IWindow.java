package powerlessri.anotsturdymod.blocks.gui.api;

import powerlessri.anotsturdymod.varia.general.EPlaneDirection;

import java.util.List;

public interface IWindow {
    
    List<IWindow> getAttatchedWindowns();

    // May get deleted
    void attachAside(IWindow window, EPlaneDirection side, EPlaneDirection lane);
    
    
    List<IComponent> getComponents();
    
    int getX();
    
    int getY();
    
}
