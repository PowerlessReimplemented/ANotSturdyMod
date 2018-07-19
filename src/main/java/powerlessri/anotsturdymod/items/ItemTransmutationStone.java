package powerlessri.anotsturdymod.items;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.items.basic.ItemBasicItem;
import powerlessri.anotsturdymod.items.handler.WorldTransmutation;

public class ItemTransmutationStone extends ItemBasicItem {
	
	public ItemTransmutationStone() {
		super("transmutation_orb");
		
		this.setCreativeTab(CreativeTabs.TOOLS);
		this.setMaxStackSize(1);
		this.setMaxDamage(5);
		this.setNoRepair();
	}
	
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		
		ItemStack resultStack = player.getHeldItem(hand);
		
		if(world.isRemote)
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, resultStack);
		
		
		//resultStack.damageItem(1, player); // Increase charge (damage) by 1
		resultStack.setItemDamage(resultStack.getItemDamage() + 1);
		
		// If exceeded maximum charge (damage), go back to 0
		if(resultStack.getItemDamage() > resultStack.getMaxDamage()) {
			resultStack.setItemDamage(0);
		}
		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, resultStack);
	}
	
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote)
			return EnumActionResult.SUCCESS;
		
		IBlockState pointerBlock = world.getBlockState(pos);
		IBlockState next = WorldTransmutation.getTransmutationNext(world, pos, pointerBlock.getBlock());
		
		// Didn't found any matched transmutation
		if(next == null)
			return EnumActionResult.FAIL;
		
		for(BlockPos changingPos : getAffectedBlocks(world, pos, facing, player.getHeldItem(hand).getItemDamage(), player.isSneaking())) {
			// If the affected pos is not the block at player's point
			// Which means it should not be affected
			if( !(pointerBlock == world.getBlockState(changingPos)) ) {
				continue;
			}
			
			world.setBlockState(changingPos, next);
		}
		
		return EnumActionResult.SUCCESS;
		
	}
	
	
	private Iterable<BlockPos> getAffectedBlocks(World world, BlockPos pos, EnumFacing faceHit, int charge, boolean sneaking) {
		
		if(sneaking) {
		    return BlockPos.getAllInBox(pos, pos);
		}
		
		switch(faceHit) {
		    case UP:
		    case DOWN:
		        return BlockPos.getAllInBox(pos.add(charge, 0, charge), pos.add(-charge, 0, -charge));
		    
		    // On a wall, it's either x changing or z
		    case NORTH:
		    case SOUTH:
		        return BlockPos.getAllInBox(pos.add(charge, charge, 0), pos.add(-charge, -charge, 0));
		    case EAST:
		    case WEST:
		    	return BlockPos.getAllInBox(pos.add(0, charge, charge), pos.add(0, -charge, -charge));
		}
		
		return BlockPos.getAllInBox(pos, pos);
	}
	
}
