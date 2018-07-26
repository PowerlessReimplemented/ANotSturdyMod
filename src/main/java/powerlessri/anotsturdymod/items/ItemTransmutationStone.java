package powerlessri.anotsturdymod.items;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.items.basic.ItemBasicItem;
import powerlessri.anotsturdymod.items.handler.WorldTransmutation;
import powerlessri.anotsturdymod.utils.handlers.enums.EDataType;
import powerlessri.anotsturdymod.utils.handlers.interfaces.IEnumNBTTags;

public class ItemTransmutationStone extends ItemBasicItem {
	
	public ItemTransmutationStone() {
		super("transmutation_orb");
		
		this.setCreativeTab(CreativeTabs.TOOLS);
		this.setMaxStackSize(1);
	}
	
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
	    String name = super.getItemStackDisplayName(stack);
	    
	    this.updateItemNBT(stack);
	    int sideLength = this.getTagBValue(stack, EnumTags.CHARGE) * 2 + 1;
	    name = name + " (" + sideLength + "*" + sideLength + ")";
	    
	    return name;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		
		ItemStack resultStack = player.getHeldItem(hand);
		
		if(world.isRemote)
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, resultStack);
		
		this.updateItemNBT(resultStack);
		this.cycleTagBValue(resultStack, EnumTags.CHARGE, (byte) 1);
		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, resultStack);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote)
			return EnumActionResult.SUCCESS;
		IBlockState pointerBlock = world.getBlockState(pos);
		IBlockState next = WorldTransmutation.getTransmutationNext(world, pos, pointerBlock);
		
		// Didn't found any matched transmutation
		if(next == null)
			return EnumActionResult.FAIL;
		
		int charge = (int) this.getTagBValue(player.getHeldItem(hand), EnumTags.CHARGE);
		for(BlockPos changingPos : getAffectedBlocks(world, pos, facing, charge, player.isSneaking())) {
			// If the affected pos is not the block at player's point
			// Which means it should not be affected
			if(pointerBlock != world.getBlockState(changingPos)) {
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
	
	private void cycleTagBValue(ItemStack stack, EnumTags tag, byte by) {
        if(stack.getTagCompound().getByte(tag.key) >= tag.max) {
            stack.getTagCompound().setByte(tag.key, (byte) 0);
            return;
        }
        stack.getTagCompound().setByte(tag.key, (byte) (getTagBValue(stack, tag) + by));
    }
	private byte getTagBValue(ItemStack stack, EnumTags tag) {
        return stack.getTagCompound().getByte(tag.key);
    }
    
	
	
	
	private static enum EnumTags implements IEnumNBTTags {
        
        CHARGE("charge", 0, 5, EDataType.BYTE);
        
	    // You should never modify these!
        EDataType type;
        String key;
        String defaultStr;
        int defaultValue;
        int max;
        
        private EnumTags(String key, int defaultVal, int max, EDataType type) {
            this.key = key;
            this.defaultValue = defaultVal;
            this.max = max;
            this.type = type;
        }
        private EnumTags(String key, String defaultStr, EDataType type) {
            this.key = key;
            this.defaultStr = defaultStr;
            this.type = type;
        }
        
    }
    
	/** Set the stack's NBT to default state */
    public void updateItemNBT(ItemStack stack) {
        if(stack.getTagCompound() == null) {
            stack.setTagCompound(this.defaultNBTTag());
        } else {
            NBTTagCompound tag = stack.getTagCompound();
            
            if(tag.hasKey(EnumTags.CHARGE.key))
                this.setTag(tag, EnumTags.CHARGE);
        }
    }
    
    /** Get the default NBT an stack would have */
    public NBTTagCompound defaultNBTTag() {
        NBTTagCompound tag = new NBTTagCompound();
        
        this.setTag(tag, EnumTags.CHARGE);
        
        return tag;
    }
    private void setTag(NBTTagCompound tag, EnumTags data) {
        switch(data.type) {
            case BYTE:
                tag.setByte(data.key, (byte) data.defaultValue);
                break;
            case INT:
                tag.setInteger(data.key, data.defaultValue);
                break;
            case STRING:
                tag.setString(data.key, data.defaultStr);
                break;
            
            default: break;
        }
    }
	
}
