package powerlessri.anotsturdymod.library.handlers;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import powerlessri.anotsturdymod.world.AnsmSavedData;

@Mod.EventBusSubscriber
public class WorldEventHandler {

    private WorldEventHandler() {
    }

    @Mod.EventHandler
    public static void onWorldLoaded(WorldEvent.Load event) {
        // Initialize saved data
        AnsmSavedData.fromWorld(event.getWorld());
    }

}
