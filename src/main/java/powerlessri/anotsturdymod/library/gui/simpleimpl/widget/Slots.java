package powerlessri.anotsturdymod.library.gui.simpleimpl.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractComponent;
import powerlessri.anotsturdymod.varia.Reference;

public abstract class Slots extends AbstractComponent {

    /**
     * Stores a set of coordinate that can be corresponded to an {@link net.minecraft.inventory.IInventory IInventory}
     * object.
     */
    public static class DrawHoveringIconEvent extends GuiDrawBackgroundEvent {

        int slotX, slotY;
        int slotIndex;

        public DrawHoveringIconEvent(GuiDrawBackgroundEvent event, int slotX, int slotY) {
            super(event.getTime(), event.getMouseX(), event.getMouseY(), event.getParticleTicks());
            this.slotX = slotX;
            this.slotY = slotY;
        }

        void calculateSlotIndex(int width) {
            this.slotIndex = slotY * width + slotX;
        }


        public int getSlotX() {
            return slotX;
        }

        public int getSlotY() {
            return slotY;
        }

        public int getSlotIndex() {
            return slotIndex;
        }

    }


    public static final ResourceLocation WIDGETS_1 = new ResourceLocation(Reference.MODID, "textures/gui/widgets_1.png");

    public static final int SLOT_POS_X = 0;
    public static final int SLOT_POS_Y = 0;
    public static final int SLOT_IMAGE_WIDTH = 18;
    public static final int SLOT_IMAGE_HEIGHT = 18;

    private int slotsHorizontal;
    private int slotsVertical;

    private int width;
    private int height;

    public Slots(int relativeX, int relativeY, int slotsHorizontal, int slotsVertical) {
        super(relativeX, relativeY);

        this.slotsHorizontal = slotsHorizontal;
        this.slotsVertical = slotsVertical;
        this.width = slotsHorizontal * SLOT_IMAGE_WIDTH;
        this.height = slotsVertical * SLOT_IMAGE_HEIGHT;
    }

    @Override
    public void draw(GuiDrawBackgroundEvent event) {
        DrawHoveringIconEvent iconEvent = new DrawHoveringIconEvent(event, 0, 0);

        Minecraft.getMinecraft().getTextureManager().bindTexture(WIDGETS_1);
        for (int i = 0; i < getSlotsVertical(); i++) {
            int currentY = getActualY() + i * SLOT_IMAGE_HEIGHT;
            iconEvent.slotY = i;

            for (int j = 0; j < getSlotsHorizontal(); j++) {
                int currentX = getActualX() + j * SLOT_IMAGE_WIDTH;
                iconEvent.slotX = j;
                iconEvent.slotIndex++;

                gui.drawTexturedModalRect(currentX, currentY, SLOT_POS_X, SLOT_POS_Y, SLOT_IMAGE_WIDTH, SLOT_IMAGE_HEIGHT);
                this.drawHoveringIcon(iconEvent, currentX, currentY);
            }
        }
    }

    /**
     * Draw the icon above every slot.
     * <p>
     * Called orderly, which means event with <i>{@code index=4}</i> will not be fired until the event with <i>{@code
     * index=3}</i> is fired.
     * </p>
     *
     * @param x The top left drawingX coordinate of the slot.
     * @param y The top left drawingY coordinate of the slot.
     */
    public abstract void drawHoveringIcon(DrawHoveringIconEvent event, int x, int y);


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

    public int getSlotsHorizontal() {
        return slotsHorizontal;
    }

    public int getSlotsVertical() {
        return slotsVertical;
    }

    public int getTotalSlots() {
        return getSlotsHorizontal() * getSlotsVertical();
    }

}

