package powerlessri.anotsturdymod.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.blocks.base.TileBlockBase;
import powerlessri.anotsturdymod.blocks.tile.TileENController;
import powerlessri.anotsturdymod.items.ItemUpgrade;

public class BlockEnergyController extends TileBlockBase {

    public static final BlockEnergyController INSTANCE = new BlockEnergyController("energy_network_controller");
    public static final ItemUpgrade STORAGE_UPGRADE = new ItemUpgrade("energy_network_storage_upgrade");


    private BlockEnergyController(String name) {
        super(name, Material.ROCK);

        setCreativeTab(CreativeTabs.MISC);
    }


    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }


        TileENController tile = (TileENController) world.getTileEntity(pos);
        ItemStack heldItem = player.getHeldItem(hand);

        // TODO make a gui for upgrades & storage display & channel
        if (player.isSneaking()) {
            // TODO add pop upgrades
            // Pop upgrades
        } else {
            // Insert upgrades
            if (heldItem.getItem() == STORAGE_UPGRADE) {
                int accepted = tile.installStorageUpgrade(heldItem.getCount());
                heldItem.setCount(heldItem.getCount() - accepted);

                return true;
            }
        }


        // Also allocate new channel (if hasn't yet) on server side
        player.sendMessage(new TextComponentString("controller id: " + tile.getOrAllocChannel()));

        return true;
    }


    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileENController();
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileENController.class;
    }

}
