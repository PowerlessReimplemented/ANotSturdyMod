package powerlessri.anotsturdymod.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.energy.EnergyStorage;
import powerlessri.anotsturdymod.blocks.base.TileBlockBase;
import powerlessri.anotsturdymod.tile.base.TileEntityBase;

public class BlockEnergyController extends TileBlockBase {
    
    public static final BlockEnergyController INSTANCE = new BlockEnergyController("remote_energy_controller");

//    public static final Item STORAGE_UPGRADE = new ItemUpgrade("energy_storage_upgrade");
    
    
    public static class TileEnergyController extends TileEntityBase {
        
        private static final int DEFAULT_CAPACITY = 1000000000; // a billion
        // Let port's implementation to specify IO limit
        private static final int DEFAULT_IO = Integer.MAX_VALUE;
        
        private static final String CHANNEL = "storageChannel";
        private static final String STORAGE_UPGRADES = "storageUpgrades";
        private static final String STORAGE_ENERGY_REMAIN = "energyStored";
        
        private static final int DEFAULT_CHANNEL = 0;
        private static int channelUsage = DEFAULT_CHANNEL + 1;
        
        
        
        public EnergyStorage storage;
        
        private int channel;
        // Capacity formula: DEFAULT_CAPACITY * (amountStorageUpgrades + 1)
        private int amountStorageUpgrades;
        
        
        public TileEnergyController() {
            this.storage = new EnergyStorage(this.getStorageCapacity(), DEFAULT_IO, DEFAULT_IO, 0);
        }
        
        
        
        public int getChannel() {
            if(channel == DEFAULT_CHANNEL) {
                channel = channelUsage;
                channelUsage++;
            }
            
            return channel;
        }
        
        public int getStorageCapacity() {
            return DEFAULT_CAPACITY * (this.amountStorageUpgrades + 1);
        }
        
        
        @Override
        public void onLoad() {
            if(channel != DEFAULT_CHANNEL && INSTANCE.tiles.get(this.channel) == null) {
                INSTANCE.tiles.set(this.channel, this);
            } else {
                String description = "Unexpected repeating channel from BlockEnergyController";
                IllegalAccessException e = new IllegalAccessException(description);
                CrashReport crashReport = CrashReport.makeCrashReport(e, description);
                
                throw new ReportedException(crashReport);
            }
        }
        
        @Override
        public void onChunkUnload() {
            INSTANCE.tiles.set(this.channel, null);
        }
        
        
        @Override
        public void readFromNBT(NBTTagCompound tag) {
            super.readFromNBT(tag);
            
            this.channel = tag.getInteger(CHANNEL);
            this.amountStorageUpgrades = tag.getInteger(STORAGE_UPGRADES);
            int energyStored = tag.getInteger(STORAGE_ENERGY_REMAIN);
            
            this.storage = new EnergyStorage(this.getStorageCapacity(), DEFAULT_IO, DEFAULT_IO, energyStored);
        }
        
        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound tag) {
            tag.setInteger(CHANNEL, this.channel);
            tag.setInteger(STORAGE_UPGRADES, this.amountStorageUpgrades);
            tag.setInteger(STORAGE_ENERGY_REMAIN, this.storage.getEnergyStored());
            
            return super.writeToNBT(tag);
        }
        
    }
    
    
    
    
    public final List<TileEnergyController> tiles;

    public BlockEnergyController(String name) {
        super(name, Material.ROCK);
        
        tiles = new ArrayList<>(10); // Preinitialize to prevent weird IndexOutOfBoundException(s)
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
        
        TileEnergyController tile = (TileEnergyController) world.getTileEntity(pos);
        player.sendMessage(new TextComponentString("controller id: " + tile.getChannel()));
        
        return true;
    }
    
    
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEnergyController();
    }
    
    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEnergyController.class;
    }

}
