package powerlessri.anotsturdymod.blocks.transfer.redirect;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import powerlessri.anotsturdymod.handlers.init.RegistryTileEntity;
import powerlessri.anotsturdymod.library.tile.base.TileEntityBase;
import powerlessri.anotsturdymod.varia.general.Utils;
import powerlessri.anotsturdymod.varia.tags.TagUtils;

import javax.annotation.Nullable;

@RegistryTileEntity("capability_redirector")
public class TileRedirector extends TileEntityBase {

    private BlockPos neighborPos;
    private BlockPos targetPos;

    @Nullable
    public BlockPos findTargetTested(BlockPos source) {
        if (this.pos.equals(source)) {
            return null;
        }

        TileEntity tile = this.getNeighborTile();
        if (tile instanceof TileRedirector) {
            return ((TileRedirector) tile).findTargetTested(source);
        }

        return this.targetPos;
    }


    @Override
    public void onLoadServer() {
        Utils.getLogger().info(targetPos);
        this.getTargetPos();
    }


    public BlockPos getNeighborPos() {
        if (neighborPos == null) {
            IBlockState state = this.getWorldBlockState();
            EnumFacing facing = state.getValue(BlockRedirector.FACING);
            this.neighborPos = this.pos.offset(facing);
        }
        return this.neighborPos;
    }

    public BlockPos getTargetPos() {
        if (targetPos == null) {
            // Default value
            this.targetPos = this.getNeighborPos();

            IBlockState state = this.getWorldBlockState();
            TileEntity tile = this.getNeighborTile();

            if (tile instanceof TileRedirector) {
                TileRedirector redirector = (TileRedirector) tile;

                // Is looping
                if (redirector.getTargetPos().equals(this.pos)) {
                    BlockRedirector block = (BlockRedirector) state.getBlock();
                    block.breakAndDrop(world, pos, state);
                    return this.targetPos;
                }

                this.targetPos = redirector.getTargetPos();
            }

            this.markDirty();
        }

        return this.targetPos;
    }

    public TileEntity getNeighborTile() {
        return this.world.getTileEntity(getNeighborPos());
    }

    public TileEntity getTargetTile() {
        return this.world.getTileEntity(getTargetPos());
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        TileEntity tile = this.getTargetTile();
        if (tile != null) {
            return tile.hasCapability(capability, facing);
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        TileEntity tile = this.getTargetTile();
        if (tile != null) {
            return tile.getCapability(capability, facing);
        }
        return super.getCapability(capability, facing);
    }


    @Override
    public void restoreFromNBT(NBTTagCompound tag) {
        this.targetPos = TagUtils.readBlockPos(tag, "target");
    }

    @Override
    public void writeRestorableNBT(NBTTagCompound tag) {
        TagUtils.writeBlockPos(tag, "target", targetPos);
    }


}
