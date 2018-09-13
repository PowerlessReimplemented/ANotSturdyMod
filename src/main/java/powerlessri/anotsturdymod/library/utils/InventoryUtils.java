package powerlessri.anotsturdymod.library.utils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.BiFunction;

import org.apache.logging.log4j.util.BiConsumer;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;


public class InventoryUtils {

    private InventoryUtils() {
    }

    public static void forEachSlot(IInventory inventory, BiConsumer<ItemStack, Integer> lambd) {
        for(int i = 0; i < inventory.getSizeInventory(); i++) {
            lambd.accept(inventory.getStackInSlot(i), i);
        }
    }

    public static int countItems(IInventory inventory, BiFunction<ItemStack, Integer, Integer> lambd) {
        int count = 0;

        for(int i = 0; i < inventory.getSizeInventory(); i++) {
            count = lambd.apply(inventory.getStackInSlot(i), count);
        }

        return count;
    }

    public static int getAmountItems(IInventory inventory, ItemStack specifiedItem) {
        return countItems(inventory, (stack, sum) -> sum += stack.getCount());
    }

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

        for(int i = 0, inventorySize = inventory.getSizeInventory(); i < inventorySize; i++) {
            ItemStack slot = inventory.getStackInSlot(i);

            if(slot.isItemEqual(specifiedItem)) {
                takeawayLeft -= slot.getCount();
                stackRemovingList.add(i);
            }

            if(takeawayLeft <= 0) {
                while(!stackRemovingList.isEmpty()) {
                    inventory.removeStackFromSlot(stackRemovingList.remove());
                }

                return true;
            }
        }

        return false;
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
        if(tag != null) {
            resultStack.setTagCompound(tag);
        }

        return resultStack;
    }

}
