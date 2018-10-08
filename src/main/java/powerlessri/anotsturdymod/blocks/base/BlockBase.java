package powerlessri.anotsturdymod.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.library.utils.Reference;


public abstract class BlockBase extends Block {

    public BlockBase(String registryName, Material material) {
        this(registryName, registryName, material);
    }

    public BlockBase(String registryName, String unlocalizedName, Material material) {
        super(material);

        this.setRegistryName(new ResourceLocation(Reference.MODID, registryName));
        this.setUnlocalizedName(this.getRegistryName().toString());
    }

}
