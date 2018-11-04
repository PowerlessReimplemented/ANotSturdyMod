package powerlessri.anotsturdymod.handlers.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.mechanics.cobblegen.block.BlockInfiniteCobbleGenerator;
import powerlessri.anotsturdymod.mechanics.decroative.BlockLightCube;
import powerlessri.anotsturdymod.blocks.block.base.BlockBase;
import powerlessri.anotsturdymod.blocks.block.base.SimpleBlockBase;
import powerlessri.anotsturdymod.blocks.block.base.TileBlockBase;
import powerlessri.anotsturdymod.mechanics.cobblegen.tile.TileCobbleGenerator;
import powerlessri.anotsturdymod.commands.CommandAnsmUtils;
import powerlessri.anotsturdymod.handlers.VanillaGuiHandler;
import powerlessri.anotsturdymod.handlers.init.ModBlocks;
import powerlessri.anotsturdymod.handlers.init.ModCommands;
import powerlessri.anotsturdymod.handlers.init.ModItems;
import powerlessri.anotsturdymod.items.ItemExchanger;
import powerlessri.anotsturdymod.items.ItemIlluminator;
import powerlessri.anotsturdymod.items.ItemTransmutationStone;
import powerlessri.anotsturdymod.items.base.ItemBase;
import powerlessri.anotsturdymod.items.base.SimpleItemBase;
import powerlessri.anotsturdymod.items.handler.WorldTransmutation;
import powerlessri.anotsturdymod.network.PacketServerCommand;
import powerlessri.anotsturdymod.network.datasync.PacketClientRequestedData;
import powerlessri.anotsturdymod.network.datasync.PacketSRequestWorld;
import powerlessri.anotsturdymod.mechanics.remote_enetwork.block.BlockEnergyAccessPort;
import powerlessri.anotsturdymod.mechanics.remote_enetwork.block.BlockEnergyController;
import powerlessri.anotsturdymod.mechanics.remote_enetwork.tile.*;
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

    public void preInit(FMLPreInitializationEvent event) {
        registerBlock(BlockEnergyController.INSTANCE);
        registerBlock(new BlockEnergyAccessPort("energy_network_input_port", () -> new TileENAccessPortInput(0, Integer.MAX_VALUE), VanillaGuiHandler.ENERGY_ACCESS_PORT));
        registerBlock(new BlockEnergyAccessPort("energy_network_output_port", () -> new TileENAccessPortOutput(0, 5000), VanillaGuiHandler.ENERGY_ACCESS_PORT));
        registerBlock(new BlockEnergyAccessPort("energy_network_wireless_transmitter", () -> new TileENWirelessTransmitter(0, 320), VanillaGuiHandler.ENERGY_WIRELESS_TRANSMITTER));
        registerBlock(new BlockInfiniteCobbleGenerator("infinite_cobble_generator"));
        registerBlock(new BlockLightCube("light_cube"));

        TileEntity.register("te.energy_network_controller", TileENController.class);
        TileEntity.register("te.energy_network_base", TileENAccessPort.class);
        TileEntity.register("te.energy_network_input", TileENAccessPortInput.class);
        TileEntity.register("te.energy_network_output", TileENAccessPortOutput.class);
        TileEntity.register("te.energy_network_wireless_transmitter", TileENWirelessTransmitter.class);
        TileEntity.register("te.cobble_generator", TileCobbleGenerator.class);


        registerItem(BlockEnergyController.STORAGE_UPGRADE);
        registerItem(BlockEnergyController.IO_UPGRADE);
        registerItem(new ItemTransmutationStone("transmutation_orb"));
        registerItem(new ItemExchanger("exchanger", EMachineLevel.BASIC, 3));
        registerItem(new ItemExchanger("exchanger", EMachineLevel.ADVANCED, 9));
        registerItem(new ItemIlluminator("illuminator", EMachineLevel.BASIC));


        int packetId = 0;
        ANotSturdyMod.genericChannel.registerMessage(PacketServerCommand.Handler.class, PacketServerCommand.class, packetId++, Side.SERVER);
        ANotSturdyMod.genericChannel.registerMessage(PacketSRequestWorld.Handler.class, PacketSRequestWorld.class, packetId++, Side.SERVER);
        ANotSturdyMod.genericChannel.registerMessage(PacketClientRequestedData.Handler.class, PacketClientRequestedData.class, packetId++, Side.CLIENT);

        TileENAccessPort.initNetwork();
        TileENWirelessTransmitter.initNetwork();
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
