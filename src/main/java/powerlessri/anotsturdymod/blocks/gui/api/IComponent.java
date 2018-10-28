package powerlessri.anotsturdymod.blocks.gui.api;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

import javax.annotation.Nullable;

public interface IComponent {
    
    void initialize(Gui gui, IComponent parent);


    /**
     * @return The GUI which this component belongs to
     */
    Gui getGui();
    
    /**
     * @return Parent component of itself, or {@code null} when it's a root component already
     */
    @Nullable
    IComponent getParentComponent();
    
    
    default boolean isRootComponent() {
        return getParentComponent() == null;
    }


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

    /**
     * Set X position relative to top-left corner of parent component.
     * If it has no parent component, this represents a absolute Y position.
     */
    void setX(int x);

    /**
     * Set Y position relative to top-left corner of parent component.
     * If it has no parent component, this represents a absolute Y position.
     */
    void setY(int y);
    
    
    int getZIndex();

    void setZIndex(int zIndex);
    
    void draw();

    /**
     * A backup to {@link #draw()} which allows a custom (x, y) value.
     */
    default void draw(int x, int y) {
        int oldX = getX();
        int oldY = getY();
        
        setX(x);
        setY(y);
        
        draw();
        
        setX(oldX);
        setY(oldY);
    }
    
}
