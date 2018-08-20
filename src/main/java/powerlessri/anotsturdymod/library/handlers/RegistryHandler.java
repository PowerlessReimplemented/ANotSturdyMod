package powerlessri.anotsturdymod.library.handlers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import powerlessri.anotsturdymod.init.ModBlocks;
import powerlessri.anotsturdymod.init.ModItems;
import powerlessri.anotsturdymod.items.basic.ItemBasicItem;

@EventBusSubscriber
public class RegistryHandler {

    private RegistryHandler() {
    }

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry()
             .registerAll(ModItems.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        //TODO register ItemBlock corresponding to the blocks
        event.getRegistry()
             .registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
    }



    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        for(Item item : ModItems.ITEMS) {
            if(item instanceof ItemBasicItem) {
                ((ItemBasicItem) item).registerModel();
            }
        }
    }

}
