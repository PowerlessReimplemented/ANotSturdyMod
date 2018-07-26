package powerlessri.anotsturdymod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.items.basic.ItemBasicItem;
import powerlessri.anotsturdymod.utils.handlers.enums.EMachineLevel;

public class ItemExchanger extends ItemBasicItem {
	
	public ItemExchanger(EMachineLevel level) {
		super(level.getName() + "_exchanger");
		
		this.setCreativeTab(CreativeTabs.TOOLS);
		this.setMaxStackSize(1);
	}
	
	
	
	@Override 
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
	    
	    if(world.isRemote)
	        return EnumActionResult.SUCCESS;
	    
	    //Pair.of(0, 0);
	    
	    return EnumActionResult.SUCCESS;
	}
	
	
}
