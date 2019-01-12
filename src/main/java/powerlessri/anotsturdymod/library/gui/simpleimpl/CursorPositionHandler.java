package powerlessri.anotsturdymod.library.gui.simpleimpl;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.events.InteractionHandler;
import powerlessri.anotsturdymod.library.gui.integration.ContextGuiUpdate;
import powerlessri.anotsturdymod.library.gui.simpleimpl.events.FocusEvent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.events.FocusListener;
import powerlessri.anotsturdymod.library.gui.simpleimpl.events.HoveringEvent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.events.HoveringListener;

import javax.annotation.Nonnull;
import java.util.Map;

public class CursorPositionHandler {

    private final IComponent DEFAULT;

    private ImmutableList<IComponent> leaves;

    private InteractionHandler focusedComponent;
    private IComponent hoveringComponent;

    private Map<InteractionHandler, FocusListener> focusListeners;
    private Map<IComponent, HoveringListener> hoveringListeners;

    private HoveringEvent.Update updateHover = new HoveringEvent.Update();
    private FocusEvent.Update updateFocus = new FocusEvent.Update();

    public CursorPositionHandler(IComponent defaultTarget, ImmutableList<IComponent> leaves) {
        this.DEFAULT = defaultTarget;
        this.leaves = leaves;
        this.hoveringComponent = defaultTarget;
    }

    public void update(ContextGuiUpdate ctx) {
        int x = ctx.getMouseX();
        int y = ctx.getMouseY();
        if (!hoveringComponent.isPointInside(x, y)) {
            notifyLeaved();
            searchForHovering(x, y);
            notifyEntered();
        }

        updateHover.nextTick();
        hoveringListeners.get(hoveringComponent).update(updateHover);
    }

    private void notifyLeaved() {
        hoveringListeners.get(hoveringComponent).onCursorLeave(new HoveringEvent.Leave());
        updateHover.reset();
    }

    private void notifyEntered() {
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
        focusListeners.get(focusedComponent).update(updateFocus);
    }

    private void notifyOff() {
        focusListeners.get(focusedComponent).onUnfocus(new FocusEvent.Off());
        updateFocus.reset();
    }

    private void notifyOn() {
        focusListeners.get(focusedComponent).onFocus(new FocusEvent.On());
    }


    public void register(InteractionHandler component, FocusListener subscriber) {
        focusListeners.put(component, subscriber);
    }

    public void unregister(InteractionHandler component) {
        focusListeners.remove(component);
    }

    public void register(IComponent component, HoveringListener subscriber) {
        hoveringListeners.put(component, subscriber);
    }

    public void unregister(IComponent component) {
        hoveringListeners.remove(component);
    }


    public IComponent getFocusedComponent() {
        return focusedComponent;
    }

    public IComponent getHoveringComponent() {
        return hoveringComponent;
    }

}
