package powerlessri.anotsturdymod.items;

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
		
		for(BlockPos changingPos : getAffectedBlocks(world, pos, facing, player.isSneaking())) {
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
	
	
	private Iterable<BlockPos> getAffectedBlocks(World world, BlockPos pos, EnumFacing faceHit, boolean sneaking) {
		//Iterable<BlockPos> iterator;
		
		if(!sneaking) {
		    return BlockPos.getAllInBox(pos, pos);
		}
		
		int xOffset = 0;
		int zOffset = 0;
		
		switch(faceHit) {
		    case UP:
		    case DOWN:
		        return BlockPos.getAllInBox(pos.add(1, 0, 1), pos.add(-1, 0, -1));
		    case NORTH:
		    case SOUTH:
		        xOffset = 1;
		        break;
		    case EAST:
		    case WEST:
		    	zOffset = 1;
		        break;
		}
		
		// On a wall, it's either x changing or z
		return BlockPos.getAllInBox(pos.add(xOffset, 1, zOffset), pos.add(-xOffset, -1, -zOffset));
		
		//return iterator;
	}
	
}
