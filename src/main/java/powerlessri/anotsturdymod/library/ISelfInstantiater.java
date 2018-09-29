package powerlessri.anotsturdymod.library;

public interface ISelfInstantiater<T> {

    T clone();
    /** Create a vanilla object by class constructor  */
    T cloneClean();

}
