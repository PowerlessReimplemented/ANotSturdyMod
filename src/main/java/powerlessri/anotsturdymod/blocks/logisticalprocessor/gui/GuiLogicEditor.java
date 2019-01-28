package powerlessri.anotsturdymod.blocks.logisticalprocessor.gui;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.library.gui.api.IContainer;
import powerlessri.anotsturdymod.library.gui.api.ITemplate;
import powerlessri.anotsturdymod.library.gui.api.TemplateProvider;
import powerlessri.anotsturdymod.library.gui.integration.ComponentizedGui;
import powerlessri.anotsturdymod.library.gui.integration.ContainerPlayerInventory;
import powerlessri.anotsturdymod.library.gui.simpleimpl.button.ButtonGradient;
import powerlessri.anotsturdymod.library.gui.simpleimpl.button.ScrollableButtonGradient;
import powerlessri.anotsturdymod.library.gui.simpleimpl.events.FocusEvent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.events.IFocusListener;
import powerlessri.anotsturdymod.library.gui.simpleimpl.events.HoveringEvent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.events.IHoveringListener;
import powerlessri.anotsturdymod.library.gui.simpleimpl.label.LabelTexture;
import powerlessri.anotsturdymod.library.gui.simpleimpl.scrollable.IScrollableComponent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.scrollable.ScrollablePanel;
import powerlessri.anotsturdymod.library.gui.simpleimpl.section.SimplePanel;
import powerlessri.anotsturdymod.library.gui.simpleimpl.slot.LabelledSlots;
import powerlessri.anotsturdymod.library.gui.template.AbstractTemplate;
import powerlessri.anotsturdymod.varia.general.Utils;

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
            IScrollableComponent button = new ScrollableButtonGradient(0, 16, 16, String.valueOf(i));
            builder.add(button);
        }

        ButtonGradient btn = new ButtonGradient(0, 0, 64, 64, "click") {

        };
        IFocusListener fl = new IFocusListener() {
            @Override
            public void onFocus(FocusEvent.On event) {
                Utils.getLogger().info("focus");
            }

            @Override
            public void onUnfocus(FocusEvent.Off event) {
                Utils.getLogger().info("unfocus");
            }

            @Override
            public void update(FocusEvent.Update event) {
            }
        };
        IHoveringListener hl = new IHoveringListener() {
            @Override
            public void onCursorEnter(HoveringEvent.Enter event) {
                Utils.getLogger().info("enter");
            }

            @Override
            public void onCursorLeave(HoveringEvent.Leave event) {
                Utils.getLogger().info("leave");
            }
        };

        windows = ImmutableList.of(
                new SimplePanel(windowX, windowY, ImmutableList.of(
                        new LabelTexture(0, 0, BKG_WIDTH, BKG_HEIGHT, BACKGROUND, BKG_START_X, BKG_START_Y),
//                        btn,
//                        new ButtonGradient(0, 70, 10, 10, "2i")
                        new LabelledSlots(131 + 4, 73 + 4, 9, 2),
                        // + 2 is the left margin, top margin is handled in ScrollablePanel
                        ScrollablePanel.simpleLayout(8 + 2, 73, 107, 11, builder.build(), 107)
                ))
        );

        super.initGui();

        root.getCursorBus().registerFocus(btn, fl);
        root.getCursorBus().registerHovering(btn, hl);
    }


    @Override
    public IContainer<?> getMainWindow() {
        return windows.get(0);
    }

}
