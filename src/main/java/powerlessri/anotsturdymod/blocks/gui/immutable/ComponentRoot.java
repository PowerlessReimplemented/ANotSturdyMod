package powerlessri.anotsturdymod.blocks.gui.immutable;

import com.google.common.collect.ImmutableList;
import javafx.scene.input.MouseButton;
import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.blocks.gui.api.IComponent;
import powerlessri.anotsturdymod.blocks.gui.api.group.IContainer;
import powerlessri.anotsturdymod.blocks.gui.api.render.EDisplayMode;
import powerlessri.anotsturdymod.blocks.gui.api.render.IDisplayEntry;

import javax.annotation.Nullable;
import java.util.List;

public class ComponentRoot implements IContainer, IDisplayEntry {

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


    public void onMouseClicked(int mouseX, int mouseY, MouseButton button) {
    }

    public void onMouseReleased(int mouseX, int mouseY, int state) {

    }

    

}
