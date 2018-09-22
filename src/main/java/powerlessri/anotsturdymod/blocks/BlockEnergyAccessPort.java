package powerlessri.anotsturdymod.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import powerlessri.anotsturdymod.blocks.BlockEnergyController.TileEnergyNetworkController;
import powerlessri.anotsturdymod.blocks.base.TileBlockBase;
import powerlessri.anotsturdymod.tile.base.TileEntityBase;

public class BlockEnergyAccessPort extends TileBlockBase {

    /**
     * No idea why is the name soooooo long.
     */
    public static class TileControllerEnergyNetworkAccessPort extends TileEntityBase implements IEnergyStorage {

        /** Reference to (supposedly) the only instance of BlockEnergyController. Exists for compat issues. */
        public static final BlockEnergyController CONTROLLER_BLOCK = BlockEnergyController.INSTANCE;


        
        private TileEnergyNetworkController buffer;
        
        /**
         * When isPlug, this access is for inserting energy to controller.
         * When !isPlug, this access is used for extracting energy to the controller
         */
        private boolean isPlug;
        private int channel;

        public TileControllerEnergyNetworkAccessPort(boolean isPlug) {
            this(0, isPlug);
        }

        public TileControllerEnergyNetworkAccessPort(int channel, boolean isPlug) {
            this.channel = channel;
            this.isPlug = isPlug;
        }


        public int getChannel() {
            return this.channel;
        }
        
        /** @return {@code true} for success. <br /> {@code false} for fail.*/
        public boolean setChannel(int channel) {
            int oldChannel = this.channel;
            this.channel = channel;
            if(this.getController() == null) {
                this.channel = oldChannel;
                return false;
            }
            return true;
        }
        
        
        @Nullable
        public TileEnergyNetworkController getController() {
            if(this.buffer != null && this.buffer.isAlive && !this.buffer.isDirty) {
                return this.buffer;
            }
            
            // This channel has been allocated
            if(CONTROLLER_BLOCK.tiles.size() > this.channel) {
                TileEnergyNetworkController attempt = CONTROLLER_BLOCK.tiles.get(this.channel);

                // The controller tile entity is loaded
                if(attempt != null) {
                    return attempt;
                } else {
                    return CONTROLLER_BLOCK.tiles.get(0);
                }
            }

            return null;
        }


        @Nullable
        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
            return this.hasCapability(capability, facing, false);
        }

        @Nullable
        public boolean hasCapability(Capability<?> capability, EnumFacing facing, boolean ignoreFacing) {
            // TODO add facing
            if(CapabilityEnergy.ENERGY == capability && (ignoreFacing || true)) {
                return true;
            }

            return super.hasCapability(capability, facing);
        }

        @Nullable
        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
            if(this.hasCapability(capability, facing, true)) {
                return CapabilityEnergy.ENERGY.cast(this);
            }

            return super.getCapability(capability, facing);
        }


        @Override
        public void readFromNBT(NBTTagCompound tag) {
            super.readFromNBT(tag);

            this.channel = tag.getInteger(TileEnergyNetworkController.CHANNEL);
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound tag) {
            tag.setInteger(TileEnergyNetworkController.CHANNEL, this.channel);

            return super.writeToNBT(tag);
        }

        
        // ====== Energy Capabilities ====== //
        
        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            if(!simulate && this.canReceive()) {
                return this.getController().insertEnergy(maxReceive);
            }
            return 0;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            if(!simulate && this.canExtract()) {
                return this.getController().extractEnergy(maxExtract);
            }
            return 0;
        }

        @Override
        public int getEnergyStored() {
            return this.getController().getEnergyStored();
        }

        @Override
        public int getMaxEnergyStored() {
            return this.getController().getStorageCapacity();
        }

        @Override
        public boolean canExtract() {
            return this.isPlug;
        }

        @Override
        public boolean canReceive() {
            return !this.isPlug;
        }

    }
    



    private final int MAX_IO;
    
    public BlockEnergyAccessPort(String name, int ioLimit) {
        super(name, Material.ROCK);
        this.MAX_IO = ioLimit;
    }



    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        // TODO vary by state
        return new TileControllerEnergyNetworkAccessPort(this.MAX_IO, false);
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileControllerEnergyNetworkAccessPort.class;
    }

}
