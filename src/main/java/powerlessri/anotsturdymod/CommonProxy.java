package powerlessri.anotsturdymod;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import powerlessri.anotsturdymod.blocks.BlockInfiniteCobbleGenerator;
import powerlessri.anotsturdymod.blocks.BlockLightCube;
import powerlessri.anotsturdymod.blocks.BlockRemoteEnergyCell;
import powerlessri.anotsturdymod.blocks.base.BlockBase;
import powerlessri.anotsturdymod.blocks.base.SimpleBlockBase;
import powerlessri.anotsturdymod.blocks.base.TileBlockBase;
import powerlessri.anotsturdymod.commands.CommandAnsmUtils;
import powerlessri.anotsturdymod.init.ModBlocks;
import powerlessri.anotsturdymod.init.ModCommands;
import powerlessri.anotsturdymod.init.ModItems;
import powerlessri.anotsturdymod.items.ItemExchanger;
import powerlessri.anotsturdymod.items.ItemIlluminator;
import powerlessri.anotsturdymod.items.ItemTransmutationStone;
import powerlessri.anotsturdymod.items.base.ItemBase;
import powerlessri.anotsturdymod.items.base.SimpleItemBase;
import powerlessri.anotsturdymod.library.EMachineLevel;
import powerlessri.anotsturdymod.library.handlers.CommonReloadHandler;
import powerlessri.anotsturdymod.world.handler.WorldTransmutation;


public class CommonProxy {

    public CommonReloadHandler reloadHandler;


    // ClientSide-only stuffs
    public void registerItemRenderer(Item item, int meta, String id) {
    }

    public void registerBlockRenderer(Block block, int meta, String id) {
    }


    /** Initializing mod stuffs that are side-dependent. */
    public void modInit() {
        this.reloadHandler = new CommonReloadHandler();
    }

    public void preInit(FMLPreInitializationEvent event) {
        registerBlock(new BlockInfiniteCobbleGenerator("infinite_cobble_generator"));
        registerBlock(new BlockRemoteEnergyCell("remote_energy_cell"));
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

    public void serverStarting(FMLServerStartingEvent event) {
        ModCommands.COMMANDS.add(new CommandAnsmUtils());
    }



    protected void registerBlock(Block block) {
        ModBlocks.BLOCKS.add(block);
    }

    protected void registerBlock(BlockBase block) {
        this.registerBlock((Block) block);
    }

    protected void registerBlock(SimpleBlockBase block) {
        this.registerBlock((BlockBase) block);
        this.registerItem(block.getItemBlock());
    }
    
    protected void registerBlock(TileBlockBase block) {
        this.registerBlock((SimpleBlockBase) block);
        
        TileEntity.register(block.getRegistryName().toString(), block.getTileEntityClass());
    }


    protected void registerItem(Item item) {
        ModItems.ITEMS.add(item);
    }

    protected void registerItem(ItemBase item) {
        this.registerItem((Item) item);
    }

    protected void registerItem(SimpleItemBase item) {
        this.registerItem((ItemBase) item);
    }

}
