package powerlessri.anotsturdymod.handlers.init;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RegistryItem {

    /**
     * The maximum metadata value that's valid. Inclusive.
     */
    int maxMeta() default 0;
    
}
