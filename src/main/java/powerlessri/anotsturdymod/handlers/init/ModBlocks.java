package powerlessri.anotsturdymod.handlers.init;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import powerlessri.anotsturdymod.blocks.cobblegen.block.BlockInfiniteCobbleGenerator;
import powerlessri.anotsturdymod.blocks.BlockLightCube;
import powerlessri.anotsturdymod.blocks.remoteenetwork.block.BlockEnergyAccessPort;
import powerlessri.anotsturdymod.blocks.remoteenetwork.block.BlockEnergyController;
import powerlessri.anotsturdymod.varia.general.Utils;
import powerlessri.anotsturdymod.varia.reflection.AnnotationRetentionUtils;

import javax.rmi.CORBA.Util;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModBlocks {

    public static final Set<Block> BLOCKS = new HashSet<>();


    public static void preInit(FMLPreInitializationEvent event) {
        loadBlocks(event.getAsmData());
        addItemForBlocks();
    }

    private static void loadBlocks(ASMDataTable table) {
        List<Field> fields = AnnotationRetentionUtils.getAllAnnotatedFields(table, RegistryBlock.class);
        for (Field field : fields) {
            Block block;
            try {
                block = (Block) field.get(null);
            } catch (ClassCastException e) {
                Utils.report("Field specified as a RegistryBlock, but has a different type than Block. ", e);
                continue;
            } catch (IllegalAccessException e) {
                // This should not happen since getAllAnnotatedFields sets the accessibility to true.
                Utils.getLogger().error("Cannot access the field " + field.getName() + " during block registry initialization.");
                continue;
            }

            BLOCKS.add(block);
        }
    }
    
    private static void addItemForBlocks() {
        for (Block block : BLOCKS) {
            ModItems.addItemForBlock(block);
        }
    }

}
