package powerlessri.anotsturdymod.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import powerlessri.anotsturdymod.blocks.BlockLightCube;
import powerlessri.anotsturdymod.commands.CommandAnsm;
import powerlessri.anotsturdymod.commands.CommandDebug;
import powerlessri.anotsturdymod.init.ModBlocks;
import powerlessri.anotsturdymod.init.ModCommands;
import powerlessri.anotsturdymod.init.ModItems;
import powerlessri.anotsturdymod.items.ItemExchanger;
import powerlessri.anotsturdymod.items.ItemLightingPlacer;
import powerlessri.anotsturdymod.items.ItemTransmutationStone;
import powerlessri.anotsturdymod.items.handler.WorldTransmutation;
import powerlessri.anotsturdymod.library.enums.EMachineLevel;


public class CommonProxy {

    // ClientSide-only stuffs
    public void registerItemRenderer(Item item, int meta, String id) {
    }

    public void registerBlockRenderer(Block block, int meta, String id) {
    }

    // ServerSide-only stuffs
    public void serverStarting(FMLServerStartingEvent event) {
        ModCommands.COMMANDS.add(new CommandDebug());
        ModCommands.COMMANDS.add(new CommandAnsm());

        ModCommands.COMMANDS.forEach((c) -> {
            event.registerServerCommand(c);
        });
    }

    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.BLOCKS.add(new BlockLightCube("light_cube"));
        
        ModItems.ITEMS.add(new ItemTransmutationStone("transmutation_orb"));
        ModItems.ITEMS.add(new ItemExchanger("exchanger", EMachineLevel.BASIC, 1));
        ModItems.ITEMS.add(new ItemExchanger("exchanger", EMachineLevel.ADVANCED, 4));
        ModItems.ITEMS.add(new ItemLightingPlacer("lighting_placer", EMachineLevel.BASIC));
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
        WorldTransmutation.init(event);
    }

}
