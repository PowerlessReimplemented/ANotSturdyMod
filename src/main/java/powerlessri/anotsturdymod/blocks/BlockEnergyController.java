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
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.blocks.base.TileBlockBase;
import powerlessri.anotsturdymod.tile.base.TileEntityBase;

public class BlockEnergyController extends TileBlockBase {

    public static final BlockEnergyController INSTANCE = new BlockEnergyController("remote_energy_controller");

    //    public static final Item STORAGE_UPGRADE = new ItemUpgrade("energy_storage_upgrade");


    /**
     * This block does not meant to be a direct part of forge energy system. Use BlockEnergyAccessPort
     * for any access to the power storage managed by this block.
     */
    public static class TileEnergyNetworkController extends TileEntityBase {

        public static final int DEFAULT_CAPACITY = 1000000000; // a billion
        /** Don't limit IO for custom access port traits. */
        public static final int DEFAULT_IO = Integer.MAX_VALUE;

        // NBT tags
        public static final String CHANNEL = "storageChannel";
        public static final String STORAGE_UPGRADES = "storageUpgrades";
        public static final String STORAGE_ENERGY_REMAIN = "energyStored";

        private static final int DEFAULT_CHANNEL = 0;
        private static int channelUsage = DEFAULT_CHANNEL + 1;



        /** {@code true} when is loaded, {@code false} when is not loaded. */
        public boolean isAlive;


        /** A unique channel id (in the save) */
        private int channel;
        /** Capacity formula: <i>DEFAULT_CAPACITY * (amountStorageUpgrades + 1)</i> */
        private int amountStorageUpgrades;
        //TODO make everything long compatible
        private long energy;


        public TileEnergyNetworkController() {
            super();
            this.energy = 0;
        }



        public int getChannel() {
            if(channel == DEFAULT_CHANNEL) {
                channel = channelUsage;
                channelUsage++;

                // Store 'this' to BlockEnergyController#tiles
                this.onLoad();
            }

            return channel;
        }


        /** @return The amount of energy failed to insert. */
        public int insertEnergy(int attempt) {
            int actualInsertion = Math.min(this.getStorageCapacity(), Math.min(DEFAULT_IO, attempt));
            this.energy += actualInsertion;
            return attempt - actualInsertion;
        }

        /** @return The amount of energy failed to extract. */
        public int extractEnergy(int attempt) {
            int actualExtraction = Math.min(this.getEnergyStored(), Math.min(DEFAULT_IO, attempt));
            this.energy -= actualExtraction;
            return attempt - actualExtraction;
        }

        public int getStorageCapacity() {
            return DEFAULT_CAPACITY * (this.amountStorageUpgrades + 1);
        }

        public int getEnergyStored() {
            return (int) this.energy;
        }


        @Override
        public void onLoad() {
            if(channel != DEFAULT_CHANNEL && INSTANCE.tiles.get(this.channel) == null) {
                INSTANCE.tiles.set(this.channel, this);

                this.isAlive = true;
            } else {
                String description = "Unexpected repeating channel from BlockEnergyController";
                IllegalAccessException e = new IllegalAccessException(description);
                CrashReport crashReport = CrashReport.makeCrashReport(e, description);

                throw new ReportedException(crashReport);
            }
        }

        /** Used to simulate this block is removed, does not matter it's unloading (save to disk) or not. */
        @Override
        public void onChunkUnload() {
            INSTANCE.tiles.set(this.channel, null);

            this.isAlive = false;
        }


        @Override
        public void readFromNBT(NBTTagCompound tag) {
            super.readFromNBT(tag);

            this.channel = tag.getInteger(CHANNEL);
            this.amountStorageUpgrades = tag.getInteger(STORAGE_UPGRADES);
            this.energy = tag.getInteger(STORAGE_ENERGY_REMAIN);
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound tag) {
            tag.setInteger(CHANNEL, this.channel);
            tag.setInteger(STORAGE_UPGRADES, this.amountStorageUpgrades);
            tag.setInteger(STORAGE_ENERGY_REMAIN, this.getEnergyStored());

            return super.writeToNBT(tag);
        }

    }



    /** A list of loaded & working (assigned with a channel) controller tile entities. */
    public final List<TileEnergyNetworkController> tiles;

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

        TileEnergyNetworkController tile = (TileEnergyNetworkController) world.getTileEntity(pos);
        player.sendMessage(new TextComponentString("controller id: " + tile.getChannel()));

        return true;
    }


    
//    public void removeRecordedTile(int channel) {
//        this.removeRecordedTile(this.tiles.get(channel));
//    }
//    
//    public void removeRecordedTile(TileEnergyNetworkController tile) {
//        if(tile != null) {
//            tile.onChunkUnload();
//        }
//    }
    

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEnergyNetworkController tile = (TileEnergyNetworkController) world.getTileEntity(pos);
        tile.onChunkUnload();
    }



    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEnergyNetworkController();
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEnergyNetworkController.class;
    }


    public void tileCleanup() {
        for(TileEnergyNetworkController tile : this.tiles) {
            World world = tile.getWorld();
            BlockPos pos = tile.getPos();
            TileEntity targetTile = world.getTileEntity(pos);

            if(targetTile == null || !(targetTile instanceof TileEnergyNetworkController)) {
                this.tiles.set(tile.getChannel(), null);
            }
        }
    }

}
