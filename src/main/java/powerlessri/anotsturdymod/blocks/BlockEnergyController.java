package powerlessri.anotsturdymod.blocks;

import java.util.ArrayList;

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
import powerlessri.anotsturdymod.blocks.base.TileBlockBase;
import powerlessri.anotsturdymod.library.utils.Utils;
import powerlessri.anotsturdymod.tile.base.TileEntityBase;
import powerlessri.anotsturdymod.world.AnsmSaveData;

public class BlockEnergyController extends TileBlockBase {

    public static final BlockEnergyController INSTANCE = new BlockEnergyController("energy_network_controller");

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
        private static int nextChannel = DEFAULT_CHANNEL + 1;



        /** {@code true} when is loaded, {@code false} when is not loaded. */
        public boolean isAlive = false;
        /** Used to update reference of controller of BlockEnergyAccessPort. {@code true} when needs a update */
        public boolean isDirty = true;


        /** A unique channel id (in the save). An allocated (non-default) channel is at least {@code 1}. */
        private int channel;
        /** Capacity formula: {@code DEFAULT_CAPACITY * (amountStorageUpgrades + 1)} */
        private int amountStorageUpgrades;
        //TODO make everything long compatible
        private long energy;


        public TileEnergyNetworkController() {
            this.energy = 0;
        }



        public int getOrAllocChannel(World world) {
            return this.getOrAllocChannel(AnsmSaveData.fromWorld(world));
        }
        
        public int getOrAllocChannel(AnsmSaveData data) {
            if(!isInitalized()) {
                channel = nextChannel;
                nextChannel++;
                data.controllerNextChannel++;
                data.markDirty();
                
                if(INSTANCE.tiles.size() - nextChannel < 5) {
                    int oldSize = INSTANCE.tiles.size();
                    INSTANCE.tiles.ensureCapacity((int) (INSTANCE.tiles.size() * 1.5f));
                    Utils.getLogger().info("Ensured size of BlockEnergyController#tiles from " + oldSize + " to " + INSTANCE.tiles.size());
                }

                // Now it has a channel, put itself into the reference list.
                this.onLoad();
            }

            return this.getChannel();
        }
        
        public int getChannel() {
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

        public int getEnergyStored() {
            return (int) this.energy;
        }

        public int getStorageCapacity() {
            return DEFAULT_CAPACITY * (this.amountStorageUpgrades + 1);
        }


        public boolean isInitalized() {
            return this.channel != DEFAULT_CHANNEL;
        }

        // TODO use a better way to manage channels
        @Override
        public void onLoad() {
            // Wait until player activate this block (wait until it has a channel)
            if(isInitalized()) {
                // This step might cause an IndexOutOfBoundsException, if channel is not allocated by #getChannel()
                // but you're not suppose to do it other than #getChannel(), so it's fine.
                //
                // BlockEnergyController#tiles actually has the appropriate size && This tile entity with the channel does not exist
                // When channel == DEFAULT_CHANNEL, #tiles[c] will always be a FakeEnergyNetworkController (non-null)
                if(INSTANCE.tiles.get(this.channel) == null) {
                    INSTANCE.tiles.set(this.channel, this);
                    this.isAlive = true;
                    this.isDirty = true;
                } else {
                    // Whenever the program goes here, it's probably because INSTANCE.tiles.size() is too small
                    String description = "Unexpected repeating channel from BlockEnergyController";
                    IllegalAccessException e = new IllegalAccessException(description);
                    CrashReport crashReport = CrashReport.makeCrashReport(e, description);

                    throw new ReportedException(crashReport);
                }
            }
        }

        @Override
        public void onRemoved() {
            INSTANCE.tiles.set(this.channel, null);
            this.isAlive = false;
            this.isDirty = true;
        }

        // Redirecting
        @Override
        public void onChunkUnload() {
            this.onRemoved();
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

    public static class FakeEnergyNetworkController extends TileEnergyNetworkController {

        public FakeEnergyNetworkController() {
            this.isAlive = true;
            this.isDirty = false;
        }

        public int getOrAllocChannel() {
            return 0;
        }


        public int insertEnergy(int attempt) {
            return attempt;
        }

        public int extractEnergy(int attempt) {
            return attempt;
        }

        public int getStorageCapacity() {
            return 0;
        }

        public int getEnergyStored() {
            return 0;
        }


        @Override
        public void onLoad() {
        }

        @Override
        public void onChunkUnload() {
        }

        @Override
        public void onRemoved() {
        }


        // These can be empty because this tile entity does nothing and stores nothing and immutable.
        
        @Override
        public void readFromNBT(NBTTagCompound tag) {
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound tag) {
            return tag;
        }
    }



    /** A list of loaded & working (assigned with a channel) controller tile entities. */
    public final ArrayList<TileEnergyNetworkController> tiles;

    public BlockEnergyController(String name) {
        super(name, Material.ROCK);

        // TODO store size to some nbt file
        this.tiles = new ArrayList<>();
        for(int i = 0; i < 64; i++) this.tiles.add(null);
        this.tiles.add(new FakeEnergyNetworkController());
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
        player.sendMessage(new TextComponentString("controller id: " + tile.getOrAllocChannel(world)));

        return true;
    }



    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEnergyNetworkController();
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEnergyNetworkController.class;
    }

}
