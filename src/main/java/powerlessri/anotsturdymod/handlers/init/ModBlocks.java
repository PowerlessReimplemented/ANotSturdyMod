package powerlessri.anotsturdymod.handlers.init;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import powerlessri.anotsturdymod.mechanisms.cobblegen.block.BlockInfiniteCobbleGenerator;
import powerlessri.anotsturdymod.mechanisms.decroative.BlockLightCube;
import powerlessri.anotsturdymod.mechanisms.remote_enetwork.block.BlockEnergyAccessPort;
import powerlessri.anotsturdymod.mechanisms.remote_enetwork.block.BlockEnergyController;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {

    private ModBlocks() {
    }

    public static final List<Block> BLOCKS = new ArrayList<Block>();

    @ObjectHolder("ansm:light_cube")
    public static final BlockLightCube LIGHT_CUBE = null;

    @ObjectHolder("ansm:infinite_cobble_generator")
    public static final BlockInfiniteCobbleGenerator INFINTE_COBBLE_GENERATOR = null;


    @ObjectHolder("ansm:energy_network_controller")
    public static final BlockEnergyController EN_CONTROLLER = null;

    @ObjectHolder("ansm:energy_network_input_port")
    public static final BlockEnergyAccessPort EN_INPUT_PORT = null;

    @ObjectHolder("ansm:energy_network_output_port")
    public static final BlockEnergyAccessPort EN_OUTPUT_PORT = null;

    @ObjectHolder("ansm:energy_network_wireless_transmitter")
    public static final BlockEnergyAccessPort EN_WIRELESS_TRANSMITTER = null;

}
