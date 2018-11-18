package powerlessri.anotsturdymod.library.gui.immutableimpl;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumActionResult;
import powerlessri.anotsturdymod.library.gui.api.*;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class ComponentRoot implements IContainer {

    private ImmutableList<IContainer<IComponent>> windows;
    private ImmutableList<IComponent> leaves;
    private GuiScreen gui;

    public ComponentRoot(GuiScreen gui, ImmutableList<IContainer<IComponent>> windows) {
        this.gui = gui;
        this.windows = windows;
        this.leaves = searchForLeaves(windows);

        for (IComponent component : windows) {
            component.initialize(gui, this);
        }
    }

    private ImmutableList<IComponent> searchForLeaves(ImmutableList<IContainer<IComponent>> subComponents) {
        ImmutableList.Builder<IComponent> leaves = ImmutableList.builder();
        for (IContainer<IComponent> window : windows) {
            searchForLeavesRecursive(window, leaves);
        }
        return leaves.build();
    }

    private void searchForLeavesRecursive(IContainer<? extends IComponent> parent, ImmutableList.Builder<IComponent> leaves) {
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


    @Override
    public EDisplayMode getDisplay() {
        return EDisplayMode.ALL;
    }

    @Override
    public void draw(GuiDrawBackgroundEvent event) {
        for (IContainer<IComponent> window : windows) {
            window.tryDraw(event);
        }
    }


    @Override
    public List<IContainer<IComponent>> getComponents() {
        return windows;
    }

    @Override
    public boolean acceptsZIndex() {
        return false;
    }

    @Override
    public void initialize(GuiScreen gui, IComponent parent) {
        this.gui = gui;
    }

    @Override
    public GuiScreen getGui() {
        return gui;
    }

    @Nullable
    @Override
    public IComponent getParentComponent() {
        return this;
    }

    @Override
    public boolean isLeafComponent() {
        return false;
    }

    @Override
    public boolean isRootComponent() {
        return true;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public int setId(int id) {
        return 0;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public int getWidth() {
        return -1;
    }

    @Override
    public int getHeight() {
        return -1;
    }

    @Override
    public int getActualX() {
        return 0;
    }

    @Override
    public int getActualY() {
        return 0;
    }

    @Override
    public boolean isPointInside(int x, int y) {
        return true;
    }

    @Override
    public void forceActualPosition(int x, int y) {
    }

    @Override
    public int getZIndex() {
        return 0;
    }

    @Override
    public void setZIndex(int zIndex) {
    }


    private IInteractionHandler lastClicked;
    
    public void onMouseClicked(int mouseX, int mouseY, EMouseButton button) {
        for (IComponent component : leaves) {
            if (component instanceof IInteractionHandler && component.isPointInside(mouseX, mouseY)) {
                IInteractionHandler handler = ((IInteractionHandler) component);
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

    public void onMouseReleased(int mouseX, int mouseY, EMouseButton button) {
        if (lastClicked == null || !lastClicked.doesReceiveEvents()) {
            return;
        }
        
        EnumActionResult result = lastClicked.onReleased(mouseX, mouseY, button, EEventType.ORIGINAL);
        if (result == EnumActionResult.FAIL) {
            return;
        }
        
        bubbleUpEvent(lastClicked.getParentComponent(), (target) -> target.onReleased(mouseX, mouseY, button, EEventType.BUBBLE));
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
