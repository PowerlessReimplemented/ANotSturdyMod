package powerlessri.anotsturdymod.items;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.handlers.init.RegistryItem;
import powerlessri.anotsturdymod.handlers.init.RegistryTileEntity;
import powerlessri.anotsturdymod.library.item.base.SimpleItemBase;
import powerlessri.anotsturdymod.varia.general.Utils;

public class ItemDebugTool extends SimpleItemBase {

    @RegistryItem
    public static final ItemDebugTool DEBUG_TOOL = new ItemDebugTool("debug_tool");

    @RegistryTileEntity("fake_tile_entity")
    public static class FakeTileEntity extends TileEntity {
        @Override
        public String toString() {
            return "FakeTileEntity{}";
        }
    }


    public ItemDebugTool(String name) {
        super(name);
    }


    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return EnumActionResult.SUCCESS;
        }


        IBlockState state = world.getBlockState(pos);
        String identifier = state.getBlock().getRegistryName().toString();
        String properties = state.getProperties().toString();

        TileEntity tile = Utils.selectNonnull(world.getTileEntity(pos), new FakeTileEntity());
        String tileInfo = tile.writeToNBT(new NBTTagCompound()).toString();

        player.sendMessage(Utils.prefixComponentFromLang("item.ansm.debugTool.msg.identifier").appendSibling(Utils.createStringGray(identifier)));
        player.sendMessage(Utils.prefixComponentFromLang("item.ansm.debugTool.msg.properties").appendSibling(Utils.createStringGray(properties)));
        player.sendMessage(Utils.prefixComponentFromLang("item.ansm.debugTool.msg.tileInformation").appendSibling(Utils.createStringGray(tileInfo)));

        return EnumActionResult.SUCCESS;
    }


    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

}
