package powerlessri.anotsturdymod.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import powerlessri.anotsturdymod.items.ItemExchanger;
import powerlessri.anotsturdymod.items.ItemLightingPlacer;
import powerlessri.anotsturdymod.items.ItemTransmutationStone;
import powerlessri.anotsturdymod.items.basic.ItemBasicItem;
import powerlessri.anotsturdymod.library.exceptions.IllegalArgumentsException;


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
    
    @ObjectHolder("ansm:basic_lighting_placer")
    public static final ItemLightingPlacer BASIC_LIGHTING_PLACER = null;

    /**
     * Add array of items to the game.
     *
     * @param items (Item[]) The array of items that will be registered.
     */
    @Deprecated
    public static void registerItems(Item[] items) {
        for(int i = 0; i < items.length; i++) {
            ITEMS.add(items[i]);
        }
    }

    /**
     * This is the base method of registerItems(), should ONLY called when you want
     * to register items with different registryName and unlocalizedName.
     *
     * @param registry_names (String[]) The registryName that will be given to the
     * items.
     * @param unlocalized_names (String[]) The unlocalizedName that will be given to
     * the items.
     * @param tab (CreativeTabs) creative tab that items will belong to.
     *
     * @throws IllegalArguments When registry_names.length does not equal to
     * unlocalized_names.length. (didn't give enough information to register all
     * items)
     */
    public static void registerItems(String[] registry_names, String[] unlocalized_names, CreativeTabs tab)
            throws IllegalArgumentsException {
        if(registry_names.length != unlocalized_names.length) {
            throw new IllegalArgumentsException();
        }

        for(int i = 0; i < registry_names.length; i++) {
            ITEMS.add(new ItemBasicItem(registry_names[i], unlocalized_names[i]).setCreativeTab(tab));
        }
    }

    /**
     * In most of the case (aka. registryName == unlocalizedName), you should use
     * this method: sharing registryName and unlocalizedName, and give items a
     * custom creative tab.
     *
     * @param common_names (String[]) Both registryName and unlocalizedName for the
     * items.
     * @param tab (CreativeTabs) Creative tab that items will belong to.
     */
    public static void registerItems(String[] common_names, CreativeTabs tab) {
        registerItems(common_names, common_names, tab);
    }

}
