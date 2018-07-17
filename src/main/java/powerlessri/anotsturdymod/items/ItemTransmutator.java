package powerlessri.anotsturdymod.items;

import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStone.EnumType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.items.basic.ItemBasicItem;
import powerlessri.anotsturdymod.items.handler.WorldTransmutation;
import powerlessri.anotsturdymod.utils.Utils;

public class ItemTransmutator extends ItemBasicItem {
	
	public ItemTransmutator(String name) {
		super(name, name);
		
		this.setCreativeTab(CreativeTabs.TOOLS);
		this.setMaxStackSize(1);
	}
	
	
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) return EnumActionResult.SUCCESS;
		
		IBlockState pointerBlock = world.getBlockState(pos);
		
		// Get the transmutation that includes the block at player's pointer
		WorldTransmutation transm = WorldTransmutation.getTransmutation(pointerBlock.getBlock());
		int currentBlock = transm.indexOf(pointerBlock.getBlock());
		
		for(BlockPos changingPos : getAffectedBlocks(world, pos, player.isSneaking())) {
			// If the affected pos is not the block at player's point
			// Which means it should not be affected
			if( !(pointerBlock == world.getBlockState(changingPos)) ) {
				//Utils.getLogger().debug("Okay, this is not the same block");
				continue;
			}
			
			int nextIndex = currentBlock + 1;
			// If it's the last one in the array, go back to first one
			if(nextIndex >= transm.members.length)
				nextIndex = 0;
			
			world.setBlockState(changingPos, transm.at(nextIndex).getDefaultState());
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
