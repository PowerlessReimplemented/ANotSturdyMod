package powerlessri.anotsturdymod.blocks.gui.base;

import com.google.common.collect.ImmutableList;
import javafx.scene.input.MouseButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import powerlessri.anotsturdymod.blocks.gui.api.IComponent;
import powerlessri.anotsturdymod.blocks.gui.api.group.IContainer;
import powerlessri.anotsturdymod.blocks.gui.immutable.ComponentRoot;
import powerlessri.anotsturdymod.varia.inventory.GuiUtils;

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
    protected void mouseClicked(int mouseX, int mouseY, int flag) throws IOException {
        super.mouseClicked(mouseX, mouseY, flag);
        root.onMouseClicked(mouseX, mouseY, getMouseButton(flag));
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        root.onMouseReleased(mouseX, mouseY, state);
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
