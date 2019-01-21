package powerlessri.anotsturdymod.network.actions.registry;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import powerlessri.anotsturdymod.network.actions.Target;

import java.util.List;
import java.util.function.Supplier;

public class TargetMapping {

    private static final TargetMapping INSTANCE = new TargetMapping();

    public static TargetMapping getInstance() {
        return INSTANCE;
    }


    private int nextID;
    private TObjectIntMap<Class<? extends Target>> delegates;
    private List<Supplier<? extends Target>> factories;

    public TargetMapping() {
        this.nextID = 0;
        this.delegates = new TObjectIntHashMap<>();
    }


    public void register(Target target, Supplier<? extends Target> factory) {
        register(target.getClass(), factory);
    }

    public void register(Class<? extends Target> clazz, Supplier<? extends Target> factory) {
        if (delegates.containsKey(clazz)) {
            throw new IllegalStateException("The given key " + clazz + " already exist in the mapping.");
        }
        delegates.put(clazz, nextID++);
        factories.add(factory);
    }


    public int getID(Target target) {
        return getID(target.getClass());
    }

    public int getID(Class<? extends Target> clazz) {
        if (!delegates.containsKey(clazz)) {
            throw new IllegalArgumentException("The class key " + clazz + " hasn't been registered yet.");
        }
        return delegates.get(clazz);
    }

    public Supplier<? extends Target> getFactory(int id) {
        return factories.get(id);
    }

    public Target create(int id) {
        return getFactory(id).get();
    }

}
