package powerlessri.anotsturdymod.library.gui.simpleimpl.scrolling;

import com.google.common.collect.ImmutableList;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractComponent;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;

public class ScrollingPanel extends AbstractComponent implements IScrollingPanel {

    /**
     * Gap between every component drawn on the panel.
     */
    private int verticalGap;
    
    private int width;
    private int height;

    private int commonHeight;
    private int amountVisibleComponents;

    private IScrollBar scroller;
    
    private ImmutableList<IScrollingComponent> content;
    private int entryIndex;

    public ScrollingPanel(int relativeX, int relativeY, int width, int height, ImmutableList<IScrollingComponent> content) {
        super(relativeX, relativeY);
        
        this.width = width;
        this.setHeight(height);
        
        this.content = content;
        this.checkComponentHeight();

        this.scroller = new ComponentScroller(relativeX, relativeY);
    }
    
    private void checkComponentHeight() {
        int commonHeight = content.get(0).getHeight();
        for (IScrollingComponent component : content) {
            if (component.getHeight() != height) {
                throw new IllegalArgumentException("All content passed to ScrollingPanel must have equal height!");
            }
        }
        this.commonHeight = commonHeight;
    }

    
    @Override
    public void draw(GuiDrawBackgroundEvent event) {
        int displayHeight = commonHeight + verticalGap;
        int nextPenDownY = getActualY() + verticalGap;
        
        int endIndex = entryIndex + amountVisibleComponents;
        for (int i = entryIndex; i < endIndex; i++) {
            nextPenDownY += displayHeight;
            content.get(i).draw(event, nextPenDownY);
        }
    }


    public void setHeight(int height) {
        this.height = height;
        
        int displayHeight = commonHeight + verticalGap;
        int usableHeight = height - (verticalGap * 2);
        amountVisibleComponents = usableHeight / displayHeight;
    }

    @Override
    public int getTotalSteps() {
        return amountVisibleComponents;
    }

    @Override
    public int getCurrentStep() {
        return entryIndex;
    }

    @Override
    public void setContentStep(int step) {
        
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
    
}
