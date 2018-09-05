package powerlessri.anotsturdymod.blocks.basic;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.blocks.base.BlockBase;
import powerlessri.anotsturdymod.library.enums.EHarvestLevel;
import powerlessri.anotsturdymod.library.enums.EHarvestTool;


public class BlockBasicBlock extends BlockBase {

    public BlockBasicBlock(String name, Material material) {
        super(name, material);
    }

    public void registerModel() {
        ANotSturdyMod.proxy.registerBlockRenderer(this, 0, "");
        ANotSturdyMod.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    public void setHarvestLevel(EHarvestTool tool, EHarvestLevel level) {
        this.setHarvestLevel(tool.getName(), level.numerical());
    }

}
