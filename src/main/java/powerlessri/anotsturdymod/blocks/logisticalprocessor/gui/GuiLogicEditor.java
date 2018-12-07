package powerlessri.anotsturdymod.blocks.logisticalprocessor.gui;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.library.gui.api.EEventType;
import powerlessri.anotsturdymod.library.gui.api.EMouseButton;
import powerlessri.anotsturdymod.library.gui.api.ITemplate;
import powerlessri.anotsturdymod.library.gui.api.TemplateProvider;
import powerlessri.anotsturdymod.library.gui.simpleimpl.scrollable.IScrollableComponent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.scrollable.ScrollablePanel;
import powerlessri.anotsturdymod.library.gui.simpleimpl.section.BasicPanel;
import powerlessri.anotsturdymod.library.gui.simpleimpl.section.TextureElement;
import powerlessri.anotsturdymod.library.gui.simpleimpl.widget.ButtonGradient;
import powerlessri.anotsturdymod.library.gui.integration.ComponentizedGui;
import powerlessri.anotsturdymod.library.gui.integration.ContainerPlayerInventory;
import powerlessri.anotsturdymod.library.gui.simpleimpl.widget.ScrollableButtonGradient;
import powerlessri.anotsturdymod.library.gui.template.AbstractTemplate;
import powerlessri.anotsturdymod.varia.general.GuiUtils;

public class GuiLogicEditor extends ComponentizedGui {

    @TemplateProvider(id = "LogisticSys_Editor")
    public static ITemplate getGuiTemplate() {
        return new AbstractTemplate() {
            @Override
            public ContainerPlayerInventory getContainer() {
                return new ContainerPlayerInventory(player) {
                    @Override
                    public boolean canInteractWith(EntityPlayer playerIn) {
                        return true;
                    }
                };
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
            IScrollableComponent button = new ScrollableButtonGradient(0, i * 20, 16, 10, String.valueOf(i));
            builder.add(button);
        }

        windows = ImmutableList.of(
                new BasicPanel(windowX, windowY, ImmutableList.of(
                        new TextureElement(0, 0, BACKGROUND, BKG_START_X, BKG_START_Y, BKG_WIDTH, BKG_HEIGHT),
                        // + 2 is left margin, top margin is handled in ScrollablePanel
                        ScrollablePanel.simpleLayout(8 + 2, 73, 107, 11, builder.build(),  107)
                ))
        );

        super.initGui();

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    }
    
}
