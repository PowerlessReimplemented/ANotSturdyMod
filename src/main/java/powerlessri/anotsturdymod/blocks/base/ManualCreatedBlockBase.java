package powerlessri.anotsturdymod.blocks.base;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.init.ModBlocks;
import powerlessri.anotsturdymod.init.ModItems;


@Deprecated
public abstract class ManualCreatedBlockBase extends BlockBase {

    public ManualCreatedBlockBase(String registry_name, String unlocalized_name, Material material) {
        super(registry_name, unlocalized_name, material);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    public ManualCreatedBlockBase(String name, Material material) {
        this(name, name, material);
    }

    public ManualCreatedBlockBase(String registry_name, String unlocalized_name) {
        this(registry_name, unlocalized_name, Material.ROCK);
    }

    public ManualCreatedBlockBase(String common_name) {
        this(common_name, common_name);
    }

    public void registerModel() {
        ANotSturdyMod.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

}
