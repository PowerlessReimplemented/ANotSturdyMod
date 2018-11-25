package powerlessri.anotsturdymod.handlers.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import powerlessri.anotsturdymod.items.exchangers.ItemExchanger;
import powerlessri.anotsturdymod.items.ItemIlluminator;
import powerlessri.anotsturdymod.items.transmutations.ItemTransmutationStone;
import powerlessri.anotsturdymod.items.ItemUpgrade;
import powerlessri.anotsturdymod.library.block.base.SimpleBlockBase;
import powerlessri.anotsturdymod.varia.general.Utils;
import powerlessri.anotsturdymod.varia.reflection.AnnotationRetentionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class ModItems {

    public static final List<Item> ITEMS = new ArrayList<Item>();


    public static void addItemForBlock(Block block) {
        if (block instanceof SimpleBlockBase) {
            ITEMS.add(((SimpleBlockBase) block).getItemBlock());
        } else {
            ItemBlock item = new ItemBlock(block);
            item.setRegistryName(block.getRegistryName());
            ITEMS.add(item);
        } 
    }
    
    
    public static void preInit(FMLPreInitializationEvent event) {
        loadItems(event.getAsmData());
    }

    private static void loadItems(ASMDataTable table) {
        List<Field> fields = AnnotationRetentionUtils.getAllAnnotatedFields(table, RegistryItem.class);
        for (Field field : fields) {
            Item item;
            try {
                item = (Item) field.get(null);
            } catch (ClassCastException e) {
                Utils.report("Field specified as a RegistryItem, but has a different type than Item. ", e);
                continue;
            } catch (IllegalAccessException e) {
                // This should not happen since getAllAnnotatedFields sets the accessibility to true.
                Utils.getLogger().error("Cannot access the field " + field.getName() + " during block registry initialization.");
                continue;
            }

            ITEMS.add(item);
        }
    }

}
