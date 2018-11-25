package powerlessri.anotsturdymod.handlers.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.blocks.BlockLightCube;
import powerlessri.anotsturdymod.blocks.cobblegen.block.BlockInfiniteCobbleGenerator;
import powerlessri.anotsturdymod.blocks.cobblegen.tile.TileCobbleGenerator;
import powerlessri.anotsturdymod.blocks.logisticalprocessor.block.BlockLogisticsController;
import powerlessri.anotsturdymod.blocks.remoteenetwork.block.BlockEnergyAccessPort;
import powerlessri.anotsturdymod.blocks.remoteenetwork.block.BlockEnergyController;
import powerlessri.anotsturdymod.blocks.remoteenetwork.tile.*;
import powerlessri.anotsturdymod.commands.CommandAnsmUtils;
import powerlessri.anotsturdymod.config.MainConfig;
import powerlessri.anotsturdymod.handlers.init.ModBlocks;
import powerlessri.anotsturdymod.handlers.init.ModCommands;
import powerlessri.anotsturdymod.handlers.init.ModItems;
import powerlessri.anotsturdymod.handlers.init.ModTileEntities;
import powerlessri.anotsturdymod.items.ItemIlluminator;
import powerlessri.anotsturdymod.items.exchangers.ItemExchanger;
import powerlessri.anotsturdymod.items.transmutations.ItemTransmutationStone;
import powerlessri.anotsturdymod.items.transmutations.WorldTransmutation;
import powerlessri.anotsturdymod.library.block.base.BlockBase;
import powerlessri.anotsturdymod.library.block.base.SimpleBlockBase;
import powerlessri.anotsturdymod.library.block.base.TileBlockBase;
import powerlessri.anotsturdymod.library.item.base.ItemBase;
import powerlessri.anotsturdymod.library.item.base.SimpleItemBase;
import powerlessri.anotsturdymod.network.PacketLocationalGuiAction;
import powerlessri.anotsturdymod.network.PacketServerCommand;
import powerlessri.anotsturdymod.varia.machines.EMachineLevel;

public class CommonProxy {


    // ClientSide-only stuffs
    public void registerItemRenderer(Item item, int meta, String id) {
    }

    public void registerBlockRenderer(Block block, int meta, String id) {
    }


    /**
     * Initializing mod stuffs that are side-dependent.
     */
    public void modInit() {
    }

    public void construct(FMLConstructionEvent event) {
        
    }

    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.preInit(event);
        ModTileEntities.preInit(event);
        ModItems.preInit(event);

        int packetId = 0;
        ANotSturdyMod.network.registerMessage(PacketServerCommand.Handler.class, PacketServerCommand.class, packetId++, Side.SERVER);
        ANotSturdyMod.network.registerMessage(PacketLocationalGuiAction.Handler.class, PacketLocationalGuiAction.class, packetId++, Side.SERVER);

        TileENComponentBase.initNetwork();
        TileENWirelessTransmitter.initNetwork();
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
        WorldTransmutation.init(event);
    }

    public void serverStarting(FMLServerStartingEvent event) {
        ModCommands.COMMANDS.add(new CommandAnsmUtils());

        ModCommands.registerCommands(event);
    }


    protected void registerBlock(Block block) {
        ModBlocks.BLOCKS.add(block);
    }

    protected void registerBlock(BlockBase block) {
        this.registerBlock((Block) block);
    }

    protected void registerBlock(SimpleBlockBase block) {
        this.registerBlock((BlockBase) block);
        // This will not register its model, as it's only an ItemBlock
        this.registerItem(block.getItemBlock());
    }

    protected void registerBlock(TileBlockBase block) {
        this.registerBlock((SimpleBlockBase) block);
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
