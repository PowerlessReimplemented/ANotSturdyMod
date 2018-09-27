package powerlessri.anotsturdymod.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.blocks.base.TileBlockBase;
import powerlessri.anotsturdymod.tile.TileEnergyNetworkController;

public class BlockEnergyController extends TileBlockBase {

    public static final BlockEnergyController INSTANCE = new BlockEnergyController("energy_network_controller");



    private BlockEnergyController(String name) {
        super(name, Material.ROCK);
    }



    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if(world.isRemote) {
            return false;
        }

        //        ItemStack hand = player.getHeldItem(hand);
        //
        //        if(hand.getItem() == STORAGE_UPGRADE) {
        //            return true;
        //        }

        TileEnergyNetworkController tile = (TileEnergyNetworkController) world.getTileEntity(pos);
        player.sendMessage(new TextComponentString("controller id: " + tile.getOrAllocChannel(world)));

        return true;
    }



    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEnergyNetworkController();
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEnergyNetworkController.class;
    }

}
