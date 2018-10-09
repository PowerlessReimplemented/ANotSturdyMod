package powerlessri.anotsturdymod.library.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.util.BiConsumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.BiFunction;


public class InventoryUtils {

    private InventoryUtils() {
    }

    public static void forEachSlot(IInventory inventory, BiConsumer<ItemStack, Integer> lambd) {
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            lambd.accept(inventory.getStackInSlot(i), i);
        }
    }

    public static int countItems(IInventory inventory, BiFunction<ItemStack, Integer, Integer> lambd) {
        int count = 0;
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            count = lambd.apply(inventory.getStackInSlot(i), count);
        }
        return count;
    }

    public static int getAmountItems(IInventory inventory, ItemStack specifiedItem) {
        return countItems(inventory, (stack, sum) -> {
            if (stack.isItemEqual(specifiedItem)) {
                return sum += stack.getCount();
            }
            return sum;
        });
    }


    /**
     * Alias to {@code tryTakeItems}.
     */
    public static void takeItems(IInventory inventory, ItemStack specifiedItem) {
        tryTakeItems(inventory, specifiedItem);
    }

    /**
     * Try to take certain amount of items out of an inventory, if not enough, skip
     * & {@code return false}. Else {@code return true}
     */
    public static boolean tryTakeItems(IInventory inventory, ItemStack specifiedItem) {
        // Count for how many items still need to be take away from the inventory
        int takeawayLeft = specifiedItem.getCount();

        // Makes sure if there isn't enough items, do nothing
        Queue<Integer> stackRemovingList = new LinkedList<Integer>();

        for (int i = 0, inventorySize = inventory.getSizeInventory(); i < inventorySize; i++) {
            ItemStack slot = inventory.getStackInSlot(i);

            if (slot.isItemEqual(specifiedItem)) {
                takeawayLeft -= slot.getCount();
                stackRemovingList.add(i);
            }

            if (takeawayLeft <= 0) {
                while (!stackRemovingList.isEmpty()) {
                    inventory.removeStackFromSlot(stackRemovingList.remove());
                }

                return true;
            }
        }

        return false;
    }

    /**
     * Try to take a certain amount of a specified type of items away from an inventory.
     *
     * @param inventory The inventory to take items from.
     * @param type      Specified item type. Check metadata, doesn't check NBT value.
     *                  If {@code null}, all items will be accepted.
     * @param toTake    Amount of items to take away.
     *                  If less than {@code 0}, will take as many as possible.
     * @return Amount of items failed to take away.
     */
    public static int forceTakeItems(IInventory inventory, ItemStack type, int toTake) {
        for (int i = 0, inventorySize = inventory.getSizeInventory(); i < inventorySize; i++) {
            ItemStack slot = inventory.getStackInSlot(i);

            if (type == null || slot.isItemEqual(type)) {
                int taken = toTake < 0 ? slot.getCount() : Math.min(toTake, slot.getCount());

                toTake -= taken;
                slot.setCount(slot.getCount() - taken);

                if (toTake == 0) {
                    return 0;
                }
            }
        }

        return toTake;
    }


    public static ItemStack stackOf(Item item, int meta) {
        return stackOf(item, meta, 1);
    }

    public static ItemStack stackOf(Item item, int meta, int count) {
        return stackOf(item, meta, count, null);
    }

    public static ItemStack stackOf(Item item, int meta, int count, NBTTagCompound tag) {
        ItemStack resultStack = new ItemStack(item);

        resultStack.setItemDamage(meta);
        resultStack.setCount(count);
        if (tag != null) {
            resultStack.setTagCompound(tag);
        }

        return resultStack;
    }

}
