package powerlessri.anotsturdymod.library.gui.immutableimpl;

import com.google.common.collect.ImmutableList;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.api.IContainer;

public class StructureProjectingUtils {
    
    private StructureProjectingUtils() {
    }


    public static ImmutableList<IComponent> searchForLeaves(ImmutableList<IContainer<IComponent>> subComponents) {
        ImmutableList.Builder<IComponent> leaves = ImmutableList.builder();
        for (IContainer<IComponent> window : subComponents) {
            searchForLeavesRecursive(window, leaves);
        }
        return leaves.build();
    }

    private static void searchForLeavesRecursive(IContainer<? extends IComponent> parent, ImmutableList.Builder<IComponent> leaves) {
        for (IComponent component : parent.getComponents()) {
            if (component.isLeafComponent()) {
                leaves.add(component);
                continue;
            }

            if (component instanceof IContainer) {
                IContainer<? extends IComponent> subContainer  = (IContainer<? extends IComponent>) component;
                searchForLeavesRecursive(subContainer, leaves);
            }
        }
    }
    
}
