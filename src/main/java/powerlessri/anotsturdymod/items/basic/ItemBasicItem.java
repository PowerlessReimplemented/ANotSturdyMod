package powerlessri.anotsturdymod.items.basic;

import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.items.base.ItemBase;


public class ItemBasicItem extends ItemBase {

    public ItemBasicItem(String name) {
        super(name, name);
    }

    public void registerModel() {
        ANotSturdyMod.proxy.registerItemRenderer(this, 0, "inventory");
    }

}
