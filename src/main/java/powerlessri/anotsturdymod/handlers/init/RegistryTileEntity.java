package powerlessri.anotsturdymod.handlers.init;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RegistryTileEntity {

    /**
     * Name of the tile entity. Will be presented with affect of {@link RegistryHandler#makeTileEntityID(String)}.
     * @return
     */
    String value();
    
}
