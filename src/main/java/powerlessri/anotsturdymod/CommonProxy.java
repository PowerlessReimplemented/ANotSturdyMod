package powerlessri.anotsturdymod;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import powerlessri.anotsturdymod.blocks.BlockEnergyAccessPort;
import powerlessri.anotsturdymod.blocks.BlockEnergyController;
import powerlessri.anotsturdymod.blocks.BlockInfiniteCobbleGenerator;
import powerlessri.anotsturdymod.blocks.BlockLightCube;
import powerlessri.anotsturdymod.blocks.base.BlockBase;
import powerlessri.anotsturdymod.blocks.base.SimpleBlockBase;
import powerlessri.anotsturdymod.blocks.base.TileBlockBase;
import powerlessri.anotsturdymod.blocks.tile.*;
import powerlessri.anotsturdymod.commands.CommandAnsmUtils;
import powerlessri.anotsturdymod.handlers.CommonReloadHandler;
import powerlessri.anotsturdymod.handlers.ModGuiHandler;
import powerlessri.anotsturdymod.handlers.init.ModBlocks;
import powerlessri.anotsturdymod.handlers.init.ModCommands;
import powerlessri.anotsturdymod.handlers.init.ModItems;
import powerlessri.anotsturdymod.items.ItemExchanger;
import powerlessri.anotsturdymod.items.ItemIlluminator;
import powerlessri.anotsturdymod.items.ItemTransmutationStone;
import powerlessri.anotsturdymod.items.base.ItemBase;
import powerlessri.anotsturdymod.items.base.SimpleItemBase;
import powerlessri.anotsturdymod.items.handler.WorldTransmutation;
import powerlessri.anotsturdymod.library.EMachineLevel;
import powerlessri.anotsturdymod.library.utils.Utils;
import powerlessri.anotsturdymod.network.PacketServerCommand;
import powerlessri.anotsturdymod.network.datasync.PacketClientRequestedData;
import powerlessri.anotsturdymod.network.datasync.PacketSRequestWorld;

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
        registerBlock(BlockEnergyController.INSTANCE);
        registerBlock(new BlockEnergyAccessPort("energy_network_input_port", 5000, BlockEnergyAccessPort.TYPE_ACCESS_PORT_IN, ModGuiHandler.ENERGY_ACCESS_PORT));
        registerBlock(new BlockEnergyAccessPort("energy_network_output_port", 5000, BlockEnergyAccessPort.TYPE_ACCESS_PORT_OUT, ModGuiHandler.ENERGY_ACCESS_PORT));
        registerBlock(new BlockEnergyAccessPort("energy_network_wireless_transmitter", 80, BlockEnergyAccessPort.TYPE_WIRELESS_EMITTER, ModGuiHandler.ENERGY_WIRELESS_TRANSMITTER) {
            @Override
            public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
                if(world.isRemote) {
                    return true;
                }

                if(player.isSneaking()) {
                    if(player.getHeldItem(hand).isEmpty()) {
                        TileENWirelessTransmitter tile = (TileENWirelessTransmitter) world.getTileEntity(pos);
                        String msg =
                                Utils.readFromLang("gui.ansm:wireless_transmitter.text.amountSupportingTiles.1") +
                                " " +
                                tile.getAmountSupportingTiles() +
                                " " +
                                Utils.readFromLang("gui.ansm:wireless_transmitter.text.amountSupportingTiles.2");

                        player.sendMessage(new TextComponentString(msg));
                        return true;
                    }
                    return false;
                }

                return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
            }
        });
        registerBlock(new BlockInfiniteCobbleGenerator("infinite_cobble_generator"));
        registerBlock(new BlockLightCube("light_cube"));
        
        TileEntity.register("te.energy_network_controller", TileENController.class);
        TileEntity.register("te.energy_network_input", TileENAccessPort.class);
        TileEntity.register("te.energy_network_output", TileENAccessPortOutput.class);
        TileEntity.register("te.energy_network_wireless_transmitter", TileENWirelessTransmitter.class);
        TileEntity.register("te.cobble_generator", TileCobbleGenerator.class);

        TileENAccessPort.initNetwork();
        TileENWirelessTransmitter.initNetwork();

        registerItem(BlockEnergyController.STORAGE_UPGRADE);
        registerItem(new ItemTransmutationStone("transmutation_orb"));
        registerItem(new ItemExchanger("exchanger", EMachineLevel.BASIC, 1));
        registerItem(new ItemExchanger("exchanger", EMachineLevel.ADVANCED, 4));
        registerItem(new ItemIlluminator("illuminator", EMachineLevel.BASIC));

        int packetId = 0;
        ANotSturdyMod.genericChannel.registerMessage(PacketServerCommand.Handler.class, PacketServerCommand.class, packetId++, Side.SERVER);
        ANotSturdyMod.genericChannel.registerMessage(PacketSRequestWorld.Handler.class, PacketSRequestWorld.class, packetId++, Side.SERVER);
        ANotSturdyMod.genericChannel.registerMessage(PacketClientRequestedData.Handler.class, PacketClientRequestedData.class, packetId++, Side.CLIENT);
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
