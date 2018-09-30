package powerlessri.anotsturdymod;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import powerlessri.anotsturdymod.blocks.container.ContainerEnergyAccessPort;
import powerlessri.anotsturdymod.handlers.ClientReloadHandler;
import powerlessri.anotsturdymod.network.PacketClientRequestedData;


public class ClientProxy extends CommonProxy {

    /**
     * Register an item model using an item object and a meta number.
     */
    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        super.registerItemRenderer(item, meta, id);

        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));

    }

    // This method is here just for oCd reasons, it doesn't do anything
    @Override
    public void registerBlockRenderer(Block block, int meta, String id) {
        super.registerBlockRenderer(block, meta, id);
    }



    @Override
    public void modInit() {
        this.reloadHandler = new ClientReloadHandler();
    }
    
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public void serverStarting(FMLServerStartingEvent event) {
        super.serverStarting(event);
    }

}
