package powerlessri.anotsturdymod.systems.remoteenergynetwork.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.systems.remoteenergynetwork.IENetworkController;
import powerlessri.anotsturdymod.handlers.init.RegistryHandler;
import powerlessri.anotsturdymod.varia.tags.TagUtils;
import powerlessri.anotsturdymod.varia.general.Utils;
import powerlessri.anotsturdymod.network.PacketServerCommand;
import powerlessri.anotsturdymod.network.datasync.PacketClientRequestedData;
import powerlessri.anotsturdymod.network.datasync.PacketSRequestWorld;
import powerlessri.anotsturdymod.network.utils.ByteIOHelper;

import javax.annotation.Nullable;

public abstract class TileENAccessPort extends TileENComponentBase implements ICapabilityProvider, IEnergyStorage {

    public static final String TILE_REGISTRY_NAME = RegistryHandler.makeTileEntityID("energy_network_access_port");


    public TileENAccessPort() {
    }

    public TileENAccessPort(int channel, int ioLimit) {
        super(channel, ioLimit);
    }


    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (CapabilityEnergy.ENERGY == capability) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (CapabilityEnergy.ENERGY == capability) {
            return CapabilityEnergy.ENERGY.cast(this);
        }
        return super.getCapability(capability, facing);
    }


    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (this.canReceive()) {
            IENetworkController controller = getController();
            return (int) controller.receiveEnergy(maxReceive, simulate);
        }
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (this.canExtract()) {
            IENetworkController controller = getController();
            return (int) controller.extractEnergy(maxExtract, simulate);
        }
        return 0;
    }


    @Override
    public int getEnergyStored() {
        return (int) this.getController().getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return (int) this.getController().getCapacity();
    }


    @Override
    public boolean canReceive() {
        return true;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

}
