package powerlessri.anotsturdymod.library.gui.simpleimpl;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.integration.ContextGuiUpdate;
import powerlessri.anotsturdymod.library.gui.simpleimpl.events.*;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class CursorPositionHandler {

    private final IComponent DEFAULT;

    private ImmutableList<IComponent> leaves;

    private IComponent focusedComponent;
    private IComponent hoveringComponent;

    private Map<IComponent, FocusListener> focusListeners = new HashMap<>();
    private Map<IComponent, HoveringListener> hoveringListeners = new HashMap<>();

    private HoveringEvent.Update updateHover = new HoveringEvent.Update();
    private FocusEvent.Update updateFocus = new FocusEvent.Update();

    private final FocusListener defaultFocusListener = new FocusListener() {
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
    private final HoveringListener defaultHoveringListener = new HoveringListener() {
        @Override
        public void onCursorEnter(HoveringEvent.Enter event) {
        }

        @Override
        public void onCursorLeave(HoveringEvent.Leave event) {
        }

        @Override
        public void update(HoveringEvent.Update event) {
        }
    };

    public CursorPositionHandler(IComponent defaultTarget, ImmutableList<IComponent> leaves) {
        this.DEFAULT = defaultTarget;
        this.leaves = leaves;
        this.focusedComponent = defaultTarget;
        this.registerFocus(defaultTarget, defaultFocusListener);
        this.hoveringComponent = defaultTarget;
        this.registerHovering(defaultTarget, defaultHoveringListener);
    }

    public void update(ContextGuiUpdate ctx) {
        int x = ctx.getMouseX();
        int y = ctx.getMouseY();
        if (!hoveringComponent.isPointInside(x, y)) {
            notifyLeaved();
            notifyEntered(x, y);
        }

        updateHover.nextTick();
        IComponent target = hoveringComponent;
        while (target != null && !target.isRootComponent()) {
            hoveringListeners.get(target).update(updateHover);
            target = target.getParentComponent();
        }
    }

    private void notifyLeaved() {
        hoveringListeners.get(hoveringComponent).onCursorLeave(new HoveringEvent.Leave());
        updateHover.reset();
    }

    private void notifyEntered(int targetX, int targetY) {
        searchForHovering(targetX, targetY);
        hoveringListeners.get(hoveringComponent).onCursorEnter(new HoveringEvent.Enter());
    }

    private void searchForHovering(int mouseX, int mouseY) {
        for (IComponent leaf : leaves) {
            if (leaf.isPointInside(mouseX, mouseY)) {
                hoveringComponent = leaf;
                return;
            }
        }
        // When cursor is floating above nothing
        hoveringComponent = DEFAULT;
    }


    public void onClickEvent(@Nonnull InteractionHandler target) {
        if (target != focusedComponent) {
            notifyOff();
            focusedComponent = Preconditions.checkNotNull(target);
            notifyOn();
        }

        updateFocus.nextTick();
        focusListeners.getOrDefault(focusedComponent, defaultFocusListener).update(updateFocus);
    }

    private void notifyOff() {
        focusListeners.getOrDefault(focusedComponent, defaultFocusListener).onUnfocus(new FocusEvent.Off());
        updateFocus.reset();
    }

    private void notifyOn() {
        focusListeners.getOrDefault(focusedComponent, defaultFocusListener).onFocus(new FocusEvent.On());
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
        return focusedComponent;
    }

    public IComponent getHoveringComponent() {
        return hoveringComponent;
    }

}
