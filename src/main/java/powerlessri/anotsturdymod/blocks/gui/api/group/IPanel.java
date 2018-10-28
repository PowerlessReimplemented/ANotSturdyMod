package powerlessri.anotsturdymod.blocks.gui.api.group;

import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.blocks.gui.api.IComponent;
import powerlessri.anotsturdymod.blocks.gui.api.IContainer;

import javax.annotation.Nullable;
import java.util.List;

public interface IPanel extends IContainer {
    
    @Nullable
    GuiScreen getGui();
    
    List<IComponent> getComponents();

    void addComponent(IComponent component);

    void addComponent(IComponent component, int zIndex);

    IComponent deleteComponent(int id);
    
    void setPosition(int x, int y);
    
    @Override
    int getX();
    
    @Override
    int getY();
    
    default void draw() {
        draw(getX(), getY());
    }
    
    void draw(int x, int y);
    
}
