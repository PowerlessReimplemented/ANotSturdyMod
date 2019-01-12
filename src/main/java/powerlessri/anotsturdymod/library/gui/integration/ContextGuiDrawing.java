package powerlessri.anotsturdymod.library.gui.integration;

public class ContextGuiDrawing {
    
    long guiTime;
    
    int mouseX;
    int mouseY;
    float particleTicks;

    public ContextGuiDrawing(ContextGuiDrawing previousEvent, int mouseX, int mouseY, float particleTicks) {
        this(previousEvent.getTime(), mouseX, mouseY, particleTicks);
    }

    public ContextGuiDrawing(long guiTime, int mouseX, int mouseY, float particleTicks) {
        this.guiTime = guiTime;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.particleTicks = particleTicks;
    }
    

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
