package powerlessri.anotsturdymod.library.gui.api;

public enum EDisplayMode {

    /**
     * The component will not be drawn.
     */
    NONE(),
    
    /**
     * The component will be drawn, and all events will be passed.
     */
    ALL(),

    /**
     * The component will be drawn, but will not received events.
     */
    DISABLED();
    
}
