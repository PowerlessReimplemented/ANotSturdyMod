package powerlessri.anotsturdymod.mechanisms.remote_enetwork;

public interface IENetworkController {

    int getOrAllocChannel();

    int getChannel();


    /**
     * @return Number of storage upgrades installed.
     */
    int getAmountStorageUpgrades();

    /**
     * Install storage upgrades with upgrade capacity check.
     *
     * @return The number of storage upgrades accepted.
     */
    int installStorageUpgrade(int attempt);


    long getEnergyStored();

    void setEnergyStored(long energy);


    void decreaseEnergy(long increment);

    void increaseEnergy(long decrement);

    /**
     * {@link #decreaseEnergy(long)} with amount of energy left check.
     *
     * @return Amount of energy successfully extracted
     */
    long extractEnergy(long attempt, boolean simulate);

    /**
     * {@link #increaseEnergy(long)} with capacity left check.
     *
     * @return Amount of energy successfully received.
     */
    long receiveEnergy(long attempt, boolean simulate);


    long getCapacity();

    default long getCapacityLeft() {
        return getCapacity() - getEnergyStored();
    }

}
