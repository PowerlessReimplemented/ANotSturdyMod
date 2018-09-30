package powerlessri.anotsturdymod.items;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.handlers.init.ModBlocks;
import powerlessri.anotsturdymod.items.base.SimpleItemBase;
import powerlessri.anotsturdymod.library.EMachineLevel;


public class ItemIlluminator extends SimpleItemBase {

    public ItemIlluminator(String name, EMachineLevel level) {
        super(level.getName() + "_" + name);
        
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing,
            float hitX, float hitY, float hitZ) {
        
        if(world.isRemote) {
            return EnumActionResult.SUCCESS;
        }
        
        BlockPos placementPos = pos.offset(facing);
        
        IBlockState placementOriginal = world.getBlockState(placementPos);
        Block placementParent = placementOriginal.getBlock();
        
        if(placementParent.isReplaceable(world, placementPos)) {
            world.setBlockState(placementPos, ModBlocks.LIGHT_CUBE.getDefaultState());
            return EnumActionResult.SUCCESS;
        }
        
        return EnumActionResult.FAIL;
    }

}
