package powerlessri.anotsturdymod.network.actions;

/**
 * Optional parameter of an action message that indicates the location of receiver.
 * <p>
 * For world objects like tile entities and entities, instead of storing location in custom data, the use of a locational target object is
 * encouraged. For non world objects, the usage of target object is optional.
 * </p>
 */
public abstract class Target {

    // Type ID's for target implementations
    // Used as header of target object
    static final byte GENERIC_TARGET = 0;
    static final byte BLOCK_POS_TARGET = 1;
    static final byte UUID_TARGET = 2;
    static final byte DIMENSINAL_UUID_TARGET = 3;

}
