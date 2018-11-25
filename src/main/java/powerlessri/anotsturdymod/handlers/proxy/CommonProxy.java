package powerlessri.anotsturdymod.handlers.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
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

    public void construct(FMLConstructionEvent event) {
    }

    public void preInit(FMLPreInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(ANotSturdyMod.instance, ANotSturdyMod.gui);
        ANotSturdyMod.gui.init(event.getAsmData());
        
        ModBlocks.preInit(event);
        ModTileEntities.preInit(event);
        ModItems.preInit(event);
        ModCommands.preInit(event);

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
        ModCommands.registerCommands(event);
    }
    
}
