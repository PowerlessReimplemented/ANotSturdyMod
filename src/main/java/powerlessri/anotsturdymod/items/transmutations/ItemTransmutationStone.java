package powerlessri.anotsturdymod.items.transmutations;

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
import powerlessri.anotsturdymod.handlers.init.RegistryItem;
import powerlessri.anotsturdymod.library.item.base.SimpleItemBase;
import powerlessri.anotsturdymod.varia.general.PosExtractor;
import powerlessri.anotsturdymod.varia.tags.TagUtils;

public class ItemTransmutationStone extends SimpleItemBase {

    @RegistryItem
    public static final ItemTransmutationStone TRANSMUTATION_ORB = new ItemTransmutationStone("transmutation_orb");

    private static final String TAG_CHARGE = "charge";
    private static final int MAX_CHARGE = 5;


    public ItemTransmutationStone(String name) {
        super(name);

        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setMaxStackSize(1);
    }

    
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        NBTTagCompound tag = TagUtils.getOrCreateTag(stack);
        int sideLength = tag.getByte(TAG_CHARGE) * 2 + 1;
        return super.getItemStackDisplayName(stack) + " (" + sideLength + "*" + sideLength + ")";
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        
        if (world.isRemote) {
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }

        if (player.isSneaking()) {
            NBTTagCompound tag = TagUtils.getOrCreateTag(stack);
            cycleByteTag(tag, (byte) 1);

            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }

        return new ActionResult<>(EnumActionResult.FAIL, stack);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (world.isRemote) {
            return EnumActionResult.SUCCESS;
        }

        IBlockState selectedBlock = world.getBlockState(pos);
        IBlockState replacementBlock = WorldTransmutation.findTransmutationContains(selectedBlock).findNextBlockState(selectedBlock, player.isSneaking() ? -1 : 1);

        // Didn't find any matched transmutation
        if (replacementBlock == null) {
            return EnumActionResult.FAIL;
        }

        ItemStack orb = player.getHeldItem(hand);
        NBTTagCompound tag = TagUtils.getOrCreateTag(orb);
        int charge = tag.getByte(TAG_CHARGE);

        for (BlockPos changingPos : PosExtractor.posOnPlane(pos, facing, charge)) {
            if (selectedBlock == world.getBlockState(changingPos)) {
                world.setBlockState(changingPos, replacementBlock);
            }
        }

        return EnumActionResult.SUCCESS;
    }

    private void cycleByteTag(NBTTagCompound tag, byte increase) {
        byte originalVal = tag.getByte(TAG_CHARGE);

        if (originalVal >= MAX_CHARGE) {
            tag.setByte(TAG_CHARGE, (byte) 0);
        } else {
            tag.setByte(TAG_CHARGE, (byte) (originalVal + increase));
        }
    }

}
