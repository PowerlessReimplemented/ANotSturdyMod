package powerlessri.anotsturdymod.items.exchangers;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.config.ExchangerConfig;
import powerlessri.anotsturdymod.handlers.init.RegistryItem;
import powerlessri.anotsturdymod.library.item.base.SimpleItemBase;
import powerlessri.anotsturdymod.varia.Reference;
import powerlessri.anotsturdymod.varia.general.Utils;
import powerlessri.anotsturdymod.varia.general.InventoryUtils;
import powerlessri.anotsturdymod.varia.general.EMachineLevel;
import powerlessri.anotsturdymod.varia.tags.EnchantmentUtils;
import powerlessri.anotsturdymod.varia.tags.TagUtils;
import powerlessri.anotsturdymod.varia.general.PosExtractor;

import java.util.Random;

public class ItemExchanger extends SimpleItemBase {

    @RegistryItem
    public static final ItemExchanger BASIC_EXCHANGER = new ItemExchanger("exchanger", EMachineLevel.BASIC, ExchangerConfig.basicExchangerRadius);
    
    @RegistryItem
    public static final ItemExchanger ADVANCED_EXCHAGNER = new ItemExchanger("exchanger", EMachineLevel.ADVANCED, ExchangerConfig.advancedExchangerRadius);


    private static final String RADIUS = "radius";
    private static final String USE_TRANSMUTATIONS = "useTransm";

    /**
     * Maximum radius for this type of exchanger.
     */
    private final int maxRadius;

    public ItemExchanger(String name, EMachineLevel level, int radius) {
        super(level.getName() + "_" + name);

        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setMaxStackSize(1);
        this.maxRadius = radius;
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (world.isRemote) {
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        
        if (player.isSneaking()) {
            NBTTagCompound tag = TagUtils.getOrCreateTag(stack);

            byte radius = tag.getByte(RADIUS);
            radius = (byte) ((radius == maxRadius) ? 0 : radius + 1);
            tag.setByte(RADIUS, radius);

            player.sendStatusMessage(
                    new TextComponentString(Utils.readFromLang("item.ansm.exchangers.inform.radius") + " " + radius),
                    true);

            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }

        return new ActionResult<>(EnumActionResult.FAIL, stack);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            if (!player.isSneaking()) {
                player.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0f, 1.0f);
            }

            return EnumActionResult.SUCCESS;
        }


        ItemStack exchanger = player.getHeldItem(hand);

        if (!player.isSneaking()) {
            return attemptExchange(player, exchanger, world, pos, facing);
        } else {
            return selectTargetBlock(player, exchanger, world.getBlockState(pos));
        }
    }


    private EnumActionResult selectTargetBlock(EntityPlayer player, ItemStack exchanger, IBlockState selectedBlock) {
        if (!isBlockValid(selectedBlock)) {
            sendTileEntityError(player);
            return EnumActionResult.FAIL;
        }
        
        NBTTagCompound tag = TagUtils.getOrCreateTag(exchanger);
        NBTUtil.writeBlockState(tag, selectedBlock);
        
        return EnumActionResult.SUCCESS;
    }

    private EnumActionResult attemptExchange(EntityPlayer player, ItemStack exchanger, World world, BlockPos posHit,
                                             EnumFacing faceHit) {
        NBTTagCompound tag = TagUtils.getOrCreateTag(exchanger);

        int radius = tag.getByte(RADIUS);
        boolean isSilkTouch = EnchantmentUtils.hasEnchantment(exchanger, Enchantments.SILK_TOUCH, -1);
        // Forget about fortune if it's already silk touch
        int fortuneLevel = isSilkTouch ? 0 : EnchantmentUtils.getEnchantLevel(exchanger, Enchantments.FORTUNE);

        IBlockState replacementBlock = NBTUtil.readBlockState(tag);
        IBlockState exchangeSource = world.getBlockState(posHit);
        if (!this.isBlockValid(exchangeSource)) {
            this.sendTileEntityError(player);
            return EnumActionResult.FAIL;
        }


        Iterable<BlockPos> posList = PosExtractor.posOnPlane(posHit, faceHit, radius);

        if (player.isCreative()) {
            replaceBlocks(world, posList, exchangeSource, replacementBlock);
            return EnumActionResult.SUCCESS;
        }


        {
            ItemStack replacementStack = InventoryUtils.stackOf(replacementBlock);

            // Actual amount of block gets exchanged
            int blockAffected = 0;

            for (BlockPos pos : posList) {
                IBlockState state = world.getBlockState(pos);

                if (state == exchangeSource && this.isBlockValid(state)) {
                    blockAffected++;
                }
            }

            boolean useTransmutation = tag.getBoolean(USE_TRANSMUTATIONS);
            int quantityAvailable = getQuantityAvailable(player.inventory, replacementStack, useTransmutation);

            if (quantityAvailable <= blockAffected) {
                return EnumActionResult.FAIL;
            }

            InventoryUtils.forceTakeItems(player.inventory, replacementStack, blockAffected);

            int quantityDropped = getQuantityDropped(world, posList, exchangeSource, isSilkTouch, fortuneLevel);
            player.inventory.addItemStackToInventory(createItemStackDropped(exchangeSource, world.rand, isSilkTouch, fortuneLevel, quantityDropped));

            replaceBlocks(world, posList, exchangeSource, replacementBlock);
            return EnumActionResult.SUCCESS;
        }
    }


    private void sendTileEntityError(EntityPlayer player) {
        player.sendStatusMessage(
                new TextComponentString(Utils.readFromLang("item.ansm.exchangers.error.tileEntity"))
                        .setStyle(Reference.STYLE_RED),
                true);
    }

    /**
     * Is the given blockstate valid for exchange? (not tile entity)
     */
    private boolean isBlockValid(IBlockState state) {
        return !state.getBlock().hasTileEntity(state);
    }

    private boolean isStackSame(ItemStack stack1, ItemStack stack2, boolean matchTransmutation) {
        if (matchTransmutation) {
            // TODO match world transmutations
            return stack1.isItemEqual(stack2);
        }

        return stack1.isItemEqual(stack2);
    }

    private void replaceBlocks(World world, Iterable<BlockPos> posList, IBlockState from, IBlockState to) {
        for (BlockPos pos : posList) {
            IBlockState state = world.getBlockState(pos);
            if (state != from) {
                continue;
            }

            world.setBlockState(pos, to);
        }
    }

    private int getQuantityDropped(World world, Iterable<BlockPos> posList, IBlockState target, boolean isSilkTouch, int fortuneLevel) {
        int result = 0;
        for (BlockPos pos : posList) {
            if (world.getBlockState(pos) == target) {
                if (isSilkTouch) {
                    result++;
                } else {
                    result += target.getBlock().quantityDropped(target, fortuneLevel, world.rand);
                }
            }
        }
        return result;
    }

    private int getQuantityAvailable(IInventory inventory, ItemStack replacement, boolean useTransmutation) {
        int result = 0;
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack slot = inventory.getStackInSlot(i);

            if (this.isStackSame(slot, replacement, useTransmutation)) {
                result += slot.getCount();
            }
        }
        return result;
    }

    private ItemStack createItemStackDropped(IBlockState state, Random rand, boolean isSilkTouch, int fortuneLevel, int count) {
        if (isSilkTouch) {
            return InventoryUtils.stackOf(state, count);
        }

        Block block = state.getBlock();
        return new ItemStack(block.getItemDropped(state, rand, fortuneLevel), count, block.getMetaFromState(state));
    }



    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.SILK_TOUCH || enchantment == Enchantments.FORTUNE) {
            return true;
        }
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

}
