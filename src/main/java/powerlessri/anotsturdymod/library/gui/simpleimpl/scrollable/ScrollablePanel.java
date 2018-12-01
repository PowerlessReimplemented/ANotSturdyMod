package powerlessri.anotsturdymod.library.gui.simpleimpl.scrollable;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractComponent;
import powerlessri.anotsturdymod.varia.general.Utils;

import java.util.List;

public class ScrollablePanel extends AbstractComponent implements IScrollingPanel {
    
    public static final int DEFAULT_GAP = 2;

    public static ScrollablePanel simpleLayout(int x, int y, int width, int amountVisibleComponents, ImmutableList<IScrollableComponent> components) {
        return simpleLayout(x, y, width, amountVisibleComponents, components, DEFAULT_GAP);
    }
    
    public static ScrollablePanel simpleLayout(int x, int y, int width, int amountVisibleComponents, ImmutableList<IScrollableComponent> components, int scrollBarDistance) {
        int componentHeight = components.get(0).getHeight() + DEFAULT_GAP;
        // Top gap only. Bottom gap is included in last component's height
        int panelHeight = (amountVisibleComponents * componentHeight) + DEFAULT_GAP;

        ComponentScrollBar scrollBar = new ComponentScrollBar(x , y, panelHeight);
        return new ScrollablePanel(x, y, width, panelHeight, components, scrollBar);
    }
    

    /**
     * Gap between every component drawn on the panel.
     */
    private int verticalGap = DEFAULT_GAP;

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
    public ScrollablePanel(int relativeX, int relativeY, int width, int height, ImmutableList<IScrollableComponent> content, ComponentScrollBar bar) {
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
        }
        scrollBar.initialize(gui, this);
    }

    private void updateHeight() {
        int componentHeight = commonHeight + verticalGap;
        // We only need to count to top gap, the bottom gap is included in the height of last component that gets displayed.
        int usableHeight = height - verticalGap;
        visibleComponents = Math.min(components.size(), usableHeight / componentHeight);

        contentHeight = components.size() * (commonHeight + verticalGap);
        contentKFactor = (float) getHeight() / getContentHeight();

        Utils.getLogger().info("ch: " + componentHeight + ", uh" + usableHeight);

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
        Utils.getLogger().info(commonHeight);
        for (IScrollableComponent component : components) {
            if (component.getHeight() != commonHeight) {
                throw new IllegalArgumentException("All components passed to ScrollablePanel must have equal amount of height!");
            }
        }

        this.commonHeight = commonHeight;
    }


    @Override
    public void draw(GuiDrawBackgroundEvent event) {
        int componentHeight = commonHeight + verticalGap;
        int endIndex = entryIndex + visibleComponents;

        int nextPenDownY = getActualY() + verticalGap;
        for (int i = entryIndex; i < endIndex; i++) {
            components.get(i).draw(event, nextPenDownY);
            nextPenDownY += componentHeight;
        }

        scrollBar.draw(event);
    }


    @Override
    public int getTotalSteps() {
        return components.size() - visibleComponents;
    }

    @Override
    public int getCurrentStep() {
        return entryIndex;
    }

    @Override
    public void setCurrentStep(int step) {
        entryIndex = Math.max(0, step);
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
