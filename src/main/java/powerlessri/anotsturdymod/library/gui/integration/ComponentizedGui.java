package powerlessri.anotsturdymod.library.gui.integration;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import powerlessri.anotsturdymod.library.gui.api.EMouseButton;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.api.IContainer;
import powerlessri.anotsturdymod.library.gui.simpleimpl.ComponentRoot;
import powerlessri.anotsturdymod.library.gui.simpleimpl.EventManager;
import powerlessri.anotsturdymod.varia.general.GuiUtils;

import java.io.IOException;

// This is purposely spelled wrong, people should get what it means.
// In case somebody doesn't get it:
/**
 * An bridge between the component-tree based GUI system and vanilla's GUI system.
 */
public class ComponentizedGui extends GuiContainer {

    /**
     * Number of updates since this GUI was created.
     * Increases every time {@link #updateScreen()} is called.
     */
    public long updates;
    
    /**
     * System time when this GUI was initialized.
     */
    public long timeCreated;
    
    protected ComponentRoot root;
    protected ImmutableList<IContainer<IComponent>> windows;
    
    private GuiDrawBackgroundEvent redrawEvent;

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
        
        timeCreated = System.currentTimeMillis();
        root = new ComponentRoot(this, windows);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();

        redrawEvent.guiTime = System.currentTimeMillis() - timeCreated;
        redrawEvent.particleTicks = partialTicks;
        redrawEvent.mouseX = mouseX;
        redrawEvent.mouseY = mouseY;
        
        root.draw(redrawEvent);
        GuiUtils.useTextureGLStates(); // In case somebody forget to call GlStateManager.enableTexture2D()

        renderHoveredToolTip(mouseX, mouseY);
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
        EMouseButton button = getMouseButton(mouseButton);
        
        eventManager.emitClickedDragging(mouseX, mouseY, button, timePressed);
        eventManager.emitHoveringDragging(mouseX, mouseY, button, timePressed);
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
