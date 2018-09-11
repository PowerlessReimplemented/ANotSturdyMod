package powerlessri.anotsturdymod.items;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.init.ModItems;
import powerlessri.anotsturdymod.items.basic.ItemBasicItem;
import powerlessri.anotsturdymod.library.enums.EMachineLevel;
import powerlessri.anotsturdymod.library.tags.EDataType;
import powerlessri.anotsturdymod.library.tags.IEnumNBTTags;
import powerlessri.anotsturdymod.library.tags.ITagBasedItem;
import powerlessri.anotsturdymod.library.utils.InventoryUtils;
import powerlessri.anotsturdymod.library.utils.NBTUtils;
import powerlessri.anotsturdymod.library.utils.PosExtractor;
import powerlessri.anotsturdymod.library.utils.Reference;
import powerlessri.anotsturdymod.library.utils.Utils;


public class ItemExchanger extends ItemBasicItem implements ITagBasedItem {

    private final int maxRadius;

    public ItemExchanger(String name, EMachineLevel level, int radius) {
        super(level.getName() + "_" + name);

        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setMaxStackSize(1);

        this.maxRadius = radius;
    }



    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing,
            float hitX, float hitY, float hitZ) {

        if(world.isRemote) {
            if(!player.isSneaking()) {
                player.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0f, 1.0f);
            }

            return EnumActionResult.SUCCESS;
        }

        ItemStack exchanger = player.getHeldItem(hand);
        this.updateItemTag(exchanger);

        if(!player.isSneaking()) {
            return attemptExchange(exchanger, player, world, pos, facing);
        } else {
            return selectTargetBlock(player, exchanger, world.getBlockState(pos));
        }
    }



    private EnumActionResult selectTargetBlock(EntityPlayer player, ItemStack exchanger, IBlockState pointerBlock) {
        if(this.isBlockValid(pointerBlock)) {
            NBTTagCompound tag = exchanger.getTagCompound();

            // Record information into nbt data
            tag.setString(EnumTags.TARGET_BLOCK.key, pointerBlock.getBlock().getRegistryName().toString());
            tag.setByte(EnumTags.TARGET_META.key, (byte) pointerBlock.getBlock().getMetaFromState(pointerBlock));

            return EnumActionResult.SUCCESS;
        }

        this.sendErrorTileEntity(player);
        return EnumActionResult.FAIL;
    }

    private EnumActionResult attemptExchange(ItemStack exchanger, EntityPlayer player, World world, BlockPos posHit,
            EnumFacing faceHit) {
        NBTTagCompound tag = exchanger.getTagCompound();

        String replacementName = tag.getString(EnumTags.TARGET_BLOCK.key);
        int replacementMeta = tag.getByte(EnumTags.TARGET_META.key);

        // TODO get item's fortune/silk touch by tag
        int radius = tag.getByte(EnumTags.RADIUS.key);
        boolean isSilkTouch = false;
        int fortuneLevel = 0;

        @SuppressWarnings("deprecation")
        IBlockState replacementBlock = Block.getBlockFromName(replacementName).getStateFromMeta(replacementMeta);
        IBlockState exchangeSource = world.getBlockState(posHit);

        if(!this.isBlockValid(exchangeSource)) {
            this.sendErrorTileEntity(player);
            return EnumActionResult.FAIL;
        }

        Block repBlockInst = replacementBlock.getBlock();
        Block exchBlockInst = exchangeSource.getBlock();

        Item repItemInst = Item.getItemFromBlock(repBlockInst);
        ItemStack replacementStack = InventoryUtils.stackOf(repItemInst, replacementMeta);

        Iterable<BlockPos> posList = PosExtractor.posOnPlane(posHit, faceHit, radius);

        int quantityDropped = 0;
        // Actual amount of block gets exchanged
        int blockAffected = 0;

        for(BlockPos pos : posList) {
            IBlockState state = world.getBlockState(pos);

            if(state != exchangeSource && this.isBlockValid(state)) {
                quantityDropped += isSilkTouch ? 1 : state.getBlock().quantityDropped(state, fortuneLevel, world.rand);
                blockAffected++;
            }
        }

        boolean useTransmutationEnabled = tag.getBoolean(EnumTags.USE_TRANSMUTATION_ORB.key);
        // Start off, if didn't event chose to match transmutations, than do nothing
        boolean matchTransmutation = false;
        int replacementInInventory = 0;

        // Search for available resources & check out if player has transmutation orb
        for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack slot = player.inventory.getStackInSlot(i);

            if(this.isStackSame(slot, replacementStack, matchTransmutation)) {
                replacementInInventory += slot.getCount();
            }

            // If already redo the search, which is indicated by matchTransmutation == true, than don't restart again
            if(useTransmutationEnabled && !matchTransmutation && slot.getItem() == ModItems.TRANSMUTATION_ORB) {
                matchTransmutation = true;

                // Restart the search
                replacementInInventory = 0;
                i = 0;
            }
        }

        if(player.isCreative()) {
            replaceBlocks(world, posList, exchangeSource, replacementBlock);
            return EnumActionResult.SUCCESS;
        }

        // Survival-only below

        if(blockAffected > replacementInInventory) {
            return EnumActionResult.FAIL;
        }

        InventoryUtils.takeItems(player.inventory, replacementStack);

        // Blocks got exchanged out
        player.inventory
        .addItemStackToInventory(
                InventoryUtils.stackOf(
                        isSilkTouch ? Item.getItemFromBlock(exchBlockInst)
                                : exchBlockInst.getItemDropped(exchangeSource, world.rand, fortuneLevel),
                                exchBlockInst.getMetaFromState(exchangeSource), quantityDropped));

        replaceBlocks(world, posList, exchangeSource, replacementBlock);

        return EnumActionResult.SUCCESS;
    }



    private void sendErrorTileEntity(EntityPlayer player) {
        player.sendMessage(
                new TextComponentString(Utils.readFromLang("item.ansm.exchangers.error.tileEntity"))
                .setStyle(Reference.STYLE_RED));
    }



    /** Is the given blockstate valid for exchange? (not tile entity) */
    private boolean isBlockValid(IBlockState state) {
        return !state.getBlock().hasTileEntity(state);
    }

    private boolean isStackSame(ItemStack stack1, ItemStack stack2, boolean matchTransmutation) {
        if(matchTransmutation) {
            // TODO match world transmutation
            return stack1.isItemEqual(stack2);
        }

        return stack1.isItemEqual(stack2);
    }

    private void replaceBlocks(World world, Iterable<BlockPos> posList, IBlockState from, IBlockState to) {
        for(BlockPos pos : posList) {
            IBlockState state = world.getBlockState(pos);
            if(state != from) {
                continue;
            }

            world.setBlockState(pos, to);
        }
    }



    public static enum EnumTags implements IEnumNBTTags<Object> {

        RADIUS("radius", (byte) 0, EDataType.BYTE), MAX_RADIUS("max_radius", (byte) -1, EDataType.BYTE),

        TARGET_BLOCK("target_block", Blocks.AIR.getRegistryName().toString(), EDataType.STRING),
        TARGET_META("target_meta", (byte) 0, EDataType.BYTE),

        USE_TRANSMUTATION_ORB("use_transmutations", false, EDataType.BOOLEAN);

        EDataType type;
        String key;
        Object defaultValue;

        private EnumTags(String key, Object defaultVal, EDataType type) {
            this.key = key;
            this.defaultValue = defaultVal;
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
        NBTUtils.buildTagWithDefault(tag, EnumTags.values());
        return tag;
    }

    @Override
    public void updateItemTag(ItemStack stack) {
        if(!stack.hasTagCompound()) {
            this.buildDefaultTag(stack);
        }

        NBTTagCompound tag = stack.getTagCompound();
        if(tag.getByte(EnumTags.MAX_RADIUS.key) == -1) {
            NBTUtils.setTagEnum(tag, EnumTags.MAX_RADIUS, this.maxRadius);
        }

        tag.setByte(EnumTags.RADIUS.key, (byte) 2);
    }

}
