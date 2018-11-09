package powerlessri.anotsturdymod.blocks.gui.base;

import com.google.common.collect.ImmutableList;
import javafx.scene.input.MouseButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import powerlessri.anotsturdymod.blocks.gui.api.render.IRenderedComponent;
import powerlessri.anotsturdymod.blocks.gui.immutable.ComponentRoot;
import powerlessri.anotsturdymod.varia.inventory.GuiUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ComponentizedGui extends GuiContainer {

    private List<WindowConstructor> constructors = new ArrayList<>();
    
    protected ComponentRoot root;
    
    public ComponentizedGui(Container container) {
        super(container);
    }

    @Override
    public void initGui() {
        super.initGui();

        ImmutableList.Builder<IRenderedComponent> builder = ImmutableList.builder();
        for (WindowConstructor constructor : constructors) {
            builder.add(constructor.create());
        }
        root = new ComponentRoot(this, builder.build());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();

        GuiUtils.resetGuiGlStates();
        root.draw();
    }
    
    
    public WindowConstructor forWindow(int id) {
        while (id >= constructors.size()) {
            constructors.add(null);
        }

        WindowConstructor window = constructors.get(id);
        if (window == null) {
            window = new WindowConstructor();
            constructors.set(id, window);
        }

        return window;
    }


    @Override
    protected void mouseClicked(int mouseX, int mouseY, int flag) throws IOException {
        super.mouseClicked(mouseX, mouseY, flag);
        root.onMouseClicked(mouseX, mouseY, flag);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        root.onMouseReleased(mouseX, mouseY, state);
    }
    
}
