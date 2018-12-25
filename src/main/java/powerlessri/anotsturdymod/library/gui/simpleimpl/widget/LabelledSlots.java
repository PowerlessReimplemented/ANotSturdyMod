package powerlessri.anotsturdymod.library.gui.simpleimpl.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import powerlessri.anotsturdymod.library.gui.api.*;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.varia.general.Utils;
import powerlessri.anotsturdymod.varia.render.Displays;

import java.util.Arrays;

public class LabelledSlots extends Slots implements IInteractionHandler, IScrollableComponent {

    private ItemStack[] itemStacks;

    public LabelledSlots(int relativeX, int relativeY, int slotsHorizontal, int slotsVertical) {
        super(relativeX, relativeY, slotsHorizontal, slotsVertical);
        this.itemStacks = new ItemStack[getTotalSlots()];
    }


    @Override
    public void initialize(GuiScreen gui, IComponent parent) {
        super.initialize(gui, parent);
        this.visible = true;
        this.renderingY = super.getActualY();
        
        //TODO sync item stacks data from server
        Arrays.fill(itemStacks, ItemStack.EMPTY);
    }

    /**
     * Record the item the player is holding in his cursor.
     */
    @Override
    public EnumActionResult onClicked(int mouseX, int mouseY, EMouseButton button, EEventType type) {
        int slotClicked = getSlotClicked(mouseX - getActualX(), mouseY - getActualY());
        ItemStack cursorItem = Minecraft.getMinecraft().player.inventory.getItemStack().copy();
        cursorItem.setCount(1);
        
        itemStacks[slotClicked] = cursorItem;
        return EnumActionResult.FAIL;
    }

    private int getSlotClicked(int x, int y) {
        // Integer divisions are automatically and always floored
        int slotX = x / SLOT_IMAGE_WIDTH;
        int slotY = y / SLOT_IMAGE_HEIGHT;
        return slotY * getSlotsHorizontal() + slotX;
    }


    @Override
    public void drawHoveringIcon(DrawHoveringIconEvent event, int x, int y) {
        ItemStack stack = itemStacks[event.getSlotIndex()];
        // Size of a slot is 18*18, but the size of an item is 16*16
        Displays.drawItemStackWithoutSize(stack, x + 1, y + 1);
    }


    @Override
    public void onClickedDragging(int mouseX, int mouseY, EMouseButton button, long timePressed) {
    }

    @Override
    public void onHoveredDragging(int mouseX, int mouseY, EMouseButton button) {
    }

    @Override
    public EnumActionResult onReleased(int mouseX, int mouseY, EMouseButton button, EEventType type) {
        return EnumActionResult.FAIL;
    }

    @Override
    public void draw(GuiDrawBackgroundEvent event) {
        if (isVisible()) {
            super.draw(event);
        }
    }


    private boolean visible;
    private int renderingY;

    @Override
    public void setExpectedY(int y) {
        this.renderingY = y;
    }

    @Override
    public int getActualY() {
        return renderingY;
    }

    @Override
    public void setVisibility(boolean visibility) {
        this.visible = visibility;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public boolean doesReceiveEvents() {
        return isVisible();
    }

}
