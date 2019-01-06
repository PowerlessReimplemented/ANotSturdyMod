package powerlessri.anotsturdymod.library.item.base;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.varia.Reference;

public abstract class ItemBase extends Item {

    public ItemBase(String registryName) {
        this.setRegistryName(new ResourceLocation(Reference.MODID, registryName));
        this.setTranslationKey(this.getRegistryName().toString());
    }

}
