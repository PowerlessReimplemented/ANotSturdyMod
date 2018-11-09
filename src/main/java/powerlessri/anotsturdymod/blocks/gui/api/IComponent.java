package powerlessri.anotsturdymod.blocks.gui.api;

import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.blocks.gui.api.group.IContainer;
import powerlessri.anotsturdymod.blocks.gui.api.render.IDisplayEntry;

import javax.annotation.Nullable;

public interface IComponent extends IDisplayEntry {
    
    /* Implementation Note:
     *     The purpose of this method is that so any can component can be moved around to different places.
     */
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

    /**
     * If the component is NOT an instance of {@link IContainer} but IComponent, then it is a leaf component.
     * In other words, it has no sub-components.
     */
    boolean isLeafComponent();

    /**
     * If the component has a parent, then {@code false}, otherwise {@code true}.
     */
    boolean isRootComponent();
    
    
    int getId();

    int setId(int id);
    

    /**
     * Get X position relative to top-left corner of parent component.
     * If it has no parent component, the return the absolute X position.
     */
    int getX();

    /**
     * Get Y position relative to top-left corner of parent component.
     * If it has no parent component, then return the absolute X position.
     */
    int getY();
    
    
    int getAbsoluteX();
    
    int getAbsoluteY();

    /**
     * Used to force-set position. 
     * @deprecated Reason: <br /> Component implementations should automatically calculate absolute position based on it's parent's position.
     */
    void resetAbsolutePosition(int x, int y);
    
    
    int getZIndex();

    void setZIndex(int zIndex);
    
}
