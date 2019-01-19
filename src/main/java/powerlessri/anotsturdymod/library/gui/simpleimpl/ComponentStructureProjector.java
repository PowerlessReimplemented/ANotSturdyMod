package powerlessri.anotsturdymod.library.gui.simpleimpl;

import com.google.common.collect.ImmutableList;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.api.IContainer;
import powerlessri.anotsturdymod.library.gui.simpleimpl.events.InteractionHandler;

import java.util.Comparator;
import java.util.List;

public class ComponentStructureProjector {

    private ComponentStructureProjector() {
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

    public static ImmutableList<InteractionHandler> handlers(ImmutableList<IComponent> leaves) {
        ImmutableList.Builder<InteractionHandler> handlers = ImmutableList.builder();
        for (IComponent leaf : leaves) {
            if (leaf instanceof InteractionHandler) {
                handlers.add((InteractionHandler) leaf);
            }
        }
        return handlers.build();
    }


    public static int findMinimumHeight(List<IComponent> components) {
//        int top = components.stream()
//                .min(Comparator.comparingInt(IComponent::getActualY))
//                .orElse(components.get(0))
//                .getActualYBottom();
//        int bottom = components.stream()
//                .max(Comparator.comparingInt(IComponent::getActualYBottom))
//                .orElse(components.get(0))
//                .getActualYBottom();

        if (components.size() == 0) {
            return 0;
        }

        int top = Integer.MAX_VALUE;
        int bottom = 0;
        for (IComponent component : components) {
            if (component == null) {
                continue;
            }

            if (component.getActualY() < top) {
                top = component.getActualY();
            }
            if (component.getActualYBottom() > bottom) {
                bottom = component.getActualYBottom();
            }
        }
        return bottom - top;

    }

    public static int findMinimumWidth(List<IComponent> components) {
//        int left = components.stream()
//                .min(Comparator.comparingInt(IComponent::getActualX))
//                .orElse(components.get(0))
//                .getActualXRight();
//        int right = components.stream()
//                .max(Comparator.comparingInt(IComponent::getActualXRight))
//                .orElse(components.get(0))
//                .getActualXRight();

        if (components.size() == 0) {
            return 0;
        }

        int left = Integer.MAX_VALUE;
        int right = 0;
        for (IComponent component : components) {
            if (component == null) {
                continue;
            }

            if (component.getActualY() < left) {
                left = component.getActualX();
            }
            if (component.getActualYBottom() > right) {
                right = component.getActualXRight();
            }
        }
        return right - left;

    }

}
