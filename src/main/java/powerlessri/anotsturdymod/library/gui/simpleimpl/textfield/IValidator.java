package powerlessri.anotsturdymod.library.gui.simpleimpl.textfield;

public interface IValidator {

    /**
     * Validate the input by player.
     *
     * @param input User input
     * @return Validation result
     */
    EValidationResult validate(String input);

    /**
     * Only check if the text if valid, but does not care about other options.
     * <p>
     * When overriding, the implementation should be equivalent to {@code .validate(input) == EValidationResult.ACCEPT}.
     * </p>
     *
     * @param input User input
     * @return {@code true} for valid ({@link EValidationResult#ACCEPT}), {@code false} for invalid ({@link EValidationResult#REJECT})
     */
    default boolean isTextValid(String input) {
        return this.validate(input) == EValidationResult.ACCEPT;
    }

    /**
     * Format the user input to make it acceptable.
     * <p>Called when {@link #validate(String)} returns {@link EValidationResult#FORMAT}.</p>
     *
     * @param input Raw user input
     * @return Formatted user input
     * @throws UnsupportedOperationException When the validator does not support inout formatting
     * @see #validate(String)
     */
    default String formatInput(String input) {
        throw new UnsupportedOperationException();
    }

}
