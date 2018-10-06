package powerlessri.anotsturdymod.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.blocks.base.TileBlockBase;
import powerlessri.anotsturdymod.blocks.tile.TileENAccessPort;
import powerlessri.anotsturdymod.blocks.tile.TileENAccessPortOutput;
import powerlessri.anotsturdymod.blocks.tile.TileENComponentBase;
import powerlessri.anotsturdymod.blocks.tile.TileENWirelessTransmitter;
import powerlessri.anotsturdymod.handlers.ModGuiHandler;

import java.util.function.Function;

public class BlockEnergyAccessPort extends TileBlockBase {

    public static final int TYPE_ACCESS_PORT = 0;
    public static final int TYPE_WIRELESS_EMITTER = 1;

    private final int ioLimit;

    private final boolean isPlug;
    private final int type;
    private final Function<Integer, TileENComponentBase> tileCreator;

    
    public BlockEnergyAccessPort(String name, int ioLimit, boolean isPlug, int type, Function<Integer, TileENComponentBase> createTE) {
        super(name, Material.ROCK);
        this.ioLimit = ioLimit;

        this.isPlug = isPlug;
        this.type = type;
        this.tileCreator = createTE;

        setCreativeTab(CreativeTabs.MISC);
    }


    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(world.isRemote) {
            return true;
        }

        player.openGui(ANotSturdyMod.instance, ModGuiHandler.ENERGY_ACCESS_PORT + type, world, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        switch(type) {
            case TYPE_ACCESS_PORT:
                return isPlug ? new TileENAccessPortOutput(0, ioLimit) : new TileENAccessPort(0, ioLimit);

            case TYPE_WIRELESS_EMITTER:
                return isPlug ? new TileENWirelessTransmitter() : null;
        }

        return null;
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileENAccessPort.class;
    }

}
