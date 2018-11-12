package powerlessri.anotsturdymod.library.gui.integration;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import powerlessri.anotsturdymod.library.gui.api.EMouseButton;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.api.IContainer;
import powerlessri.anotsturdymod.library.gui.immutableimpl.ComponentRoot;
import powerlessri.anotsturdymod.varia.general.GuiUtils;

import java.io.IOException;

public class ComponentizedGui extends GuiContainer {

    protected ComponentRoot root;
    protected ImmutableList<IContainer<IComponent>> windows;
    
    public ComponentizedGui(Container container, ImmutableList<IContainer<IComponent>> windows) {
        super(container);
        this.windows = windows;
    }

    @Override
    public void initGui() {
        super.initGui();

        root = new ComponentRoot(this, windows);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();

        GuiUtils.useTextureGLStates();
        root.draw();
    }
    

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int buttonId) throws IOException {
        super.mouseClicked(mouseX, mouseY, buttonId);
        root.onMouseClicked(mouseX, mouseY, getMouseButton(buttonId));
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int buttonId) {
        super.mouseReleased(mouseX, mouseY, buttonId);
        root.onMouseReleased(mouseX, mouseY, getMouseButton(buttonId));
    }


    private EMouseButton getMouseButton(int flag) {
        switch (flag) {
            case 0:
                return EMouseButton.PRIMARY;
            case 1:
                return EMouseButton.SECONDARY;

            default:
                return EMouseButton.NONE;
        }
    }
    
}
