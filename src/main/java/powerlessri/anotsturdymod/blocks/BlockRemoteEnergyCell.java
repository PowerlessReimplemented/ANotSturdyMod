package powerlessri.anotsturdymod.blocks;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.EnergyStorage;
import powerlessri.anotsturdymod.blocks.base.TileBlockBase;
import powerlessri.anotsturdymod.tile.TileEnergyStorage;
import powerlessri.anotsturdymod.world.handler.LinkedEnergyStorage;

public class BlockRemoteEnergyCell extends TileBlockBase {
    
    private static final LinkedEnergyStorage DEFAULT_MULTISTORAGE = new LinkedEnergyStorage();
    private static final int DEFAULT_STORAGE_ID = DEFAULT_MULTISTORAGE.nextChannel();
    
    private static List<BlockRemoteEnergyCell> instances = new ArrayList<>();
    
    @Nullable
    public static LinkedEnergyStorage forMultiStorage(int id) {
        return instances.get(id).multiStorage;
    }
    
    
    
    public static class TileRemoteEnergyCell extends TileEnergyStorage {
        
        private static final int DEFAULT_CAPACITY = 1000000;
        private static final int DEFAULT_MAX_RECEIVE = 5000;
        private static final int DEFAULT_MAX_EXTRACT = 5000;
        
        private static final String MULTISTORAGE_ID = "multiStorageId";
        private static final String CHANNEL = "channel";
        
        
        // Id of instance of BlockRemoteEnergyCell, used to find the corresponding multistorage
        private int instanceId;
        private LinkedEnergyStorage multiStorage;
        
        private int channel;
        
        public TileRemoteEnergyCell(int instanceId) {
            super();
            
            this.instanceId = instanceId;
            this.multiStorage = forMultiStorage(instanceId);
            this.channel = DEFAULT_STORAGE_ID;
        }
        
        
        
        public void setChannel() {
            this.setChannel(this.multiStorage.nextChannel());
        }
        
        public void setChannel(int channel) {
            this.updateStorage(-DEFAULT_CAPACITY);
            
            if(this.multiStorage.doesStorageExist(channel)) {
                this.channel = channel;
            }
            
            this.updateStorage(DEFAULT_CAPACITY);
        }
        
        public void updateStorage() {
            this.storage = this.multiStorage.getStorage(this.channel);
        }
        
        private void updateStorage(int capacityIncreament) {
            EnergyStorage last = multiStorage.getStorage(this.channel);
            // Take out DEFAULT_CAPACITY amount of capacity
            // This step will dispose the energy storage 'last'
            multiStorage.setStorageTraits(this.channel, last.getMaxEnergyStored() + capacityIncreament, DEFAULT_MAX_RECEIVE, DEFAULT_MAX_EXTRACT);
            // Put rest of energy into the new energy storage
            multiStorage.getStorage(this.channel).receiveEnergy(last.getEnergyStored(), false);
        }
        
        
        
        @Override
        public void readFromNBT(NBTTagCompound tag) {
            super.readFromNBT(tag);
            
            this.multiStorage = BlockRemoteEnergyCell.forMultiStorage(tag.getByte(MULTISTORAGE_ID));
            this.channel = tag.getByte(CHANNEL);
        }
        
        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound tag) {
            tag.setByte(MULTISTORAGE_ID, (byte) this.instanceId);
            tag.setByte(CHANNEL, (byte) this.channel);
            
            return super.writeToNBT(tag);
        }
        
    }
    
    
    
    private final int instanceId;
    private final LinkedEnergyStorage multiStorage;
    

    public BlockRemoteEnergyCell(String name) {
        super(name, Material.IRON);

        this.setHardness(2.0f);
        this.setResistance(15.0f);
        this.setHarvestLevel(EHarvestTool.PICKAXE, EHarvestLevel.IRON);

        // TODO add own creative tab
        this.setCreativeTab(CreativeTabs.MISC);
        
        instances.add(this);
        this.instanceId = instances.size() - 1;
        
        this.multiStorage = new LinkedEnergyStorage();
    }



    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        
        if(world.isRemote) {
            return true;
        }
        
        // TODO insert upgrades, open gui
        
        return false;
    }



    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileRemoteEnergyCell.class;
    }
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileRemoteEnergyCell(this.instanceId);
    }

}
