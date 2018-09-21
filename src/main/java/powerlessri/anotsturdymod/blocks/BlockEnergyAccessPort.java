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
    public static class TileControllerEnergyNetworkAccessPort extends TileEntityBase {

        /** Reference to (supposedly) the only instance of BlockEnergyController. Exists for compat issues. */
        public static final BlockEnergyController CONTROLLER_BLOCK = BlockEnergyController.INSTANCE;



        private int channel;

        public TileControllerEnergyNetworkAccessPort() {
            this(0);
        }

        public TileControllerEnergyNetworkAccessPort(int channel) {
            this.channel = channel;
        }


        @Nullable
        public TileEnergyNetworkController getController() {
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

        public EnergyStorage getControllerStorage() {
            TileEnergyNetworkController controller = this.getController();
            if(controller != null) {
                return controller.storage;
            }
            return new EnergyStorage(0, 0, 0, 0);
        }
        
        public EnergyStorage getChameleonStorage() {
            EnergyStorage controllerStorage = this.getControllerStorage();
            int maxIO = ((BlockEnergyAccessPort) this.getWorldBlockType()).MAX_IO;
            
            return new EnergyStorage(controllerStorage.getMaxEnergyStored(), maxIO, maxIO, controllerStorage.getEnergyStored());
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
                return CapabilityEnergy.ENERGY.cast(this.getControllerStorage());
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

    }



    private final int MAX_IO;
    
    public BlockEnergyAccessPort(String name, int ioLimit) {
        super(name, Material.ROCK);
        this.MAX_IO = ioLimit;
    }



    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileControllerEnergyNetworkAccessPort();
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileControllerEnergyNetworkAccessPort.class;
    }

}
