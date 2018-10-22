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
import powerlessri.anotsturdymod.items.base.SimpleItemBase;
import powerlessri.anotsturdymod.items.handler.WorldTransmutation;
import powerlessri.anotsturdymod.varia.tags.IEnumNBTTags;
import powerlessri.anotsturdymod.varia.tags.ITagBasedItem;
import powerlessri.anotsturdymod.varia.tags.TagUtils;
import powerlessri.anotsturdymod.world.utils.PosExtractor;

public class ItemTransmutationStone extends SimpleItemBase implements ITagBasedItem {

    public ItemTransmutationStone(String name) {
        super(name);

        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setMaxStackSize(1);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String name = super.getItemStackDisplayName(stack);

        this.updateItemTag(stack);
        int sideLength = this.getByteTag(stack, EnumTags.CHARGE) * 2 + 1;
        name = name + " (" + sideLength + "*" + sideLength + ")";

        return name;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

        ItemStack resultStack = player.getHeldItem(hand);

        if (world.isRemote) {
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, resultStack);
        }


        if (player.isSneaking()) {
            this.updateItemTag(resultStack);
            this.cycleByteTag(resultStack, EnumTags.CHARGE, (byte) 1);

            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, resultStack);
        }

        return new ActionResult<ItemStack>(EnumActionResult.FAIL, resultStack);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing,
                                      float hitX, float hitY, float hitZ) {

        if (world.isRemote) {
            return EnumActionResult.SUCCESS;
        }

        IBlockState pointerBlock = world.getBlockState(pos);
        IBlockState next = WorldTransmutation.getTransmutationNext(world, pos, pointerBlock,
                player.isSneaking() ? -1 : 1);

        // Didn't find any matched transmutation
        if (next == null)
            return EnumActionResult.FAIL;

        int charge = (int) this.getByteTag(player.getHeldItem(hand), EnumTags.CHARGE);
        for (BlockPos changingPos : PosExtractor.posOnPlane(pos, facing, charge)) {
            // Only the block that is same to the block at player's pointer will get changed
            if (pointerBlock == world.getBlockState(changingPos)) {
                world.setBlockState(changingPos, next);
            }
        }

        return EnumActionResult.SUCCESS;
    }

    private byte getByteTag(ItemStack stack, EnumTags tag) {
        return stack.getTagCompound().getByte(tag.key);
    }

    private void cycleByteTag(ItemStack stack, EnumTags targetNbt, byte increase) {
        NBTTagCompound tag = TagUtils.getTagSafe(stack);
        byte originalVal = getByteTag(stack, targetNbt);

        if (originalVal >= targetNbt.max) {
            tag.setByte(targetNbt.key, (byte) 0);
        } else {
            TagUtils.setTagEnum(tag, targetNbt, (byte) (originalVal + increase));
        }
    }


    private static enum EnumTags implements IEnumNBTTags<Object> {

        CHARGE("charge", 0, EDataType.BYTE, 5);

        final EDataType type;
        final String key;
        final Object defaultValue;

        /**
         * Numbers ONLY
         */
        int max;

        private EnumTags(String key, String defaultVal, EDataType type) {
            this.key = key;
            this.defaultValue = defaultVal;
            this.type = type;
        }

        private EnumTags(String key, int defaultVal, EDataType type, int max) {
            this.key = key;
            if (type == EDataType.BYTE) {
                this.defaultValue = (byte) defaultVal;
                this.max = (byte) max;
            } else {
                this.defaultValue = defaultVal;
                this.max = max;
            }
            this.type = type;
        }

        @Override
        public EDataType getType() {
            return this.type;
        }

        @Override
        public String getKey() {
            return this.key;
        }

        @Override
        public Object getDefaultValue() {
            return this.defaultValue;
        }

    }

    @Override
    public NBTTagCompound getDefaultTag() {
        NBTTagCompound tag = new NBTTagCompound();
        TagUtils.buildTagWithDefault(tag, EnumTags.values());
        return tag;
    }

    @Override
    public void updateItemTag(ItemStack stack) {
        if (stack.getTagCompound() == null) {
            this.buildDefaultTag(stack);
        }
    }

}
