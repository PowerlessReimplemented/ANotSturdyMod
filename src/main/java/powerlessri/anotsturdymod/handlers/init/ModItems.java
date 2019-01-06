package powerlessri.anotsturdymod.handlers.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import powerlessri.anotsturdymod.library.block.base.SimpleBlockBase;
import powerlessri.anotsturdymod.varia.general.Utils;
import powerlessri.anotsturdymod.varia.reflection.AnnotationSearcher;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ModItems {

    public static final Set<Item> ITEMS = new HashSet<>();


    public static void addItemForBlock(Block block) {
        if (block instanceof SimpleBlockBase) {
            addItemForBlock((SimpleBlockBase) block);
        }
    }

    public static void addItemForBlock(SimpleBlockBase block) {
        if (block.hasItemForm()) {
            ITEMS.add(block.getItemBlock());
        }
    }
    
    
    public static void preInit(FMLPreInitializationEvent event) {
        loadItems(event.getAsmData());
    }

    private static void loadItems(ASMDataTable table) {
        List<Field> fields = AnnotationSearcher.getAllAnnotatedFields(table, RegistryItem.class);
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
