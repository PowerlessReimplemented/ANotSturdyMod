package powerlessri.anotsturdymod.library.gui.simpleimpl;

import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.integration.ContextGuiUpdate;
import powerlessri.anotsturdymod.library.gui.simpleimpl.events.FocusListener;
import powerlessri.anotsturdymod.varia.general.Utils;

import javax.annotation.Nullable;
import java.util.Set;

public class CursorPositionHandler {

    private ComponentRoot root;

    private IComponent focusedComponent;
    private IComponent hoveringComponent;

    private Set<FocusListener> subscribers;

    public CursorPositionHandler(ComponentRoot root) {
        this.root = root;
        this.focusedComponent = root;
        this.hoveringComponent = root;
    }

    public void update(ContextGuiUpdate ctx) {
        for (IComponent leaf : root.getLeaves()) {
            if (leaf.isPointInside(ctx.getMouseX(), ctx.getMouseY())) {
                this.hoveringComponent = leaf;
                return;
            }
        }
        this.hoveringComponent = root;
    }

    public void onClickEvent(@Nullable IComponent target) {
        this.focusedComponent = Utils.selectNonnull(target, root);
    }

}
