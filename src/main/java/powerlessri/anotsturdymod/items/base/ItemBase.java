package powerlessri.anotsturdymod.items.base;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.library.Reference;

public abstract class ItemBase extends Item {

    public ItemBase(String registryName) {
        this(registryName, registryName);
    }

    public ItemBase(String registryName, String unlocalizedName) {
        this.setRegistryName(new ResourceLocation(Reference.MODID, registryName));
        this.setUnlocalizedName(this.getRegistryName().toString());
    }

}
