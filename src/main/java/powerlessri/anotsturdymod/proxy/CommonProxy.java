package powerlessri.anotsturdymod.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import powerlessri.anotsturdymod.blocks.BlockLightCube;
import powerlessri.anotsturdymod.blocks.base.BlockBase;
import powerlessri.anotsturdymod.blocks.basic.BlockBasicBlock;
import powerlessri.anotsturdymod.commands.CommandAnsm;
import powerlessri.anotsturdymod.commands.CommandDebug;
import powerlessri.anotsturdymod.init.ModBlocks;
import powerlessri.anotsturdymod.init.ModCommands;
import powerlessri.anotsturdymod.init.ModItems;
import powerlessri.anotsturdymod.items.ItemExchanger;
import powerlessri.anotsturdymod.items.ItemIlluminator;
import powerlessri.anotsturdymod.items.ItemTransmutationStone;
import powerlessri.anotsturdymod.items.base.ItemBase;
import powerlessri.anotsturdymod.items.handler.WorldTransmutation;
import powerlessri.anotsturdymod.library.enums.EMachineLevel;


public class CommonProxy {

    // ClientSide-only stuffs
    public void registerItemRenderer(Item item, int meta, String id) {
    }

    public void registerBlockRenderer(Block block, int meta, String id) {
    }



    public void serverStarting(FMLServerStartingEvent event) {
        ModCommands.COMMANDS.add(new CommandDebug());
        ModCommands.COMMANDS.add(new CommandAnsm());

        ModCommands.COMMANDS.forEach((c) -> {
            event.registerServerCommand(c);
        });
    }

    public void preInit(FMLPreInitializationEvent event) {
        registerBlock(new BlockLightCube("light_cube"));

        registerItem(new ItemTransmutationStone("transmutation_orb"));
        registerItem(new ItemExchanger("exchanger", EMachineLevel.BASIC, 1));
        registerItem(new ItemExchanger("exchanger", EMachineLevel.ADVANCED, 4));
        registerItem(new ItemIlluminator("illuminator", EMachineLevel.BASIC));
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
        WorldTransmutation.init(event);
    }



    protected void registerBlock(BlockBase block) {
        ModBlocks.BLOCKS.add(block);
    }

    protected void registerBlock(BlockBasicBlock block) {
        this.registerBlock((BlockBase) block);
        ModItems.ITEMS.add(Item.getItemFromBlock(block));
    }

    protected void registerItem(ItemBase item) {
        ModItems.ITEMS.add(item);
    }

}
