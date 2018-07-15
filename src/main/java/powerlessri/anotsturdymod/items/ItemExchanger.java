package powerlessri.anotsturdymod.items;

import com.jcraft.jorbis.Block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.items.basic.ItemBasicItem;

public class ItemExchanger extends ItemBasicItem {
	
	public ItemExchanger(String registry_name, String unlocalized_name, CreativeTabs tab) {
		super(registry_name, unlocalized_name, tab);
	}
	public ItemExchanger(String name, CreativeTabs tab) {
		this(name, name, tab);
	}
	
	
	
	@Override
	public int getItemStackLimit() {
		return 1;
	}
	
}
