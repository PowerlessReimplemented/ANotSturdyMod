package powerlessri.anotsturdymod.library.gui.simpleimpl.scrolling;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractComponent;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;

import java.util.List;

public class ScrollingPanel extends AbstractComponent implements IScrollingPanel {

    /**
     * Gap between every component drawn on the panel.
     */
    private int verticalGap = 2;
    
    private int width;
    private int height;

    private int commonHeight;
    private int amountVisibleComponents;

    private IScrollBar scrollBar;
    private ImmutableList<IScrollableComponent> components;
    private int entryIndex;

    public ScrollingPanel(int relativeX, int relativeY, int width, int height, ImmutableList<IScrollableComponent> content, ChunkyScrollBar bar) {
        super(relativeX, relativeY);
        
        this.components = content;
        this.checkComponentHeight();

        this.width = width;
        this.setHeight(height);

        // TODO scroll bar details
        this.scrollBar = bar;
    }
    
    private void checkComponentHeight() {
        int commonHeight = components.get(0).getHeight();
        for (IScrollableComponent component : components) {
            if (component.getHeight() != commonHeight) {
                throw new IllegalArgumentException("All components passed to ScrollingPanel must have equal amount of height!");
            }
        }
        
        this.commonHeight = commonHeight;
    }

    
    @Override
    public void draw(GuiDrawBackgroundEvent event) {
        int displayHeight = commonHeight + verticalGap;
        int endIndex = entryIndex + amountVisibleComponents;

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

        int hiddenComponents = components.size() - visibleComponents;
        if (hiddenComponents > height) {
            throw new IllegalArgumentException("Unable to fit all components with the given height " + height + "!");
        }
        
        this.height = height;
        this.amountVisibleComponents = visibleComponents;
    }

    @Override
    public int getTotalSteps() {
        return components.size() - amountVisibleComponents + 1;
    }

    @Override
    public int getCurrentStep() {
        return entryIndex;
    }

    @Override
    public void setContentStep(int step) {
        entryIndex = step;
    }


    @Override
    public void initialize(GuiScreen gui, IComponent parent) {
        super.initialize(gui, parent);
        for (IScrollableComponent component : components) {
            component.initialize(gui, this);
        }
        scrollBar.initialize(gui, this);

        if (getHeight() != scrollBar.getHeight()) {
            throw new IllegalArgumentException("Scroll bar must have the same height as the panel it belongs to.");
        }
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
    private ImmutableList<IComponent> content;

    @Override
    public List<IComponent> getComponents() {
        if (content == null) {
            ImmutableList.Builder<IComponent> builder = ImmutableList.builder();
            builder.addAll(components);
            builder.add(scrollBar);
            content = builder.build();
        }
        return content;
    }

    @Override
    public boolean acceptsZIndex() {
        return false;
    }
    
}
