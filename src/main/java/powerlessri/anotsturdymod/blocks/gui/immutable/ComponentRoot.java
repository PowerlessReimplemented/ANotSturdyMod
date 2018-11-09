package powerlessri.anotsturdymod.blocks.gui.immutable;

import com.google.common.collect.ImmutableList;
import javafx.scene.input.MouseButton;
import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.blocks.gui.api.IComponent;
import powerlessri.anotsturdymod.blocks.gui.api.group.IContainer;
import powerlessri.anotsturdymod.blocks.gui.api.render.EDisplayMode;
import powerlessri.anotsturdymod.blocks.gui.api.render.IDisplayEntry;
import powerlessri.anotsturdymod.blocks.gui.api.render.IRenderedComponent;

import javax.annotation.Nullable;
import java.util.List;

public class ComponentRoot implements IContainer, IDisplayEntry {

    private ImmutableList<IContainer<IRenderedComponent>> windows;
    private ImmutableList<IRenderedComponent> leaves;
    private GuiScreen gui;

    public ComponentRoot(GuiScreen gui, ImmutableList<IContainer<IRenderedComponent>> windows) {
        this.gui = gui;
        this.windows = windows;
        this.leaves = searchForLeaves(windows);
    }

    private ImmutableList<IRenderedComponent> searchForLeaves(ImmutableList<IContainer<IRenderedComponent>> subComponents) {
        ImmutableList.Builder<IRenderedComponent> leaves = ImmutableList.builder();
        for (IContainer<IRenderedComponent> window : windows) {
            searchForLeavesRecursive(window, leaves);
        }
        return leaves.build();
    }

    private void searchForLeavesRecursive(IContainer<IRenderedComponent> parent, ImmutableList.Builder<IRenderedComponent> leaves) {
        for (IRenderedComponent component : parent.getComponents()) {
            if (component.isLeafComponent()) {
                leaves.add(component);
                continue;
            }

            if (component instanceof IContainer) {
                @SuppressWarnings("unchecked")
                IContainer<IRenderedComponent> subContainer  = (IContainer<IRenderedComponent>) component;
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
        for (IContainer<IRenderedComponent> window : windows) {
            window.tryDraw();
        }
    }


    @Override
    public List<IRenderedComponent> getComponents() {
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
    public int getAbsoluteX() {
        return 0;
    }

    @Override
    public int getAbsoluteY() {
        return 0;
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


    public void onMouseClicked(int mouseX, int mouseY, int flag) {
        MouseButton button = getMouseButton(flag);
    }

    public void onMouseReleased(int mouseX, int mouseY, int state) {

    }

    private MouseButton getMouseButton(int flag) {
        switch (flag) {
            case 0:
                return MouseButton.PRIMARY;
            case 1:
                return MouseButton.SECONDARY;

            default:
                return MouseButton.NONE;
        }
    }

}
