package powerlessri.anotsturdymod.blocks.remoteenetwork.block;

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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.handlers.init.RegistryBlock;
import powerlessri.anotsturdymod.handlers.init.RegistryItem;
import powerlessri.anotsturdymod.library.block.base.TileBlockBase;
import powerlessri.anotsturdymod.items.ItemUpgrade;
import powerlessri.anotsturdymod.blocks.remoteenetwork.tile.TileENController;

// TODO add model & texture
public class BlockEnergyController extends TileBlockBase {

    @RegistryBlock
    public static final BlockEnergyController EN_CONTROLLER = new BlockEnergyController("energy_network_controller");
    
    @RegistryItem
    public static final ItemUpgrade STORAGE_UPGRADE = new ItemUpgrade("energy_network_storage_upgrade");
    
    @RegistryItem
    public static final ItemUpgrade IO_UPGRADE = new ItemUpgrade("energy_network_io_upgrade");


    private BlockEnergyController(String name) {
        super(name, Material.ROCK);

        setHardness(4.0f);
        setResistance(50.0f);
        setHarvestLevel(EHarvestTool.PICKAXE, EHarvestLevel.IRON);
        setCreativeTab(CreativeTabs.MISC);
    }


    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }


        TileENController tile = (TileENController) world.getTileEntity(pos);
        ItemStack heldItem = player.getHeldItem(hand);

        // TODO make a gui for upgrades & storage display & network
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


        // Also allocate new network (if hasn't yet) on server side
        player.sendStatusMessage(new TextComponentString("controller id: " + tile.getOrAllocChannel() + "  energy stored: " + tile.energyStored), true);

        return true;
    }


    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileENController();
    }


    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

}
