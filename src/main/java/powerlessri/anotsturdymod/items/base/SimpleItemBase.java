package powerlessri.anotsturdymod.items.base;

import powerlessri.anotsturdymod.ANotSturdyMod;


public abstract class SimpleItemBase extends ItemBase {

    public SimpleItemBase(String name) {
        super(name);
    }

    public void registerModel() {
        ANotSturdyMod.proxy.registerItemRenderer(this, 0, "inventory");
    }

}
