package powerlessri.anotsturdymod.library.utils;

import java.util.LinkedList;
import java.util.Queue;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;


public class InventoryUtils {

    private InventoryUtils() {
    }



    public static int getAmountItems(IInventory inventory, ItemStack specifiedItem) {
        int amount = 0;
        
        for(int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack slot = inventory.getStackInSlot(i);
            
            if(slot.isItemEqual(specifiedItem)) {
                amount += slot.getCount();
            }
        }
        
        return amount;
    }
    
    public static void takeItems(IInventory inventory, ItemStack specifiedItem) {
        tryTakeItems(inventory, specifiedItem);
    }
    
    /** Try to take certain amount of items out of an inventory, if not enough, skip & {@code return false}. Else {@code return true} */
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
                stackRemovingList.forEach((index) -> {
                    inventory.removeStackFromSlot(index);
                });
                
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
