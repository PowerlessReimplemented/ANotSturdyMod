package powerlessri.anotsturdymod.blocks.gui.immutable;

import com.google.common.collect.ImmutableList;
import javafx.scene.input.MouseButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.world.IInteractionObject;
import org.lwjgl.input.Mouse;
import powerlessri.anotsturdymod.blocks.gui.api.IComponent;
import powerlessri.anotsturdymod.blocks.gui.api.group.IContainer;
import powerlessri.anotsturdymod.blocks.gui.api.render.EDisplayMode;
import powerlessri.anotsturdymod.blocks.gui.api.render.EEventType;
import powerlessri.anotsturdymod.blocks.gui.api.render.IDisplayEntry;
import powerlessri.anotsturdymod.blocks.gui.api.render.IInteractionComponent;

import javax.annotation.Nullable;
import java.util.List;

public class ComponentRoot implements IContainer {

    private ImmutableList<IContainer<IComponent>> windows;
    private ImmutableList<IComponent> leaves;
    private GuiScreen gui;

    public ComponentRoot(GuiScreen gui, ImmutableList<IContainer<IComponent>> windows) {
        this.gui = gui;
        this.windows = windows;
        this.leaves = searchForLeaves(windows);
    }

    private ImmutableList<IComponent> searchForLeaves(ImmutableList<IContainer<IComponent>> subComponents) {
        ImmutableList.Builder<IComponent> leaves = ImmutableList.builder();
        for (IContainer<IComponent> window : windows) {
            searchForLeavesRecursive(window, leaves);
        }
        return leaves.build();
    }

    private void searchForLeavesRecursive(IContainer<IComponent> parent, ImmutableList.Builder<IComponent> leaves) {
        for (IComponent component : parent.getComponents()) {
            if (component.isLeafComponent()) {
                leaves.add(component);
                continue;
            }

            if (component instanceof IContainer) {
                IContainer<IComponent> subContainer  = (IContainer<IComponent>) component;
                searchForLeavesRecursive(subContainer, leaves);
            }
        }
    }


    @Override
    public EDisplayMode getDisplay() {
        return EDisplayMode.CUSTOM;
    }

    @Override
    public void draw() {
        for (IContainer<IComponent> window : windows) {
            window.tryDraw();
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
    public int getAbsoluteX() {
        return 0;
    }

    @Override
    public int getAbsoluteY() {
        return 0;
    }

    @Override
    public boolean isPointInBound(int x, int y) {
        return true;
    }

    @Override
    public void resetAbsolutePosition(int x, int y) {
    }

    @Override
    public int getZIndex() {
        return 0;
    }

    @Override
    public void setZIndex(int zIndex) {
    }


    public void onMouseClicked(int mouseX, int mouseY, MouseButton button) {
        for (IComponent component : leaves) {
            if (component instanceof IInteractionComponent) {
                IInteractionComponent interactableComp = ((IInteractionComponent) component);
                if (!interactableComp.isPointInBound(mouseX, mouseY)) {
                    continue;
                }

                // Original event
                interactableComp.onClicked(mouseX, mouseY, button, EEventType.ORIGINAL);

                // Bubbling events
                IComponent target = interactableComp;
                while (!target.isRootComponent()) {
                    if (target instanceof IInteractionComponent) {
                        ((IInteractionComponent) target).onClicked(mouseX, mouseY, button, EEventType.BUBBLE);
                    }

                    target = target.getParentComponent();
                }
            }
        }
    }

    public void onMouseReleased(int mouseX, int mouseY, MouseButton button) {
    }

}
