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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.items.base.ITagBasedItem;
import powerlessri.anotsturdymod.items.basic.ItemBasicItem;
import powerlessri.anotsturdymod.library.enums.EDataType;
import powerlessri.anotsturdymod.library.enums.EMachineLevel;
import powerlessri.anotsturdymod.library.interfaces.IEnumNBTTags;
import powerlessri.anotsturdymod.library.utils.NBTUtils;
import powerlessri.anotsturdymod.library.utils.PosUtils;
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

        if(world.isRemote)
            return EnumActionResult.SUCCESS;
        
        if(hand == EnumHand.OFF_HAND) {
            return EnumActionResult.FAIL;
        }

        ItemStack exchanger = player.getHeldItem(hand);
        this.updateItemTag(exchanger);
        
        if(player.isSneaking()) {
            selectTargetBlock(exchanger, world.getBlockState(pos));
        } else {
            attemptExchange(exchanger, player, world, pos, facing);
        }

        return EnumActionResult.SUCCESS;
    }
    
    private void selectTargetBlock(ItemStack exchanger, IBlockState pointerBlock) {
        NBTTagCompound tag = exchanger.getTagCompound();
        
        NBTUtils.setTagEnum(tag, EnumTags.TARGET_BLOCK, pointerBlock.getBlock().getRegistryName().toString());
        NBTUtils.setTagEnum(tag, EnumTags.TARGET_META, pointerBlock.getBlock().getMetaFromState(pointerBlock));
    }
    
    private void attemptExchange(ItemStack exchanger, EntityPlayer player, World world, BlockPos posHit, EnumFacing faceHit) {
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
        
        Iterable<BlockPos> affectedBlocks = PosUtils.blocksOnPlane(posHit, faceHit, radius);
        Item itemDropped = exchBlockInst.getItemDropped(exchangeSource, world.rand, fortuneLevel);
        int quantityDropped = 0;
        
        Utils.getLogger().info(String.format("exchange attempt: radius=%d, rep=%s:%d, source=%s:%d",
                radius, replacementName, replacementMeta, exchBlockInst.getRegistryName().toString(), exchBlockInst.getMetaFromState(exchangeSource)));
        
        for(BlockPos pos : affectedBlocks) {
            IBlockState state = world.getBlockState(pos);
            if(state != exchangeSource) {
                continue;
            }
            
            quantityDropped += state.getBlock().quantityDropped(state, fortuneLevel, world.rand);
            world.setBlockState(pos, replacementBlock);
        }
        
        player.inventory.addItemStackToInventory(new ItemStack(itemDropped, quantityDropped));
    }
    
    
    
    public static enum EnumTags implements IEnumNBTTags<Object> {
        
        RADIUS("radius", (byte) 0, EDataType.BYTE),
        MAX_RADIUS("max_radius", (byte) -1, EDataType.BYTE),
        
        TARGET_BLOCK("target_block", Blocks.AIR.getRegistryName().toString(), EDataType.STRING),
        TARGET_META("target_meta", (byte) 0, EDataType.BYTE);

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
