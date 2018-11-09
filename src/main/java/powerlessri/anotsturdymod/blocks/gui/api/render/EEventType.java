package powerlessri.anotsturdymod.blocks.gui.api.render;

public enum EEventType {

    /**
     * This event is directly triggered on this element.
     * In other words, it's the first element which received this event.
     */
    ORIGINAL(),
    
    /**
     * This event is passed from a parent element.
     * It's either sent by a controller or the parent element.
     * 
     * @deprecated Event bubbling (type {@link #BUBBLE}) is easier to understand and easier to implement. Use it instead if possible.
     */
    @Deprecated
    CAPTURE(),
    
    /**
     * This event is passed from a child element.
     * It's either sent by a controller or the child element itself.
     */
    BUBBLE();
    
}
