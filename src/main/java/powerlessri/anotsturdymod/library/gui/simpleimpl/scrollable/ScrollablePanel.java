package powerlessri.anotsturdymod.library.gui.simpleimpl.scrollable;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractComponent;

import java.util.List;

public class ScrollablePanel extends AbstractComponent implements IScrollingPanel {

    public static final int DEFAULT_PADDING = 2;

    public static ScrollablePanel simpleLayout(int x, int y, int width, int amountVisibleComponents, ImmutableList<IScrollableComponent> components) {
        return simpleLayout(x, y, width, amountVisibleComponents, components, DEFAULT_PADDING);
    }

    public static ScrollablePanel simpleLayout(int x, int y, int width, int amountVisibleComponents, ImmutableList<IScrollableComponent> components, int scrollbarDistance) {
        int componentHeight = components.get(0).getHeight() + DEFAULT_PADDING;
        // Top gap only. Bottom gap is included in last component's height
        int panelHeight = (amountVisibleComponents * componentHeight) + DEFAULT_PADDING;

        SlidingScrollbar scrollbar = new SlidingScrollbar(scrollbarDistance, 0, panelHeight);
        return new ScrollablePanel(x, y, width, panelHeight, components, scrollbar);
    }


    /**
     * Padding between every component and side of the panel.
     */
    private int verticalPadding = DEFAULT_PADDING;

    private int width;
    private int height;


    private IScrollbar scrollbar;
    private ImmutableList<IScrollableComponent> components;

    /**
     * A shared height for all components.
     */
    private int commonHeight;
    private int contentHeight;
    private float contentScaleFactor;

    /**
     * Maximum number of components that can be drawn at a time.
     */
    private int visibleComponents;

    private int entryIndex;

    /**
     * Construct a scrollable panel with a completely custom height.
     * <p>
     * It should be noted that in some cases, using an incomplete height (all display desires cannot fill the given height)
     * and a special number of components, the last component might be excluded to display.
     * One (hack) solution to this problem is to ensure the panel height is always a multiple of components' display height.
     * </p>
     * <p>Example of an  case: {@code height = 80, componentHeight = 10, numberOComponents = 32}</p>
     *
     * @param height The custom height value
     */
    public ScrollablePanel(int relativeX, int relativeY, int width, int height, ImmutableList<IScrollableComponent> components, SlidingScrollbar scrollbar) {
        super(relativeX, relativeY);
        this.width = width;
        this.height = height;
        this.components = components;
        this.scrollbar = scrollbar;
    }


    @Override
    public void initialize(GuiScreen gui, IComponent parent) {
        super.initialize(gui, parent);

        this.checkComponentHeight();
        this.checkScrollBarHeight();
        this.calculateHeight();

        for (IScrollableComponent component : components) {
            component.initialize(gui, this);
            component.setVisibility(false);
            component.setExpectedY(-1);
        }
        // Make all components visible
        this.setCurrentStep(0);

        scrollbar.initialize(gui, this);
    }

    private void checkComponentHeight() {
        int candidate = components.get(0).getHeight();
        for (IScrollableComponent component : components) {
            if (component.getHeight() != candidate) {
                throw new IllegalArgumentException("Components passed to " + this.toString() + " does not have an equal number of height");
            }
        }
        this.commonHeight = candidate;
    }

    private void checkScrollBarHeight() {
        if (getHeight() != scrollbar.getMaximumHeight()) {
            throw new IllegalArgumentException("Scroll bar must have the same height as the panel it belongs to.");
        }
    }

    private void calculateHeight() {
        // The verticalPadding is used at the bottom of the component
        int componentHeight = commonHeight + verticalPadding;
        // We only need to count to top margin, the bottom margin is included in the height of last component that gets displayed.
        int usableHeight = height - verticalPadding;
        this.visibleComponents = Math.min(components.size(), usableHeight / componentHeight);

        // TODO remove dirty tricks
        // Dirty trick: (components.size() + 1) to fix the problem that last component get ignored when drawn
        this.contentHeight = (components.size() + 1) * (commonHeight + verticalPadding);
        this.contentScaleFactor = (float) getHeight() / getContentHeight();
    }


    @Override
    public void draw(GuiDrawBackgroundEvent event) {
        for (IScrollableComponent component : components) {
            component.draw(event);
        }
        scrollbar.draw(event);
    }


    @Override
    public int getTotalSteps() {
        return components.size() - visibleComponents;
    }

    /**
     * @see #getEntryIndex()
     */
    @Override
    public int getCurrentStep() {
        return entryIndex;
    }

    @Override
    public void setCurrentStep(int step) {
        int oldEntryIndex = entryIndex;
        int oldEndIndex = this.getEndIndex();
        this.entryIndex = Math.max(0, step);

        int smallerEntryIndex = Math.min(oldEntryIndex, getEndIndex());
        int largerEndIndex = Math.max(oldEndIndex, getEndIndex());
        int contentTopY = getActualY() + verticalPadding;
        int componentHeight = this.getComponentHeight();

        // Go through both old and new ranges, refresh the state of those components
        for (int i = smallerEntryIndex, nextPenDownY = contentTopY; i < largerEndIndex; i++) {
            IScrollableComponent component = components.get(i);

            if (shouldComponentVisible(i)) {
                component.setVisibility(true);
                component.setExpectedY(nextPenDownY);
                nextPenDownY += componentHeight;
            } else {
                component.setVisibility(false);
                component.setExpectedY(-1);
            }
        }
    }

    private boolean shouldComponentVisible(int index) {
        return index >= entryIndex && index < getEndIndex();
    }

    /**
     * Index of the first component which gets drawn.
     */
    public int getEntryIndex() {
        return entryIndex;
    }

    /**
     * Index of the last component which gets drawn.
     */
    public int getEndIndex() {
        return entryIndex + visibleComponents;
    }

    /**
     * The height of a component when used to calculate scrollable list layout.
     */
    public int getComponentHeight() {
        return commonHeight + verticalPadding;
    }


    /**
     * The length that is required to display all of the components when not using IScrollablePanel
     * <p>
     * Formula: <i>components.size() * (commonHeight + verticalPadding)</i>.
     * Does not include whitespace on the top and bottom.
     * </p>
     */
    public int getContentHeight() {
        return contentHeight;
    }

    /**
     * <p>
     * Scale factor from length of all components to to current available height for displaying.
     * In other words, from {@link #getContentHeight()} to {@link #getHeight()}
     * </p>
     */
    @Override
    public float getContentScaleFactor() {
        return contentScaleFactor;
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
     * Collection containing {@link #components} and {@link #scrollbar}
     */
    private ImmutableList<IComponent> allRelated;

    /**
     * All components including the scroll bar.
     */
    @Override
    public List<IComponent> getComponents() {
        // This operation is pretty costly, lazy evaluation to ensure there will not be huge amount of (possibly) unnecessary iterations preceded on when player opens the GUI
        if (allRelated == null) {
            ImmutableList.Builder<IComponent> builder = ImmutableList.builder();
            builder.addAll(components);
            builder.add(scrollbar);
            allRelated = builder.build();
        }
        return allRelated;
    }

    /**
     * All components except the scroll bar.
     */
    public List<IScrollableComponent> getScrollableComponents() {
        return components;
    }

    @Override
    public boolean acceptsZIndex() {
        return false;
    }

    @Override
    public String toString() {
        return "ScrollablePanel{" + super.toString() + "}";
    }

}
