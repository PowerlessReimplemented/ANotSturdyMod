package powerlessri.anotsturdymod.library.gui.integration;

import com.google.common.collect.ImmutableList;
import javafx.scene.input.MouseButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
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
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();

        GuiUtils.resetGuiGlStates();
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


    private MouseButton getMouseButton(int flag) {
        switch (flag) {
            case 0:
                return MouseButton.PRIMARY;
            case 1:
                return MouseButton.SECONDARY;

            default:
                return MouseButton.NONE;
        }
    }
    
}
