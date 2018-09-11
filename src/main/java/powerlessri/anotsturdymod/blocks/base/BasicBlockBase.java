package powerlessri.anotsturdymod.blocks.base;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IStringSerializable;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.library.INumberSerializable;


/**
 * Base class for blocks that has an item form.
 */
// <registryName> is in the form of DOMAIN:PATH
//
// Some notes for creating blockstate/model file for the block:
//     + blockstate file should be named <registryName>.json
//     + item model file should be named <registryName>.json too
public abstract class BasicBlockBase extends BlockBase {
    
    public enum EHarvestTool implements IStringSerializable {
        PICKAXE("pickaxe"), AXE("axe"), SHOVEL("shovel"), SWORD("sword");

        private String name;

        private EHarvestTool(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }

    }
    
    public enum EHarvestLevel implements INumberSerializable {
        
        WOODEN(0), 
        STONE(1), 
        IRON(2), 
        DIAMOND(2);

        private int number;

        private EHarvestLevel(int numericalId) {
            this.number = numericalId;
        }

        @Override
        public int getNumber() {
            return this.number;
        }

    }
    
    

    protected final ItemBlock itemBlock;

    public BasicBlockBase(String name, Material material) {
        super(name, material);

        this.itemBlock = new ItemBlock(this);
        this.itemBlock.setRegistryName(this.getRegistryName());
        this.itemBlock.setUnlocalizedName(this.getRegistryName().toString());
    }

    public void registerModel() {
        // You don't actually register block renderer yourself, just for oCd reasons
        ANotSturdyMod.proxy.registerBlockRenderer(this, 0, "");
        ANotSturdyMod.proxy.registerItemRenderer(this.getItemBlock(), 0, "inventory");
    }



    public void setHarvestLevel(EHarvestTool tool, EHarvestLevel level) {
        this.setHarvestLevel(tool.getName(), level.getNumber());
    }

    public void setMaxStackSize(int size) {
        this.itemBlock.setMaxStackSize(size);
    }



    public ItemBlock getItemBlock() {
        return this.itemBlock;
    }

}
