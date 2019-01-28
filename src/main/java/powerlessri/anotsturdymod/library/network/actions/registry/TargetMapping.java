package powerlessri.anotsturdymod.library.network.actions.registry;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import powerlessri.anotsturdymod.library.network.actions.ITarget;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TargetMapping {

    private static final TargetMapping INSTANCE = new TargetMapping();

    public static TargetMapping getInstance() {
        return INSTANCE;
    }


    private int nextID;
    private TObjectIntMap<Class<? extends ITarget>> delegates;
    private List<Supplier<? extends ITarget>> factories;

    public TargetMapping() {
        this.nextID = 0;
        this.delegates = new TObjectIntHashMap<>();
        this.factories = new ArrayList<>();
    }


    public void register(ITarget target, Supplier<? extends ITarget> factory) {
        register(target.getClass(), factory);
    }

    public void register(Class<? extends ITarget> clazz, Supplier<? extends ITarget> factory) {
        if (delegates.containsKey(clazz)) {
            throw new IllegalStateException("The given key " + clazz + " already exist in the mapping.");
        }
        delegates.put(clazz, nextID++);
        factories.add(factory);
    }


    public int getID(ITarget target) {
        return getID(target.getClass());
    }

    public int getID(Class<? extends ITarget> clazz) {
        if (!delegates.containsKey(clazz)) {
            throw new IllegalArgumentException("The class key " + clazz + " hasn't been registered yet.");
        }
        return delegates.get(clazz);
    }

    public Supplier<? extends ITarget> getFactory(int id) {
        return factories.get(id);
    }

    public ITarget create(int id) {
        return getFactory(id).get();
    }

}
