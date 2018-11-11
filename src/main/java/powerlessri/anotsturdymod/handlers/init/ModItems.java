package powerlessri.anotsturdymod.handlers.init;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import powerlessri.anotsturdymod.items.exchangers.ItemExchanger;
import powerlessri.anotsturdymod.items.ItemIlluminator;
import powerlessri.anotsturdymod.items.transmutations.ItemTransmutationStone;
import powerlessri.anotsturdymod.items.ItemUpgrade;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    private ModItems() {
    }

    public static final List<Item> ITEMS = new ArrayList<Item>();

    @ObjectHolder("ansm:transmutation_orb")
    public static final ItemTransmutationStone TRANSMUTATION_ORB = null;

    @ObjectHolder("ansm:basic_exchanger")
    public static final ItemExchanger BASIC_EXCHANGER = null;

    @ObjectHolder("ansm:advanced_exchanger")
    public static final ItemExchanger ADVANCED_EXCHAGNER = null;

    @ObjectHolder("ansm:basic_illuminator")
    public static final ItemIlluminator BASIC_ILLUMINATOR = null;


    @ObjectHolder("ansm:energy_network_storage_upgrade")
    public static final ItemUpgrade ENERGY_STORAGE_UPGRADE = null;

    @ObjectHolder("ansm:energy_network_io_upgrade")
    public static final ItemUpgrade ENERGY_IO_UPGRADE = null;

}
