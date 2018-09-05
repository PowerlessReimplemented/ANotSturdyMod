package powerlessri.anotsturdymod.items.base;

import net.minecraft.item.Item;
import powerlessri.anotsturdymod.library.utils.Utils;


public abstract class ItemBase extends Item {
    
    public ItemBase(String registryName) {
        this(registryName, registryName);
    }

    public ItemBase(String registryName, String unlocalizedName) {
        this.setRegistryName(registryName);
        this.setUnlocalizedName(Utils.formatRegistryId(unlocalizedName));
    }

}
