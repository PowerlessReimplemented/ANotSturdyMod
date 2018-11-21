package powerlessri.anotsturdymod.library.gui.simpleimpl;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.EnumActionResult;
import powerlessri.anotsturdymod.library.gui.api.*;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class EventManager {
    
    public static EventManager forLeaves(ImmutableList<IComponent> leaves) {
        EventManager eventManager = new EventManager();
        
        ImmutableList.Builder<IInteractionHandler> handlerBuilder = ImmutableList.builder();
        for (IComponent leaf : leaves) {
            if (leaf instanceof IInteractionHandler) {
                handlerBuilder.add((IInteractionHandler) leaf);
            }
        }
        
        eventManager.handlers = handlerBuilder.build();
        eventManager.visible = new HashSet<>(eventManager.handlers);
        return eventManager;
    }
    

    private List<IInteractionHandler> handlers;
    private Set<IInteractionHandler> visible;
    private IInteractionHandler lastClicked;

    public void emitMouseClicked(int mouseX, int mouseY, EMouseButton button) {
        for (IInteractionHandler handler : visible) {
            if (handler.isPointInside(mouseX, mouseY)) {
                lastClicked = handler;
                if (!handler.doesReceiveEvents()) {
                    return;
                }

                EnumActionResult result = handler.onClicked(mouseX, mouseY, button, EEventType.ORIGINAL);
                if (result == EnumActionResult.FAIL) {
                    return;
                }

                bubbleUpEvent(handler.getParentComponent(), (target) -> target.onClicked(mouseX, mouseY, button, EEventType.BUBBLE));
                break;
            }
        }
    }


    public void markVisible(IInteractionHandler component) {
        visible.add(component);
    }
    
    public void markInvisible(IInteractionHandler component) {
        visible.remove(component);
    }
    

    public void emitClickedDragging(int mouseX, int mouseY, EMouseButton button, long timePressed) {
        if (lastClicked != null) {
            lastClicked.onClickedDragging(mouseX, mouseY, button, timePressed);
        }
    }

    public void emitHoveringDragging(int mouseX, int mouseY, EMouseButton button, long timePressed) {
        for (IInteractionHandler handler : visible) {
            if (handler != lastClicked && handler.isPointInside(mouseX, mouseY)) {
                handler.onHoveredDragging(mouseX, mouseY, button);
            }
        }
    }

    public void emitMouseReleased(int mouseX, int mouseY, EMouseButton button) {
        if (lastClicked != null && lastClicked.doesReceiveEvents()) {
            EnumActionResult result = lastClicked.onReleased(mouseX, mouseY, button, EEventType.ORIGINAL);
            if (result == EnumActionResult.FAIL) {
                return;
            }

            bubbleUpEvent(lastClicked.getParentComponent(), (target) -> target.onReleased(mouseX, mouseY, button, EEventType.BUBBLE));
        }
    }


    private void bubbleUpEvent(@Nullable IComponent target, Consumer<IInteractionHandler> event) {
        while (target != null && !target.isRootComponent()) {
            if (target instanceof IInteractionHandler) {
                event.accept((IInteractionHandler) target);
            }

            target = target.getParentComponent();
        }
    }
    
}
