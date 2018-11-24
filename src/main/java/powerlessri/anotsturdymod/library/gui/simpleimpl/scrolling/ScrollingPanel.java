package powerlessri.anotsturdymod.library.gui.simpleimpl.scrolling;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractComponent;
import powerlessri.anotsturdymod.varia.general.Utils;

import javax.rmi.CORBA.Util;
import java.util.List;

public class ScrollingPanel extends AbstractComponent implements IScrollingPanel {

    /**
     * Gap between every component drawn on the panel.
     */
    private int verticalGap = 2;

    private int width;
    private int height;


    private IScrollBar scrollBar;

    /**
     * List of components that will get scrolled. Does not include {@link #scrollBar}.
     */
    private ImmutableList<IScrollableComponent> components;

    /**
     * A shared height for every allRelated component. Equivalent to {@code components.get(0).getHeight()}.
     */
    private int commonHeight;
    /**
     * The height of expanded components. (The total height of all components that is required to display them all if not using scroll panel)
     * Formula: components.size() * (commonHeight + verticalGap). Does not include whitespace on the top and bottom.
     */
    private int contentHeight;
    /**
     * Scale factor from height of expanded height to to current available height for displaying.
     * (i.e. from {@link #contentHeight} to {@link #height})
     */
    private float contentKFactor;

    /**
     * Maximum number of components that will be drawn at a time.
     */
    private int visibleComponents;

    /**
     * Index of the first component that gets drawn.
     */
    private int entryIndex;

    public ScrollingPanel(int relativeX, int relativeY, int width, int height, ImmutableList<IScrollableComponent> content, ChunkyScrollBar bar) {
        super(relativeX, relativeY);

        this.components = content;
        this.width = width;
        this.setHeight(height);

        this.scrollBar = bar;
    }


    @Override
    public void initialize(GuiScreen gui, IComponent parent) {
        super.initialize(gui, parent);

        checkScrollBarHeight();
        checkComponentHeight();

        for (IScrollableComponent component : components) {
            component.initialize(gui, this);
        }
        scrollBar.initialize(gui, this);
    }

    private void checkScrollBarHeight() {
        if (getHeight() != scrollBar.getMaximumHeight()) {
            throw new IllegalArgumentException("Scroll bar must have the same height as the panel it belongs to.");
        }
    }

    private void checkComponentHeight() {
        int commonHeight = components.get(0).getHeight();
        for (IScrollableComponent component : components) {
            if (component.getHeight() != commonHeight) {
                throw new IllegalArgumentException("All components passed to ScrollingPanel must have equal amount of height!");
            }
        }

        this.commonHeight = commonHeight;
        this.contentHeight = components.size() * (commonHeight + verticalGap);
        this.contentKFactor = (float) getHeight() / getContentHeight();
        Utils.getLogger().info("ch " + contentHeight + "ckf " + contentKFactor);
    }


    @Override
    public void draw(GuiDrawBackgroundEvent event) {
        int displayHeight = commonHeight + verticalGap;
        int endIndex = entryIndex + visibleComponents;

        int nextPenDownY = getActualY() + verticalGap;
        for (int i = entryIndex; i < endIndex; i++) {
            nextPenDownY += displayHeight;
            components.get(i).draw(event, nextPenDownY);
        }

        scrollBar.draw(event);
    }


    // TODO decide private or public
    public void setHeight(int height) {
        int componentHeight = commonHeight + verticalGap;
        int usableHeight = height - (verticalGap * 2);
        int visibleComponents = Math.min(components.size(), usableHeight / componentHeight);

        // TODO add size check
//        int hiddenComponents = components.size() - visibleComponents;
//        if (hiddenComponents > height) {
//            throw new IllegalArgumentException("Unable to fit all components with the given height " + height + "!");
//        }

        this.height = height;
        this.visibleComponents = visibleComponents;
    }

    @Override
    public int getTotalSteps() {
        return components.size() - visibleComponents + 1;
    }

    @Override
    public int getCurrentStep() {
        return entryIndex;
    }

    @Override
    public void setCurrentStep(int step) {
        entryIndex = Math.min(0, step);
    }


    public int getContentHeight() {
        return contentHeight;
    }

    @Override
    public float getContentKFactor() {
        return contentKFactor;
    }

    @Override
    public boolean isLeafComponent() {
        return false;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    /**
     * Collection containing {@link #components} and {@link #scrollBar}
     */
    private ImmutableList<IComponent> allRelated;

    @Override
    public List<IComponent> getComponents() {
        // This operation is pretty costly, lazy evaluation to ensure there will not be huge amount of (unnecessary) iterations going on when player open the GUI
        if (allRelated == null) {
            ImmutableList.Builder<IComponent> builder = ImmutableList.builder();
            builder.addAll(components);
            builder.add(scrollBar);
            allRelated = builder.build();
        }
        return allRelated;
    }

    public List<IScrollableComponent> getScrollableComponents() {
        return components;
    }

    @Override
    public boolean acceptsZIndex() {
        return false;
    }

}
