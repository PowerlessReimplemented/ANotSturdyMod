package powerlessri.anotsturdymod.blocks.basic;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.blocks.base.BlockBase;
import powerlessri.anotsturdymod.library.enums.EHarvestLevel;
import powerlessri.anotsturdymod.library.enums.EHarvestTool;
import powerlessri.anotsturdymod.library.utils.Utils;


/** Base class for blocks that has an item form. */
public class BlockBasicBlock extends BlockBase {

    protected final ItemBlock itemBlock;

    public BlockBasicBlock(String name, Material material) {
        super(name, material);

        this.itemBlock = new ItemBlock(this);
        this.itemBlock.setRegistryName(this.getRegistryName());
        this.itemBlock.setUnlocalizedName(Utils.formatRegistryId(name));
    }

    public void registerModel() {
        ANotSturdyMod.proxy.registerBlockRenderer(this, 0, "");
        ANotSturdyMod.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    public void setHarvestLevel(EHarvestTool tool, EHarvestLevel level) {
        this.setHarvestLevel(tool.getName(), level.numerical());
    }

    public void setMaxStackSize(int size) {
        this.itemBlock.setMaxStackSize(size);
    }
    
    

    public ItemBlock getItemBlock() {
        return this.itemBlock;
    }

}
