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
import powerlessri.anotsturdymod.library.gui.simpleimpl.scrolling.ChunkyScrollBar;
import powerlessri.anotsturdymod.library.gui.simpleimpl.scrolling.IScrollableComponent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.scrolling.ScrollingPanel;
import powerlessri.anotsturdymod.library.gui.simpleimpl.section.BasicPanel;
import powerlessri.anotsturdymod.library.gui.simpleimpl.widget.ButtonGradient;
import powerlessri.anotsturdymod.library.gui.integration.ComponentizedGui;
import powerlessri.anotsturdymod.library.gui.integration.ContainerPlayerInventory;
import powerlessri.anotsturdymod.library.gui.template.AbstractTemplate;

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


    public int centerX;
    public int centerY;

    public GuiLogicEditor(Container container) {
        super(container);
    }


    @Override
    public void initGui() {
        ButtonGradient displayBtn = new ButtonGradient(0, 0, 40, 30, "hello!");
        ImmutableList.Builder<IScrollableComponent> builder = ImmutableList.builder();
        for (int i = 0; i < 16; i++) {
            IScrollableComponent button = new ButtonGradient(0, i * 20, 16, 10, String.valueOf(i));
            builder.add(button);
        }
        
        windows = ImmutableList.of(
                new BasicPanel(10, 10, ImmutableList.of(
                        displayBtn,
                        new ButtonGradient(0, 80, 20, 16, "^") {
                            @Override
                            public EnumActionResult onClicked(int mouseX, int mouseY, EMouseButton button, EEventType type) {
                                if (displayBtn.isDisabled()) {
                                    displayBtn.enable();
                                } else {
                                    displayBtn.disable();
                                }
                                return super.onClicked(mouseX, mouseY, button, type);
                            }
                        },

                        new ScrollingPanel(
                                50, 0, 40, 40, 
                                builder.build(),
                                new ChunkyScrollBar(40, 0, 40))
                ))
        );

        super.initGui();

        centerX = (width / 2) - (BKG_WIDTH / 2);
        centerY = (height / 2) - (BKG_HEIGHT / 2);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

//        GuiUtils.useTextureGLStates();
//        mc.renderEngine.bindTexture(BACKGROUND);
//        drawTexturedModalRect(centerX, centerY, BKG_START_X, BKG_START_Y, BKG_WIDTH, BKG_HEIGHT);
    }


    @Override
    public String toString() {
        return "GuiLogicEditor{" +
                "centerX=" + centerX +
                ", centerY=" + centerY +
                '}';
    }
}
