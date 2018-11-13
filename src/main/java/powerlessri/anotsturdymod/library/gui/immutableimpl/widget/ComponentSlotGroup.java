package powerlessri.anotsturdymod.library.gui.immutableimpl.widget;

import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.library.gui.immutableimpl.AbstractComponent;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.varia.Reference;

public class ComponentSlotGroup extends AbstractComponent {
    
    public static final ResourceLocation WIDGETS_1 = new ResourceLocation(Reference.MODID, "textures/gui/widgets_1.png");
    
    public static final int SLOT_POS_X = 0;
    public static final int SLOT_POS_Y = 0;
    public static final int SLOT_IMAGE_WIDTH = 18;
    public static final int SLOT_IMAGE_HEIGHT = 18;

    private int slotsHorizontal;
    private int slotsVertical;
    
    private int width;
    private int height;

    public ComponentSlotGroup(int relativeX, int relativeY, int slotsHorizontal, int slotsVertical) {
        super(relativeX, relativeY);
        
        this.slotsHorizontal = slotsHorizontal;
        this.slotsVertical = slotsVertical;
        this.width = slotsHorizontal * SLOT_IMAGE_WIDTH;
        this.height = slotsVertical * SLOT_IMAGE_HEIGHT;
    }

    @Override
    public void draw(GuiDrawBackgroundEvent event) {
        gui.mc.renderEngine.bindTexture(WIDGETS_1);
        
        for (int i = 0; i < slotsVertical; i++) {
            int currentY = getActualY() + i * SLOT_IMAGE_HEIGHT;
            for (int j = 0; j < slotsHorizontal; j++) {
                int currentX = getActualX() + j * SLOT_IMAGE_WIDTH;
                
                gui.drawTexturedModalRect(currentX, currentY, SLOT_POS_X, SLOT_POS_Y, SLOT_IMAGE_WIDTH, SLOT_IMAGE_HEIGHT);
            }
        }
    }


    @Override
    public boolean isLeafComponent() {
        return true;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getZIndex() {
        return 0;
    }

    @Override
    public void setZIndex(int zIndex) {
    }
    
}

