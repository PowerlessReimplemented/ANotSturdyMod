package powerlessri.anotsturdymod.items;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.items.base.ITagBasedItem;
import powerlessri.anotsturdymod.items.basic.ItemBasicItem;
import powerlessri.anotsturdymod.library.enums.EDataType;
import powerlessri.anotsturdymod.library.enums.EMachineLevel;
import powerlessri.anotsturdymod.library.interfaces.IEnumNBTTags;
import powerlessri.anotsturdymod.library.utils.NBTUtils;
import powerlessri.anotsturdymod.library.utils.PosUtils;
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
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if(world.isRemote) {
            if(player.isSneaking()) {
                
            } else {
                player.playSound(new SoundEvent(Utils.locationOf("endermen_teleport")), 0.7f, 1.0f);
            }
            
            return EnumActionResult.SUCCESS;
        }

        if(hand == EnumHand.OFF_HAND) {
            return EnumActionResult.PASS;
        }

        ItemStack exchanger = player.getHeldItem(hand);
        this.updateItemTag(exchanger);

        Utils.getLogger().info("Player is " + (player.isSneaking() ? "" : "not " + "sneaking, ") + (player.isSneaking() ? "selectBlock" : "replacement"));
        
        if(player.isSneaking()) {
            return selectTargetBlock(exchanger, world.getBlockState(pos));
        }

        return attemptExchange(exchanger, player, world, pos, facing);
    }

    private EnumActionResult selectTargetBlock(ItemStack exchanger, IBlockState pointerBlock) {
        NBTTagCompound tag = exchanger.getTagCompound();

        NBTUtils.setTagEnum(tag, EnumTags.TARGET_BLOCK, pointerBlock.getBlock().getRegistryName().toString());
        NBTUtils.setTagEnum(tag, EnumTags.TARGET_META, pointerBlock.getBlock().getMetaFromState(pointerBlock));

        return EnumActionResult.SUCCESS;
    }

    private EnumActionResult attemptExchange(ItemStack exchanger, EntityPlayer player, World world, BlockPos posHit, EnumFacing faceHit) {
        NBTTagCompound tag = exchanger.getTagCompound();

        String replacementName = tag.getString(EnumTags.TARGET_BLOCK.key);
        int replacementMeta = tag.getByte(EnumTags.TARGET_META.key);

        //TODO get item's fortune by nbt
        int radius = tag.getByte(EnumTags.RADIUS.key);
        int fortuneLevel = 0;

        @SuppressWarnings("deprecation")
        IBlockState replacementBlock = Block.getBlockFromName(replacementName).getStateFromMeta(replacementMeta);
        IBlockState exchangeSource = world.getBlockState(posHit);
        Block repBlockInst = replacementBlock.getBlock();
        Block exchBlockInst = exchangeSource.getBlock();
//        ItemStack replacementStack = Item.getItemFromBlock(repBlockInst).getDefaultInstance();
        
        Item repItemInst = Item.getItemFromBlock(repBlockInst);
        int repItemMeta = repBlockInst.getMetaFromState(replacementBlock);

        Iterable<BlockPos> affectedBlocks = PosUtils.blocksOnPlane(posHit, faceHit, radius);
        int quantityDropped = 0;
        // Actual amount of block gets exchanged
        int blocksAffectedCount = 0;

        Utils.getLogger().info(String.format("exchange attempt: radius=%d, rep=%s:%d, source=%s:%d",
                radius, replacementName, replacementMeta, exchBlockInst.getRegistryName().toString(), exchBlockInst.getMetaFromState(exchangeSource)));

        for(BlockPos pos : affectedBlocks) {
            IBlockState state = world.getBlockState(pos);
            if(state != exchangeSource) {
                continue;
            }

            quantityDropped += state.getBlock().quantityDropped(state, fortuneLevel, world.rand);
            blocksAffectedCount++;
        }

//        boolean useTransmutationEnabled = tag.getBoolean(EnumTags.USE_TRANSMUTATION_ORB.key);
//        boolean useTransmutation = false;
        int replacementInInventory = 0;

        ItemStack replacementStack = new ItemStack(repItemInst);
        replacementStack.setItemDamage(repItemMeta);
        for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack slot = player.inventory.getStackInSlot(i);
            if(slot.isItemEqual(replacementStack)) {
//            if(slot.getItem() == ) {
                replacementInInventory += slot.getCount();
            }
            
//            if(useTransmutationEnabled && slot.getItem() == ModItems.transmutationStone) {
//                useTransmutation = true;
//            }
        }
        
        Utils.getLogger().info(String.format("invetory serach result: required=%d, aviliable=%d",
                blocksAffectedCount, replacementInInventory));
        
        if(player.isCreative()) {
            replaceBlocks(world, affectedBlocks, exchangeSource, replacementBlock);
            return EnumActionResult.SUCCESS;
        }
        
//        Utils.getLogger().info(String.format("blocksAffected=%d, avaliableBlocks=%d",
//                blocksAffectedCount, replacementInInventory));;
        
        if(blocksAffectedCount > replacementInInventory) {
            return EnumActionResult.FAIL;
        }
        
        int decreamentRequested = blocksAffectedCount;
        
        for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack slot = player.inventory.getStackInSlot(i);
            
            if(slot.isItemEqual(replacementStack)) {
                if(slot.getCount() <=  decreamentRequested) {
                    decreamentRequested -= slot.getCount();
                    player.inventory.removeStackFromSlot(i);
                } else {
                    slot.setCount( slot.getCount() - decreamentRequested );
                    decreamentRequested = 0;
                    break;
                }
            }
        }

        ItemStack resultStack = new ItemStack(exchBlockInst.getItemDropped(exchangeSource, world.rand, fortuneLevel), quantityDropped);
        resultStack.setItemDamage(exchBlockInst.getMetaFromState(exchangeSource));
        player.inventory.addItemStackToInventory(resultStack);
        
        replaceBlocks(world, affectedBlocks, exchangeSource, replacementBlock);
        
        return EnumActionResult.SUCCESS;
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

        RADIUS("radius", (byte) 0, EDataType.BYTE),
        MAX_RADIUS("max_radius", (byte) -1, EDataType.BYTE),

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
        NBTTagCompound tag = stack.getTagCompound();
        if(tag == null)  {
            this.buildDefaultTag(stack);
        }

        if(tag.getByte(EnumTags.MAX_RADIUS.key) == -1) {
            NBTUtils.setTagEnum(tag, EnumTags.MAX_RADIUS, this.maxRadius);
        }
        
        NBTUtils.setTagEnum(tag, EnumTags.RADIUS, 2);
    }


}
