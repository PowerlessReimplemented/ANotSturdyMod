package powerlessri.anotsturdymod.handlers.init;

public @interface RegistryTileEntity {

    /**
     * Name of the tile entity. Will be presented with affect of {@link RegistryHandler#makeTileEntityID(String)}.
     * @return
     */
    String value();
    
}
