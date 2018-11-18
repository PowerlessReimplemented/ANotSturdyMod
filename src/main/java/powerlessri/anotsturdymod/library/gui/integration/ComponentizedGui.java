package powerlessri.anotsturdymod.library.gui.integration;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import powerlessri.anotsturdymod.library.gui.api.EMouseButton;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.api.IContainer;
import powerlessri.anotsturdymod.library.gui.immutableimpl.ComponentRoot;
import powerlessri.anotsturdymod.library.gui.immutableimpl.EventManager;
import powerlessri.anotsturdymod.varia.general.GuiUtils;

import java.io.IOException;

public class ComponentizedGui extends GuiContainer {

    /**
     * Number of updates since this GUI was created.
     */
    public long updates;

    /**
     * System time when this GUI was initialized.
     */
    public long timeStarted;
    
    private GuiDrawBackgroundEvent redrawEvent;
    
    protected ComponentRoot root;
    protected ImmutableList<IContainer<IComponent>> windows;


    public ComponentizedGui(Container container) {
        this(container, null);
    }
    
    public ComponentizedGui(Container container, ImmutableList<IContainer<IComponent>> windows) {
        super(container);
        
        this.windows = windows;
        this.redrawEvent = new GuiDrawBackgroundEvent();
    }

    @Override
    public void initGui() {
        super.initGui();
        
        timeStarted = System.currentTimeMillis();
        root = new ComponentRoot(this, windows);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();

        redrawEvent.guiTime = System.currentTimeMillis() - timeStarted;
        redrawEvent.particleTicks = partialTicks;
        redrawEvent.mouseX = mouseX;
        redrawEvent.mouseY = mouseY;
        
        GuiUtils.useTextureGLStates();
        root.draw(redrawEvent);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        updates++;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        root.getEventManager().emitMouseClicked(mouseX, mouseY, getMouseButton(mouseButton));
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int mouseButton, long timePressed) {
        super.mouseClickMove(mouseX, mouseY, mouseButton, timePressed);
        EventManager eventManager = root.getEventManager();
        eventManager.emitClickedDrag(mouseX, mouseY, getMouseButton(mouseButton), timePressed);
        eventManager.emitHoveringDrag(mouseX, mouseY, getMouseButton(mouseButton), timePressed);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        root.getEventManager().emitMouseReleased(mouseX, mouseY, getMouseButton(mouseButton));
    }


    private EMouseButton getMouseButton(int mouseButton) {
        switch (mouseButton) {
            case 0:
                return EMouseButton.PRIMARY;
            case 1: 
                return EMouseButton.MIDDLE;
            case 2:
                return EMouseButton.SECONDARY;

            default:
                return EMouseButton.NONE;
        }
    }
    
}
