package powerlessri.anotsturdymod.library.gui.integration;

public class ContextGuiUpdate {

    public long time;
    public int mouseX;
    public int mouseY;

    public ContextGuiUpdate(long time, int mouseX, int mouseY) {
        this.time = time;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    /**
     * Time in ticks since the gui was created.
     * @return
     */
    public long getTime() {
        return time;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }
}
