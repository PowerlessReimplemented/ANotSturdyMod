package powerlessri.anotsturdymod.blocks;

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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import powerlessri.anotsturdymod.blocks.base.TileBlockBase;
import powerlessri.anotsturdymod.library.utils.Utils;
import powerlessri.anotsturdymod.tile.TileEnergyStorage;
import powerlessri.anotsturdymod.world.AnsmSaveData;
import powerlessri.anotsturdymod.world.handler.LinkedEnergyStorage;

public class BlockRemoteEnergyCell extends TileBlockBase {

    public static class TileRemoteEnergyCell extends TileEnergyStorage {

        private static final int DEFAULT_CAPACITY = 1000000;
        private static final int DEFAULT_MAX_RECEIVE = 5000;
        private static final int DEFAULT_MAX_EXTRACT = 5000;

        private static final String CHANNEL = "channel";


        private final int capacity;
        private final int maxReceive;
        private final int maxExtract;

        private AnsmSaveData data;
        /** Reference to level-wide multiStorage */
        private LinkedEnergyStorage multiStorage;

        private int channel;

        public TileRemoteEnergyCell() {
            // Use default status
            this(-1, -1, -1);
        }
        
        public TileRemoteEnergyCell(int capacity, int maxReceive, int maxExtract) {
            super();

            this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
            this.maxReceive = Math.max(DEFAULT_MAX_RECEIVE, maxReceive);
            this.maxExtract = Math.max(DEFAULT_MAX_EXTRACT, maxExtract);

            this.channel = -1;
        }

        @Override
        public void onLoad() {
            if(multiStorage == null) {
                this.data = AnsmSaveData.fromWorld(this.getWorld());
                this.multiStorage = this.data.linkedEnergyNet.get(0);
            }
        }



        public EnergyStorage getStorage() {
            return multiStorage.getStorage(this.channel);
        }

        public void setChannel() {
            this.setChannel(this.multiStorage.nextChannel());
        }

        public void setChannel(int channel) {
            if(this.channel == channel) {
                Utils.getLogger().info("Same target channel with current channel when excuting TileRemoteEnergyCell#setChannel(int)");
                return;
            }
            
            // Update old storage capacity
            if(this.channel != -1) this.updateParentStorage(-capacity);

            this.channel = channel;

            // Increase new storage's capacity
            if(this.channel != -1) this.updateParentStorage(capacity);
        }

        /** Modify the capacity & stored energy of parent storage after setting channel. */
        private void updateParentStorage(int capacityIncreament) {
            EnergyStorage last = multiStorage.getStorage(this.channel);
            // Take out TileRemoteEnergyCell#capacity amount of storage capacity
            // This step will dispose the energy storage 'last'
            multiStorage.setStorageTraits(this.channel, last.getMaxEnergyStored() + capacityIncreament, maxReceive, maxExtract);
            // Put rest of energy into the new energy storage
            multiStorage.getStorage(this.channel).receiveEnergy(last.getEnergyStored(), false);
            this.data.markDirty();
        }
        

        
        @Override
        public void onChunkUnload() {
            // Remove self's capacity & energy from all storages
            this.setChannel(-1);
            this.data.markDirty();
        }


        @Override
        public void readFromNBT(NBTTagCompound tag) {
            super.readFromNBT(tag);

            this.channel = tag.getByte(CHANNEL);
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound tag) {
            tag.setByte(CHANNEL, (byte) this.channel);

            return super.writeToNBT(tag);
        }



        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing, boolean ignoreFacing) {
            // TODO add facing (replace true with facing condition)
            if(CapabilityEnergy.ENERGY == capability && (ignoreFacing || true)) {
                return true;
            }
            return super.hasCapability(capability, facing, ignoreFacing);
        }

        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
            if(this.hasCapability(capability, facing, true)) {
                return CapabilityEnergy.ENERGY.cast(this.getStorage());
            }
            return super.getCapability(capability, facing);
        }

    }



    public BlockRemoteEnergyCell(String name) {
        super(name, Material.IRON);

        this.setHardness(2.0f);
        this.setResistance(15.0f);
        this.setHarvestLevel(EHarvestTool.PICKAXE, EHarvestLevel.IRON);

        // TODO add own creative tab
        this.setCreativeTab(CreativeTabs.MISC);
    }



    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if(world.isRemote) {
            return true;
        }

        TileRemoteEnergyCell tile = ((TileRemoteEnergyCell) world.getTileEntity(pos));
        Utils.getLogger().info("Energy cell id: " + tile.channel);
        if(tile.channel == -1) {
            tile.setChannel();
            Utils.getLogger().info("Updated energy cell id: " + tile.channel);
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
        // Use default properties from TileRemoteEnergyCell
        return new TileRemoteEnergyCell(2000000, 5000, 5000);
    }

}
