package powerlessri.anotsturdymod.blocks.logisticalprocessor.gui;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.library.gui.api.IContainer;
import powerlessri.anotsturdymod.library.gui.simpleimpl.scrollable.IScrollableComponent;
import powerlessri.anotsturdymod.library.gui.api.ITemplate;
import powerlessri.anotsturdymod.library.gui.api.TemplateProvider;
import powerlessri.anotsturdymod.library.gui.integration.ComponentizedGui;
import powerlessri.anotsturdymod.library.gui.integration.ContainerPlayerInventory;
import powerlessri.anotsturdymod.library.gui.simpleimpl.scrollable.ScrollablePanel;
import powerlessri.anotsturdymod.library.gui.simpleimpl.section.SimplePanel;
import powerlessri.anotsturdymod.library.gui.simpleimpl.slot.LabelledSlots;
import powerlessri.anotsturdymod.library.gui.simpleimpl.label.LabelTexture;
import powerlessri.anotsturdymod.library.gui.simpleimpl.button.ScrollableButtonGradient;
import powerlessri.anotsturdymod.library.gui.template.AbstractTemplate;

public class GuiLogicEditor extends ComponentizedGui {

    @TemplateProvider(id = "LogisticSys_Editor")
    public static ITemplate getGuiTemplate() {
        return new AbstractTemplate() {
            @Override
            public ContainerPlayerInventory getContainer() {
                ContainerPlayerInventory container = new ContainerPlayerInventory(player) {
                    
                    @Override
                    public boolean canInteractWith(EntityPlayer playerIn) {
                        return true;
                    }
                };
                container.addPlayerInventorySlots(0, 0);
                return container;
            }

            @Override
            public ComponentizedGui getGui() {
                return new GuiLogicEditor(getContainer());
            }
        };
    }


    public static final ResourceLocation BACKGROUND = new ResourceLocation("ansm:textures/gui/logistics_procedure_editor.png");
    public static final int BKG_START_X = 0;
    public static final int BKG_START_Y = 0;
    public static final int BKG_WIDTH = 256;
    public static final int BKG_HEIGHT = 224;


    public int windowX;
    public int windowY;

    public GuiLogicEditor(Container container) {
        super(container);
    }


    @Override
    public void initGui() {
        windowX = (width / 2) - (BKG_WIDTH / 2);
        windowY = (height / 2) - (BKG_HEIGHT / 2);

        ImmutableList.Builder<IScrollableComponent> builder = ImmutableList.builder();
        for (int i = 0; i < 32; i++) {
            IScrollableComponent button = new ScrollableButtonGradient(0, 16, 10, String.valueOf(i));
            builder.add(button);
        }

        windows = ImmutableList.of(
                new SimplePanel(windowX, windowY, ImmutableList.of(
                        new LabelTexture(0, 0, BKG_WIDTH, BKG_HEIGHT, BACKGROUND, BKG_START_X, BKG_START_Y),
                        new LabelledSlots(131 + 4, 73 + 4, 9, 2),
                        // + 2 is the left margin, top margin is handled in ScrollablePanel
                        ScrollablePanel.simpleLayout(8 + 2, 73, 107, 11, builder.build(), 107)
                ))
        );

        super.initGui();
    }

    
    @Override
    public IContainer<?> getMainWindow() {
        return windows.get(0);
    }

}
