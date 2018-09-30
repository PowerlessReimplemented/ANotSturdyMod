package powerlessri.anotsturdymod.handlers.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import powerlessri.anotsturdymod.blocks.BlockInfiniteCobbleGenerator;
import powerlessri.anotsturdymod.blocks.BlockLightCube;


public class ModBlocks {

    private ModBlocks() {
    }

    public static final List<Block> BLOCKS = new ArrayList<Block>();
    
    @ObjectHolder("ansm:light_cube")
    public static final BlockLightCube LIGHT_CUBE = null;
    
    @ObjectHolder("ansm:infinite_cobble_generator")
    public static final BlockInfiniteCobbleGenerator INFINTE_COBBLE_GENERATOR = null;

}
