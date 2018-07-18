package powerlessri.anotsturdymod.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.utils.Reference;

public abstract class BlockBase extends Block {
	
	public BlockBase(String registry_name, String unlocalized_name, Material material) {
		super(material);
		
		this.setRegistryName(new ResourceLocation(Reference.MODID, registry_name));
		this.setUnlocalizedName(Reference.MODID + ":" + registry_name);
		//this.setUnlocalizedName(unlocalized_name);
	}
	
}
