package powerlessri.anotsturdymod.items;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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
    }
    
    private void attemptExchange(ItemStack exchanger, EntityPlayer player, World world, BlockPos posHit, EnumFacing faceHit) {
        NBTTagCompound tag = exchanger.getTagCompound();
        
        String tragetName = tag.getString(EnumTags.TARGET_BLOCK.key);
        int targetMeta = tag.getByte(EnumTags.TARGET_BLOCK_META.key);
        int radius = tag.getByte(EnumTags.RADIUS.key);
        
        //TODO get item's fortune by nbt
        int fortuneLevel = 0;
        
        @SuppressWarnings("deprecation")
        IBlockState exchangeTarget = Block.getBlockFromName(tragetName).getStateFromMeta(targetMeta);
        Iterable<BlockPos> affectedBlocks = PosUtils.blocksOnPlane(posHit, faceHit, radius);
        int resultQuantity = 0;
        
        for(BlockPos pos : affectedBlocks) {
            IBlockState state = world.getBlockState(pos);
            if(state != exchangeTarget) {
                continue;
            }
            
            resultQuantity += state.getBlock().quantityDropped(state, fortuneLevel, world.rand);
            world.setBlockState(pos, exchangeTarget);
        }
        
        player.inventory.addItemStackToInventory(new ItemStack(exchangeTarget.getBlock(), resultQuantity));
    }
    
    
    
    public static enum EnumTags implements IEnumNBTTags<Object> {
        
        RADIUS("radius", (byte) 0, EDataType.BYTE),
        MAX_RADIUS("max_radius", (byte) -1, EDataType.BYTE),
        
        TARGET_BLOCK("target_block", Blocks.AIR.getRegistryName().toString(), EDataType.STRING),
        TARGET_BLOCK_META("target_meta", (byte) 0, EDataType.BYTE);

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
