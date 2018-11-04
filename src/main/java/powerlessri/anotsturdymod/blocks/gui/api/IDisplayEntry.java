package powerlessri.anotsturdymod.blocks.gui.api;

public interface IDisplayEntry {

    EDisplayMode getDisplay();

    void draw();
    
    default void tryDraw() {
        if (getDisplay() != EDisplayMode.NONE) {
            draw();
        }
    }

}
