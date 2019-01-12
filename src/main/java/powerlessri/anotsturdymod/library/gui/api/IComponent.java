package powerlessri.anotsturdymod.library.gui.api;

import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.library.gui.integration.ContextGuiDrawing;

import javax.annotation.Nullable;

public interface IComponent {

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

    int getWidth();

    int getHeight();


    int getActualX();

    int getActualY();

    /**
     * X position of the bottom right corner.
     */
    default int getActualXRight() {
        return getActualX() + getWidth();
    }

    /**
     * Y position of the bottom right corner.
     */
    default int getActualYBottom() {
        return getActualY() + getHeight();
    }

    
    boolean isPointInside(int x, int y);


    int getZIndex();

    void setZIndex(int zIndex);


    boolean isVisible();


    void draw(ContextGuiDrawing event);

}
