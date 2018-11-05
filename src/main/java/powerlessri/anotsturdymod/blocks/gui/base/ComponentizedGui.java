package powerlessri.anotsturdymod.blocks.gui.base;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import powerlessri.anotsturdymod.blocks.gui.api.render.IRenderedComponent;
import powerlessri.anotsturdymod.blocks.gui.immutable.ComponentRoot;
import powerlessri.anotsturdymod.varia.inventory.GuiUtils;

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
    
}
