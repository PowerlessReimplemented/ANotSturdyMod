package powerlessri.anotsturdymod.library.gui.simpleimpl;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.EnumActionResult;
import powerlessri.anotsturdymod.library.gui.api.EEventType;
import powerlessri.anotsturdymod.library.gui.api.EMouseButton;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.api.IInteractionHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class MouseEventManager {

    public static MouseEventManager forLeaves(ImmutableList<IComponent> leaves) {
        return forHandlers(ComponentStructureProjector.handlers(leaves));
    }

    public static MouseEventManager forHandlers(ImmutableList<IInteractionHandler> handlers) {
        return new MouseEventManager(handlers);
    }


    private List<IInteractionHandler> handlers;
    private IInteractionHandler lastClicked;

    MouseEventManager(List<IInteractionHandler> handlers) {
        this.handlers = handlers;
    }


    public void emitMouseClicked(int mouseX, int mouseY, EMouseButton button) {
        for (IInteractionHandler handler : handlers) {
            if (handler.isPointInside(mouseX, mouseY) && handler.doesReceiveEvents()) {
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

    public void emitClickedDragging(int mouseX, int mouseY, EMouseButton button, long timePressed) {
        if (lastClicked != null) {
            lastClicked.onClickedDragging(mouseX, mouseY, button, timePressed);
        }
    }

    public void emitHoveringDragging(int mouseX, int mouseY, EMouseButton button, long timePressed) {
        for (IInteractionHandler handler : handlers) {
            if (handler != lastClicked && handler.isPointInside(mouseX, mouseY) && handler.isVisible()) {
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
