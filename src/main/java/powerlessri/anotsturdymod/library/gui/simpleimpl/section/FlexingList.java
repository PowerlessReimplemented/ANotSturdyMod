package powerlessri.anotsturdymod.library.gui.simpleimpl.section;

import com.google.common.collect.ImmutableList;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.api.IContainer;
import powerlessri.anotsturdymod.library.gui.api.IScrollableComponent;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractComponent;

import java.util.Comparator;
import java.util.List;

public class FlexingList extends AbstractComponent implements IContainer<IScrollableComponent> {

    private ImmutableList<IScrollableComponent> components;

    private int commonMarginBottom;

    private int width;
    private int height;

    public FlexingList(int x, int y, ImmutableList<IScrollableComponent> components) {
        this(x, y, components, 0);
    }

    public FlexingList(int x, int y, ImmutableList<IScrollableComponent> components, int commonMarginBottom) {
        super(x, y);
        this.components = components;
        this.commonMarginBottom = commonMarginBottom;
        this.collectData();
    }

    private void collectData() {
        int nextPenDownY = getActualY();
        for (IScrollableComponent component : components) {
            component.setExpectedY(nextPenDownY);
            nextPenDownY += component.getHeight() + commonMarginBottom;
        }

        this.width = components.stream()
                .max(Comparator.comparingInt(IComponent::getWidth))
                .orElse(components.get(0))
                .getWidth();
        this.height = nextPenDownY - getActualY() - commonMarginBottom;
    }


    @Override
    public void draw(GuiDrawBackgroundEvent event) {
        for (IScrollableComponent component : components) {
            component.tryDraw(event);
        }
    }


    @Override
    public List<IScrollableComponent> getComponents() {
        return components;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean isLeafComponent() {
        return false;
    }

    @Override
    public boolean acceptsZIndex() {
        return false;
    }

}
