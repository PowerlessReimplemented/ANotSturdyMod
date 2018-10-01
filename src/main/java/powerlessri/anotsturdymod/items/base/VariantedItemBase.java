package powerlessri.anotsturdymod.items.base;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import powerlessri.anotsturdymod.ANotSturdyMod;

public class VariantedItemBase extends SimpleItemBase {

    // unlocalizedName for each item.
    public final List<String> VARIANT_NAMES = new ArrayList<String>();

    public VariantedItemBase(String registryName, CreativeTabs tab) {
        super(registryName);

        this.setCreativeTab(tab);
    }

    public void registerModel() {
        ANotSturdyMod.proxy.registerItemRenderer(this, 0, "inventory");
    }

    public void addVariant(String unlocalized_name) {
        VARIANT_NAMES.add(unlocalized_name);
    }

    public void removeVariant(String unlocalized_name) {
        VARIANT_NAMES.remove(unlocalized_name);
    }

    public void removeVariant(int index) {
        VARIANT_NAMES.remove(index);
    }

    public int getVariantAmount() {
        return VARIANT_NAMES.size();
    }

    public String getVariant(int index) {
        return VARIANT_NAMES.get(index);
    }

    public boolean contains(String unlocalized_name) {
        return VARIANT_NAMES.contains(unlocalized_name);
    }

}
