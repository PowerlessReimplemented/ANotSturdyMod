package powerlessri.anotsturdymod.library.gui.simpleimpl.widget;

/**
 * A rectangular matrix of slots that is purely denotational.
 */
public class SlotsDisplay extends Slots {
    
    public SlotsDisplay(int relativeX, int relativeY, int slotsHorizontal, int slotsVertical) {
        super(relativeX, relativeY, slotsHorizontal, slotsVertical);
    }

    
    @Override
    public void drawHoveringIcon(DrawHoveringIconEvent event, int x, int y) {
    }

}
