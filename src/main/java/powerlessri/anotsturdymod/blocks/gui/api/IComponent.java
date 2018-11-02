package powerlessri.anotsturdymod.blocks.gui.api;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

import javax.annotation.Nullable;

public interface IComponent {
    
    void initialize(GuiScreen gui, IComponent parent);

    /**
     * @return The GUI which this component belongs to
     */
    GuiScreen getGui();
    
    /**
     * @return Parent component of itself, or {@code null} when it's a root component already
     */
    @Nullable
    IComponent getParentComponent();
    
    default boolean isRootComponent() {
        return getParentComponent() == null;
    }
    
    
    int getId();

    int setId(int id);
    

    /**
     * Get X position relative to top-left corner of parent component.
     * If it has no parent component, this represents a absolute X position.
     */
    int getX();

    /**
     * Get Y position relative to top-left corner of parent component.
     * If it has no parent component, this represents a absolute X position.
     */
    int getY();
    
    
    int getAbsoluteX();
    
    int getAbsoluteY();

    void resetAbsolutePosition(int x, int y);
    
    
    int getZIndex();

    void setZIndex(int zIndex);
    
    void draw();
    
}
