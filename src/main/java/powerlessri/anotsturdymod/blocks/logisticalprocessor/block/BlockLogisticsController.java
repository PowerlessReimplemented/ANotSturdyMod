package powerlessri.anotsturdymod.blocks.logisticalprocessor.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.blocks.logisticalprocessor.ILogisticsController;
import powerlessri.anotsturdymod.handlers.ComponentizedGuiHandler;
import powerlessri.anotsturdymod.handlers.init.RegistryBlock;
import powerlessri.anotsturdymod.library.block.base.TileBlockBase;

public class BlockLogisticsController extends TileBlockBase implements ILogisticsController {
    
    @RegistryBlock
    public static final BlockLogisticsController LOGISTICS_CONTROLLER = new BlockLogisticsController("logistics_controller");
    
    
    public BlockLogisticsController(String name) {
        super(name, Material.ROCK);
        setCreativeTab(CreativeTabs.MISC);
    }


    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }

        ComponentizedGuiHandler.openGui("LogisticSys_Editor", player, world, pos);
        
        return false;
    }


    @Override
    public boolean hasItemForm() {
        return true;
    }

}
