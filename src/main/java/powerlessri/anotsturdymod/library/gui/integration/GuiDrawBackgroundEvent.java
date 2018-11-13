package powerlessri.anotsturdymod.library.gui.integration;

public class GuiDrawBackgroundEvent {
    
    long guiTime;
    
    int mouseX;
    int mouseY;
    float particleTicks;


    public long getTime() {
        return guiTime;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public float getParticleTicks() {
        return particleTicks;
    }
    
}
