package powerlessri.anotsturdymod.handlers.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import powerlessri.anotsturdymod.blocks.base.SimpleBlockBase;
import powerlessri.anotsturdymod.items.base.SimpleItemBase;


@EventBusSubscriber
public class RegistryHandler {

    private RegistryHandler() {
    }

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        for(Item item : ModItems.ITEMS) {
            if(item instanceof SimpleItemBase) {
                ((SimpleItemBase) item).registerModel();
            }
        }
        
        for(Block block : ModBlocks.BLOCKS) {
            if(block instanceof SimpleBlockBase) {
                ((SimpleBlockBase) block).registerModel();
            }
        }
    }



    public static String makeTileEntityID(String teName) {
        return "te." + teName;
    }

}
