package powerlessri.anotsturdymod.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import powerlessri.anotsturdymod.items.ItemExchanger;
import powerlessri.anotsturdymod.items.ItemIlluminator;
import powerlessri.anotsturdymod.items.ItemTransmutationStone;


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

}
