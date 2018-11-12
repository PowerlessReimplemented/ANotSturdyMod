package powerlessri.anotsturdymod.blocks.logisticalprocessor.gui;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.library.gui.api.ITemplate;
import powerlessri.anotsturdymod.library.gui.api.TemplateProvider;
import powerlessri.anotsturdymod.library.gui.immutableimpl.ComponentPanel;
import powerlessri.anotsturdymod.library.gui.immutableimpl.button.ButtonGradient;
import powerlessri.anotsturdymod.library.gui.integration.ComponentizedGui;
import powerlessri.anotsturdymod.library.gui.integration.ContainerPlayerInventory;
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
    
    
    public int centerX;
    public int centerY;

    public GuiLogicEditor(Container container) {
        super(container, ImmutableList.of());
    }


    @Override
    public void initGui() {
        super.initGui();

        centerX = (width / 2) - (BKG_WIDTH / 2);
        centerY = (height / 2) - (BKG_HEIGHT / 2);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        
        GuiUtils.resetGuiGlStates();
        mc.renderEngine.bindTexture(BACKGROUND);
        drawTexturedModalRect(centerX, centerY, BKG_START_X, BKG_START_Y, BKG_WIDTH, BKG_HEIGHT);
    }
}
