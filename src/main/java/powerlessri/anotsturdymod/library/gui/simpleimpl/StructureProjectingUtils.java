package powerlessri.anotsturdymod.library.gui.simpleimpl;

import com.google.common.collect.ImmutableList;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.api.IContainer;

public class StructureProjectingUtils {

    private StructureProjectingUtils() {
    }


    public static ImmutableList<IComponent> flatten(ImmutableList<IContainer<IComponent>> windows) {
        ImmutableList.Builder<IComponent> flattened = ImmutableList.builder();
        for (IContainer<IComponent> window : windows) {
            flattenRecursive(window, flattened);
        }
        return flattened.build();
    }

    private static void flattenRecursive(IContainer<? extends IComponent> parent, ImmutableList.Builder<IComponent> flattened) {
        flattened.add(parent);
        for (IComponent component : parent.getComponents()) {
            flattened.add(component);

            if (component instanceof IContainer) { 
                flattenRecursive((IContainer<? extends IComponent>) component, flattened);
            }
        }
    }


    public static ImmutableList<IComponent> leaves(ImmutableList<IComponent> components) {
        ImmutableList.Builder<IComponent> leaves = ImmutableList.builder();
        for (IComponent component : components) {
            if (component.isLeafComponent()) {
                leaves.add(component);
            }
        }
        return leaves.build();
    }


}
