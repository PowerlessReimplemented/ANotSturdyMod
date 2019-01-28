package powerlessri.anotsturdymod.library.gui.simpleimpl;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.EnumActionResult;
import powerlessri.anotsturdymod.library.gui.api.EEventType;
import powerlessri.anotsturdymod.library.gui.api.EMouseButton;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.integration.ContextGuiUpdate;
import powerlessri.anotsturdymod.library.gui.simpleimpl.events.*;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CursorEventManager implements CursorEventBus {

    static final HoveringListener DEFAULT_HOVER_LISTENER = new HoveringListener() {
        @Override
        public void onCursorEnter(HoveringEvent.Enter event) {
        }

        @Override
        public void onCursorLeave(HoveringEvent.Leave event) {
        }
    };

    static final FocusListener DEFAULT_FOCUS_LISTENER = new FocusListener() {
        @Override
        public void onFocus(FocusEvent.On event) {
        }

        @Override
        public void onUnfocus(FocusEvent.Off event) {
        }

        @Override
        public void update(FocusEvent.Update event) {
        }
    };


    public static CursorEventManager forLeaves(IComponent root, ImmutableList<IComponent> leaves) {
        return new CursorEventManager(root, leaves, ComponentStructureProjector.handlers(leaves));
    }

    private IComponent root;
    private List<IComponent> leaves;
    private List<InteractionHandler> handlers;

    private InteractionHandler clickedHandler;

    private IComponent focus;
    private IComponent hover;

    private Map<IComponent, FocusListener> focusListeners = new HashMap<>();
    private Map<IComponent, HoveringListener> hoveringListeners = new HashMap<>();

    private CursorEventManager(IComponent root, List<IComponent> leaves, List<InteractionHandler> handlers) {
        this.root = root;
        this.leaves = leaves;
        this.handlers = handlers;
        this.hover = root;
        this.registerHovering(root, DEFAULT_HOVER_LISTENER);
        this.focus = root;
        this.registerFocus(root, DEFAULT_FOCUS_LISTENER);
    }


    public void fireClickedEvent(int mx, int my, EMouseButton button) {
        for (IComponent leaf : handlers) {
            if (leaf.isPointInside(mx, my)) {
                this.onFocus(focus, leaf);
                focus = leaf;

                if (leaf instanceof InteractionHandler) {
                    this.onHandlerClicked(mx, my, button, (InteractionHandler) leaf);
                }
            }
        }
    }

    public void onFocus(IComponent oldHandler, IComponent newHandler) {
        // Do it this way since we allow different components to be bond to the same listener
        if (oldHandler != newHandler) {
            FocusListener oldListener = focusListeners.getOrDefault(oldHandler, DEFAULT_FOCUS_LISTENER);
            FocusListener newListener = focusListeners.getOrDefault(newHandler, DEFAULT_FOCUS_LISTENER);

            oldListener.onUnfocus(new FocusEvent.Off());
            newListener.onFocus(new FocusEvent.On());
        }
    }

    private void onHandlerClicked(int x, int y, EMouseButton button, InteractionHandler handler) {
        if (handler.doesReceiveEvents()) {
            clickedHandler = handler;

            EnumActionResult result = handler.onClicked(x, y, button, EEventType.ORIGINAL);
            if (result == EnumActionResult.FAIL) {
                return;
            }

            bubbleUpEvent(handler.getParentComponent(), (target) -> target.onClicked(x, y, button, EEventType.BUBBLE));
        }
    }

    public void fireDraggingEvent(int mx, int my, EMouseButton button, long timePressed) {
        if (clickedHandler != null) {
            clickedHandler.onClickedDragging(mx, my, button, timePressed);
        }
    }

    public void fireHoveringEvent(int mx, int my) {
        for (InteractionHandler handler : handlers) {
            if (handler != clickedHandler && handler.isPointInside(mx, my) && handler.isVisible()) {
                handler.onHovering(mx, my);
                bubbleUpEvent(handler, target -> target.onHovering(mx, my));
            }
        }
    }

    public void fireReleasedEvent(int mx, int my, EMouseButton button) {
        if (clickedHandler != null && clickedHandler.doesReceiveEvents()) {
            EnumActionResult result = clickedHandler.onReleased(mx, my, button, EEventType.ORIGINAL);
            if (result == EnumActionResult.FAIL) {
                return;
            }

            bubbleUpEvent(clickedHandler.getParentComponent(), target -> target.onReleased(mx, my, button, EEventType.BUBBLE));
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


    public void update(ContextGuiUpdate ctx) {
        int x = ctx.getMouseX();
        int y = ctx.getMouseY();
        if (!hover.isPointInside(x, y)) {
            notifyLeaved();
            hover = searchForHovering(x, y);
            notifyEntered();
        }
    }

    @Override
    public void handleMouseClick(int x, int y, EMouseButton button) {

    }

    @Override
    public void handleMouseRelease(int x, int y, EMouseButton button) {

    }

    private void notifyLeaved() {
        hoveringListeners.get(hover).onCursorLeave(new HoveringEvent.Leave());
    }

    private void notifyEntered() {
        hoveringListeners.get(hover).onCursorEnter(new HoveringEvent.Enter());
    }

    private IComponent searchForHovering(int mouseX, int mouseY) {
        for (IComponent leaf : leaves) {
            if (leaf.isPointInside(mouseX, mouseY)) {
                return leaf;
            }
        }
        // When cursor is floating above nothing
        return root;
    }



    public void registerFocus(IComponent component, FocusListener subscriber) {
        focusListeners.put(component, subscriber);
    }

    public void unregisterFocus(IComponent component) {
        focusListeners.remove(component);
    }

    public void registerHovering(IComponent component, HoveringListener subscriber) {
        hoveringListeners.put(component, subscriber);
    }

    public void unregisterHovering(IComponent component) {
        hoveringListeners.remove(component);
    }


    public IComponent getFocusedComponent() {
        return focus;
    }

    @Override
    public FocusListener getCurrentFocusListener() {
        return focusListeners.get(getFocusedComponent());
    }

    public IComponent getHoveringComponent() {
        return hover;
    }

    @Override
    public HoveringListener getCurrentHoveringListener() {
        return hoveringListeners.get(getHoveringComponent());
    }

    @Override
    public InteractionHandler getLastClickedHandler() {
        return clickedHandler;
    }


    @Override
    public boolean supportsBubbling() {
        return false;
    }

    @Override
    public boolean supportsCapture() {
        return false;
    }

    @Override
    public void enableBubbling() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void disableBubbling() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void enableCapture() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void disableCapture() {
        throw new UnsupportedOperationException();
    }

}
