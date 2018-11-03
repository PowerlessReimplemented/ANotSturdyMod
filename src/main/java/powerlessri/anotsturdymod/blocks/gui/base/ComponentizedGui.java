package powerlessri.anotsturdymod.blocks.gui.base;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import powerlessri.anotsturdymod.blocks.gui.api.IComponent;
import powerlessri.anotsturdymod.blocks.gui.immutable.ComponentRoot;
import powerlessri.anotsturdymod.varia.inventory.GuiUtils;

import java.util.ArrayList;
import java.util.List;

public class ComponentizedGui extends GuiContainer {

    private List<WindowConstructor> bufWindows = new ArrayList<>();
    
    protected ComponentRoot root;
    
    public ComponentizedGui(Container container) {
        super(container);
    }

    @Override
    public void initGui() {
        super.initGui();

        // Convert WindowConstructor's to IComponent's
        IComponent[] windows = new IComponent[bufWindows.size()];
        for (int i = 0; i < windows.length; i++) {
            WindowConstructor windowConstructor = bufWindows.get(i);
            windows[i] = windowConstructor.create();
        }
        
        root = new ComponentRoot(this, ImmutableList.copyOf(windows));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();

        GuiUtils.resetGuiGlStates();
        root.draw();
    }
    
    
    public WindowConstructor forWindow(int id) {
        while (id >= bufWindows.size()) {
            bufWindows.add(null);
        }

        WindowConstructor window = bufWindows.get(id);
        if (window == null) {
            window = new WindowConstructor();
            bufWindows.set(id, window);
        }

        return window;
    }
    
}
