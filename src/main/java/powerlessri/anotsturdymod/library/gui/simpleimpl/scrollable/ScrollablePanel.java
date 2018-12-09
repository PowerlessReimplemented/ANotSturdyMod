package powerlessri.anotsturdymod.library.gui.simpleimpl.scrollable;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.api.IScrollableComponent;
import powerlessri.anotsturdymod.library.gui.api.IScrollbar;
import powerlessri.anotsturdymod.library.gui.api.IScrollingPanel;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractComponent;

import java.util.List;

public class ScrollablePanel extends AbstractComponent implements IScrollingPanel {

    public static final int DEFAULT_MARGIN = 2;

    public static ScrollablePanel simpleLayout(int x, int y, int width, int amountVisibleComponents, ImmutableList<IScrollableComponent> components) {
        return simpleLayout(x, y, width, amountVisibleComponents, components, DEFAULT_MARGIN);
    }

    public static ScrollablePanel simpleLayout(int x, int y, int width, int amountVisibleComponents, ImmutableList<IScrollableComponent> components, int scrollBarDistance) {
        int componentHeight = components.get(0).getHeight() + DEFAULT_MARGIN;
        // Top gap only. Bottom gap is included in last component's height
        int panelHeight = (amountVisibleComponents * componentHeight) + DEFAULT_MARGIN;

        ComponentScrollBar scrollBar = new ComponentScrollBar(scrollBarDistance, 0, panelHeight);
        return new ScrollablePanel(x, y, width, panelHeight, components, scrollBar);
    }


    /**
     * Gap between every component drawn on the panel.
     */
    private int marginTop = DEFAULT_MARGIN;

    private int width;
    private int height;


    private IScrollbar scrollBar;

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
     * Formula: components.size() * (commonHeight + marginTop). Does not include whitespace on the top and bottom.
     */
    private int contentHeight;
    /**
     * Scale factor from height of expanded height to to current available height for displaying.
     * (i.e. from {@link #contentHeight} to {@link #height})
     */
    private float contentScaleFactor;

    /**
     * Maximum number of components that will be drawn at a time.
     */
    private int visibleComponents;

    /**
     * Index of the first component that gets drawn.
     */
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
    public ScrollablePanel(int relativeX, int relativeY, int width, int height, ImmutableList<IScrollableComponent> content, SlidingScrollbar bar) {
        super(relativeX, relativeY);

        this.components = content;
        this.width = width;
        this.height = height;

        this.scrollBar = bar;
    }


    @Override
    public void initialize(GuiScreen gui, IComponent parent) {
        super.initialize(gui, parent);

        checkComponentHeight();
        checkScrollBarHeight();
        updateHeight();

        for (IScrollableComponent component : components) {
            component.initialize(gui, this);
            component.setVisibility(false);
            component.setExpectedY(-1);
        }
        // Make all components visible
        setCurrentStep(0);

        scrollBar.initialize(gui, this);

        
    }

    private void updateHeight() {
        int componentHeight = commonHeight + marginTop;
        // We only need to count to top gap, the bottom gap is included in the height of last component that gets displayed.
        int usableHeight = height - marginTop;
        visibleComponents = Math.min(components.size(), usableHeight / componentHeight);

        contentHeight = components.size() * (commonHeight + verticalGap);
        contentKFactor = (float) getHeight() / getContentHeight();
        
        // TODO add size check
//        int hiddenComponents = components.size() - visibleComponents;
//        if (hiddenComponents > height) {
//            throw new IllegalArgumentException("Unable to fit all components with the given height " + height + "!");
//        }
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
                throw new IllegalArgumentException("All components passed to ScrollablePanel must have equal amount of height!");
            }
        }

        this.commonHeight = commonHeight;
    }


    @Override
    public void draw(GuiDrawBackgroundEvent event) {
        for (IScrollableComponent component : components) {
            component.draw(event);
        }
        scrollBar.draw(event);
    }


    @Override
    public int getTotalSteps() {
        return components.size() - visibleComponents;
    }

    /**
     * Alias: {@code getEntryIndex()}
     */
    @Override
    public int getCurrentStep() {
        return entryIndex;
    }

    public int getEndIndex() {
        return entryIndex + visibleComponents;
    }

    public int getComponentHeight() {
        return commonHeight + marginTop;
    }

    public boolean isComponentShown(int index) {
        return index >= entryIndex && index < getEndIndex();
    }

    @Override
    public void setCurrentStep(int step) {
        int oldEntryIndex = entryIndex;
        int oldEndIndex = getEndIndex();
        entryIndex = Math.max(0, step);

        int endIndex = getEndIndex();
        int componentHeight = getComponentHeight();
        int minimumEntryIndex = Math.min(oldEntryIndex, entryIndex);
        int maximumEndIndex = Math.max(oldEndIndex, endIndex);

        for (int i = minimumEntryIndex, nextPenDownY = getActualY() + marginTop; i < maximumEndIndex; i++) {
            IScrollableComponent component = components.get(i);

            if (isComponentShown(i)) {
                component.setVisibility(true);

                component.setExpectedY(nextPenDownY);
                nextPenDownY += componentHeight;
            } else {
                component.setVisibility(false);
                component.setExpectedY(-1);
            }
        }
    }


    public int getContentHeight() {
        return contentHeight;
    }

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
     * Collection containing {@link #components} and {@link #scrollBar}
     */
    private ImmutableList<IComponent> allRelated;

    /**
     * All components including the scroll bar.
     */
    @Override
    public List<IComponent> getComponents() {
        // This operation is pretty costly, lazy evaluation to ensure there will not be huge amount of (possibly) unnecessary iterations going on when player open the GUI
        if (allRelated == null) {
            ImmutableList.Builder<IComponent> builder = ImmutableList.builder();
            builder.addAll(components);
            builder.add(scrollBar);
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

}
