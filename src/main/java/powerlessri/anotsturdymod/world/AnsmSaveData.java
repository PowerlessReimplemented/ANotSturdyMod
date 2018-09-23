package powerlessri.anotsturdymod.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import powerlessri.anotsturdymod.library.utils.Reference;

public class AnsmSaveData extends WorldSavedData {

    // TODO complete the code part that is actually related to this part (branch 'remote-energy-cell')
    // TODO code clean-up, and keep it DRY
    // Mojang's sh*t nbt implementation.
    public static class RemoteEnergyNetworkData implements INBTSerializable<NBTTagCompound> {

        public static final float CAPACITY_ENSURING_MULTIPLYER = 1.5f;

        public static final String CHANNEL_USAGE = "chnnlUsage";
        public static final String LIST_DATA = "storageData";
        public static final String CAPACITY = "capacitiy";
        public static final String IO_LIMIT = "ioLimit";
        public static final String STORED_ENERGY = "storedEnergy";

        public int nextChannel;

        /** Amount of channels in use. Should == capacity.size == ioLimit.size == storedEnergy.size (Ones that are actually allocated) */
        private int channelUsage;
        public int[] capacity;
        public int[] ioLimit;
        public int[] storedEnergy;

        public RemoteEnergyNetworkData() {

        }


        /**
         * 
         * @param channel Channel id to modify.
         * @param capacity The new capacity. Put -1 to ignore this parameter.
         * @param ioLimit The new IO limit. Put -1 to ignore this parameter.
         * @param storedEnergy The new energy stored. Put -1 to ignore this parameter.
         */
        public void set(int channel, int capacity, int ioLimit, int storedEnergy) {
            if(channel < this.channelUsage) {
                if(capacity != -1) this.capacity[channel] = capacity;
                if(ioLimit != -1) this.ioLimit[channel] = ioLimit;
                if(storedEnergy != -1) this.storedEnergy[channel] = storedEnergy;
            }
        }

        public void update() {
            // nextChannel = 0 when channelUsage = 1
            if(nextChannel - channelUsage < 2) {
                int[] oldCapacity = capacity;
                int[] oldIOLimit = ioLimit;
                int[] oldStoredEnergy = storedEnergy;

                this.capacity = new int[(int) (oldCapacity.length * CAPACITY_ENSURING_MULTIPLYER)];
                this.ioLimit = new int[(int) (oldIOLimit.length * CAPACITY_ENSURING_MULTIPLYER)];
                this.storedEnergy = new int[(int) (oldStoredEnergy.length * CAPACITY_ENSURING_MULTIPLYER)];

                for(int i = 0; i < this.channelUsage; i++) {
                    this.capacity[i] = oldCapacity[i];
                    this.ioLimit[i] = oldIOLimit[i];
                    this.storedEnergy[i] = oldStoredEnergy[i];
                }
            }
        }


        @Override
        public NBTTagCompound serializeNBT() {
            NBTTagCompound tag = new NBTTagCompound();

            tag.setInteger(CHANNEL_USAGE, channelUsage);

            NBTTagList dataList = new NBTTagList();
            for(int i = 0; i < this.channelUsage; i++) {
                NBTTagCompound dataComp = new NBTTagCompound();
                dataComp.setInteger(CAPACITY, capacity[i]);
                dataComp.setInteger(IO_LIMIT, ioLimit[i]);
                dataComp.setInteger(STORED_ENERGY, storedEnergy[i]);

                dataList.appendTag(dataComp);
            }
            tag.setTag(LIST_DATA, dataList);

            return tag;
        }


        @Override
        public void deserializeNBT(NBTTagCompound tag) {
            this.channelUsage = tag.getInteger(CHANNEL_USAGE);
            // Actual size of array, suppose to be bigger than channelUsage
            int arraySize = (int) (channelUsage * CAPACITY_ENSURING_MULTIPLYER);

            this.capacity = new int[arraySize];
            this.ioLimit = new int[arraySize];
            this.storedEnergy = new int[arraySize];

            NBTTagList dataList = tag.getTagList(LIST_DATA, Constants.NBT.TAG_COMPOUND);
            for(int i = 0; i < this.channelUsage; i++) {
                NBTTagCompound dataComp = dataList.getCompoundTagAt(i);
                this.capacity[i] = dataComp.getInteger(CAPACITY);
                this.ioLimit[i] = dataComp.getInteger(IO_LIMIT);
                this.storedEnergy[i] = dataComp.getInteger(STORED_ENERGY);
            }
        }

    }


    public static AnsmSaveData fromWorld(World world) {
        MapStorage storage = world.getMapStorage();
        AnsmSaveData result = (AnsmSaveData) storage.getOrLoadData(AnsmSaveData.class, DATA_NAME);

        if(result == null) {
            result = new AnsmSaveData();
            storage.setData(DATA_NAME, result);
        }

        return result;
    }



    public static final String DATA_NAME = Reference.MODID;

    // NBT tag keys
    public static final String CONTROLLER_CHANNEL_USAGE = "cntrllrNextChnnl";



    // TODO redesign so it's not coupled with BlockEnergyController.nextChannel
    public int controllerNextChannel = 1;


    public AnsmSaveData() {
        super(DATA_NAME);
    }


    @Override
    public void readFromNBT(NBTTagCompound tag) {
        this.controllerNextChannel = tag.getInteger(CONTROLLER_CHANNEL_USAGE);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setInteger(CONTROLLER_CHANNEL_USAGE, controllerNextChannel);

        return tag;
    }

}
