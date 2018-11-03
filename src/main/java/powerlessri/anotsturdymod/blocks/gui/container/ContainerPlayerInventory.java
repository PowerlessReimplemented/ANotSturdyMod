package powerlessri.anotsturdymod.blocks.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

/**
 * Gives
 */
public abstract class ContainerPlayerInventory extends Container {

    private static final int SLOT_WIDTH = 18;
    private static final int SLOT_HEIGHT = 18;

    /**
     * Amount of slot horizontally.
     */
    private static final int PLAYER_INVENTORY_WIDTH = 9;
    /**
     * Amount of slots vertically.
     */
    private static final int PLAYER_INVENTORY_HEIGHT = 3;
    /**
     * Amount of pixels between main storage and hotbar.
     */
    private static final int PLAYER_INVENTORY_HOTBAR_GAP = 4;


    protected EntityPlayer player;

    public ContainerPlayerInventory(EntityPlayer player) {
        this.player = player;
    }

    public void addPlayerInventorySlots(int x, int y) {
        // The 3*9 storage panel
        // Slot ID: top-left = 9
        for (int i = 0, slotId = 9; i < PLAYER_INVENTORY_HEIGHT; i++) {
            int currentY = SLOT_HEIGHT * i + y;

            for (int j = 0; j < PLAYER_INVENTORY_WIDTH; j++) {
                int currentX = SLOT_WIDTH * j + x;

                Slot slot = new Slot(player.inventory, slotId++, currentX, currentY);
                addSlotToContainer(slot);
            }
        }

        // Hotbar
        // Slot ID: most left = 0
        final int hotbarY = y + (PLAYER_INVENTORY_HEIGHT * SLOT_HEIGHT) + PLAYER_INVENTORY_HOTBAR_GAP;
        for (int i = 0, slotId = 0; i < PLAYER_INVENTORY_WIDTH; i++) {
            int currentX = SLOT_WIDTH * i + x;

            Slot slot = new Slot(player.inventory, slotId++, currentX, hotbarY);
            addSlotToContainer(slot);
        }
    }

}
