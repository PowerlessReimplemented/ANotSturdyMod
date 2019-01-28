package powerlessri.anotsturdymod.library.gui.simpleimpl;

import com.google.common.collect.ImmutableList;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.api.IContainer;
import powerlessri.anotsturdymod.library.gui.simpleimpl.events.IInteractionHandler;

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

    public static ImmutableList<IInteractionHandler> handlers(ImmutableList<IComponent> leaves) {
        ImmutableList.Builder<IInteractionHandler> handlers = ImmutableList.builder();
        for (IComponent leaf : leaves) {
            if (leaf instanceof IInteractionHandler) {
                handlers.add((IInteractionHandler) leaf);
            }
        }
        return handlers.build();
    }

    // TODO decided use stream or regular imperative min/max

    public static int findMinimumHeight(List<? extends IComponent> components) {
        if (components.size() == 0) {
            return 0;
        }

//        int top = components.stream()
//                .min(Comparator.comparingInt(IComponent::getActualY))
//                .orElseThrow(IllegalArgumentException::new)
//                .getActualY();
//        int bottom = components.stream()
//                .max(Comparator.comparingInt(IComponent::getActualYBottom))
//                .orElseThrow(IllegalArgumentException::new)
//                .getActualYBottom();
//        return bottom - top;

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

    public static int findMinimumWidth(List<? extends IComponent> components) {
        if (components.size() == 0) {
            return 0;
        }

//        int left = components.stream()
//                .min(Comparator.comparingInt(IComponent::getActualX))
//                .orElseThrow(IllegalArgumentException::new)
//                .getActualX();
//        int right = components.stream()
//                .max(Comparator.comparingInt(IComponent::getActualXRight))
//                .orElseThrow(IllegalArgumentException::new)
//                .getActualXRight();
//        return right - left;

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
