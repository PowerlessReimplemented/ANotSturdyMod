package powerlessri.anotsturdymod.library.gui.simpleimpl;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.EnumActionResult;
import powerlessri.anotsturdymod.library.gui.api.EEventType;
import powerlessri.anotsturdymod.library.gui.api.EMouseButton;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.events.InteractionHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class MouseEventManager {

    public static MouseEventManager forLeaves(ImmutableList<IComponent> leaves, CursorPositionHandler cursorPositionHandler) {
        return forHandlers(ComponentStructureProjector.handlers(leaves), cursorPositionHandler);
    }

    public static MouseEventManager forHandlers(ImmutableList<InteractionHandler> handlers, CursorPositionHandler cursorPositionHandler) {
        return new MouseEventManager(handlers, cursorPositionHandler);
    }


    private List<InteractionHandler> handlers;
    private InteractionHandler lastClicked;

    private CursorPositionHandler cursorPositionHandler;

    MouseEventManager(List<InteractionHandler> handlers, CursorPositionHandler handler) {
        this.handlers = handlers;
        this.cursorPositionHandler = handler;
    }


    public void emitMouseClicked(int mouseX, int mouseY, EMouseButton button) {
        for (InteractionHandler handler : handlers) {
            if (handler.isPointInside(mouseX, mouseY) && handler.doesReceiveEvents()) {
                lastClicked = handler;
                if (!handler.doesReceiveEvents()) {
                    return;
                }
                this.cursorPositionHandler.onClickEvent(handler);

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

    public void emitHovering(int mouseX, int mouseY) {
        for (InteractionHandler handler : handlers) {
            if (handler != lastClicked && handler.isPointInside(mouseX, mouseY) && handler.isVisible()) {
                handler.onHovering(mouseX, mouseY);
                bubbleUpEvent(handler, target -> target.onHovering(mouseX, mouseY));
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


    private void bubbleUpEvent(@Nullable IComponent target, Consumer<InteractionHandler> event) {
        while (target != null && !target.isRootComponent()) {
            if (target instanceof InteractionHandler) {
                event.accept((InteractionHandler) target);
            }

            target = target.getParentComponent();
        }
    }

}
