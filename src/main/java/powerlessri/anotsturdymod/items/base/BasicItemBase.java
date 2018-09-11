package powerlessri.anotsturdymod.items.base;

import powerlessri.anotsturdymod.ANotSturdyMod;


public abstract class BasicItemBase extends ItemBase {

    public BasicItemBase(String name) {
        super(name);
    }

    public void registerModel() {
        ANotSturdyMod.proxy.registerItemRenderer(this, 0, "inventory");
    }

}
