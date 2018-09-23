package powerlessri.anotsturdymod.world;

import java.util.ArrayList;

import powerlessri.anotsturdymod.blocks.BlockEnergyController.TileEnergyNetworkController;

/** Unlike {@link AnsmSaveData}, these data will not get written onto disk. They will generated when loading in a save. */
public class AnsmWorldTempData {
    
    /** A list of loaded & working (assigned with a channel) controller tile entities. */
    public ArrayList<TileEnergyNetworkController> tiles = new ArrayList<>();
    
}
