package powerlessri.anotsturdymod.library.gui.simpleimpl.textfield;

/**
 * Validation result designed for {@link IValidator}. May be used for other purposes if needed.
 */
public enum EValidationResult {

    /**
     * The given user input is acceptable, and it will be applied as the formal stored string.
     */
    ACCEPT(),

    /**
     * The given user input is unacceptable, however it can be formatted through {@link IValidator#formatInput(String)} to become
     * acceptable.
     * <p>Not all validators support the format operation.</p>
     */
    FORMAT(),

    /**
     * The given user input is unacceptable, and it cannot be formatted.
     * <p>
     * The text field implementation may choose to notify server in any form. For instance tell server the unaccepted user input.
     * </p>
     */
    REJECT();

}
