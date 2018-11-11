package powerlessri.anotsturdymod.varia.general;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.util.BiConsumer;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.BiFunction;


public class InventoryUtils {

    private InventoryUtils() {
    }

    public static void forEachSlot(@Nonnull IInventory inventory, BiConsumer<ItemStack, Integer> lambd) {
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            lambd.accept(inventory.getStackInSlot(i), i);
        }
    }

    public static int countItems(@Nonnull IInventory inventory, BiFunction<ItemStack, Integer, Integer> lambd) {
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
        tryTakeItems(inventory, specifiedItem, specifiedItem.getCount());
    }

    /**
     * Try to take certain amount of items out of an inventory, if not enough, skip
     * & {@code return false}. Else {@code return true}
     */
    public static boolean tryTakeItems(@Nonnull IInventory inventory, ItemStack specifiedItem, int takeawayLeft) {
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
    public static int forceTakeItems(@Nonnull IInventory inventory, ItemStack type, int toTake) {
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


    public static ItemStack stackOf(Item item, int meta, int count, NBTTagCompound tag) {
        ItemStack resultStack = new ItemStack(item);

        resultStack.setItemDamage(meta);
        resultStack.setCount(count);
        if (tag != null) {
            resultStack.setTagCompound(tag);
        }

        return resultStack;
    }

    public static ItemStack stackOf(IBlockState state) {
        return stackOf(state, 1);
    }

    public static ItemStack stackOf(IBlockState state, int count) {
        Block block = state.getBlock();
        int meta = block.getMetaFromState(state);
        return new ItemStack(block, count, meta);
    }

}
