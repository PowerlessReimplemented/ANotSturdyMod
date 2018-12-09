package powerlessri.anotsturdymod.library.block.base;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.varia.general.IIntegerSerializable;


/**
 * Base class for blocks that has an item form.
 */
// <registryName> is in the form of DOMAIN:PATH
//
// Some notes for creating blockstate/model file for the block:
//     + blockstate file should be named <registryName>.json
//     + item model file should be named <registryName>.json too
public abstract class SimpleBlockBase extends BlockBase {

    public static enum EHarvestTool implements IStringSerializable {
        PICKAXE("pickaxe"),
        AXE("axe"),
        SHOVEL("shovel"),
        SWORD("sword");

        public final String name;

        private EHarvestTool(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }

    }

    public static enum EHarvestLevel implements IIntegerSerializable {

        WOODEN(0),
        STONE(1),
        IRON(2),
        DIAMOND(2);

        public final int level;

        private EHarvestLevel(int numericalId) {
            this.level = numericalId;
        }

        @Override
        public int getInt() {
            return this.level;
        }

    }


    protected final ItemBlock itemBlock;

    public SimpleBlockBase(String name, Material material) {
        super(name, material);

        this.itemBlock = new ItemBlock(this);
        this.itemBlock.setRegistryName(this.getRegistryName());
        this.itemBlock.setUnlocalizedName(this.getRegistryName().toString());
    }
    
    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelLoader.setCustomModelResourceLocation(getItemBlock(), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }


    public void setHarvestLevel(EHarvestTool tool, EHarvestLevel level) {
        this.setHarvestLevel(tool.getName(), level.getInt());
    }

    public void setMaxStackSize(int size) {
        this.itemBlock.setMaxStackSize(size);
    }


    public ItemBlock getItemBlock() {
        return this.itemBlock;
    }

}
