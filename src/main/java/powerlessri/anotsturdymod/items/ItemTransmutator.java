package powerlessri.anotsturdymod.items;

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

public class ItemTransmutator extends ItemBasicItem {
	
	public ItemTransmutator(String name) {
		super(name, name);
		
		this.setCreativeTab(CreativeTabs.TOOLS);
		this.setMaxStackSize(1);
	}
	
	
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) return EnumActionResult.SUCCESS;
		
		IBlockState STONE = Blocks.STONE.getBlockState().getBaseState();
		IBlockState COBBLESTONE = Blocks.COBBLESTONE.getBlockState().getBaseState();
		
		
		for(BlockPos changingPos : getAffectedBlocks(world, pos, player.isSneaking())) {
			IBlockState target = world.getBlockState(changingPos);
			if(target == STONE) {
				world.setBlockState(changingPos, COBBLESTONE);
			} else if(target == COBBLESTONE) {
				world.setBlockState(changingPos, STONE);
			}
		}
		
		return EnumActionResult.SUCCESS;
		
	}
	
	private Iterable<BlockPos> getAffectedBlocks(World world, BlockPos pos, boolean sneaking) {
		Iterable<BlockPos> iterator;
		
		if(sneaking) {
			iterator = BlockPos.getAllInBox(pos.add(1, 0, 1), pos.add(-1, 0, -1));
		} else {
			iterator = BlockPos.getAllInBox(pos, pos);
		}
		
		return iterator;
	}
	
}
