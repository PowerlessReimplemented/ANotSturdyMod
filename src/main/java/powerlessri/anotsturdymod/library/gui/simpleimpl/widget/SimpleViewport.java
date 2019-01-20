package powerlessri.anotsturdymod.library.gui.simpleimpl.widget;

import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.api.IContainer;
import powerlessri.anotsturdymod.library.gui.api.IOnOffState;
import powerlessri.anotsturdymod.library.gui.integration.ContextGuiDrawing;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractComponent;

public class SimpleViewport extends AbstractComponent implements IOnOffState {

    public static SimpleViewport referenceFrom(int x, int y, IContainer<IComponent> reference) {
        SimpleViewport viewport = new SimpleViewport(x, y);
        viewport.bind(reference);
        return viewport;
    }


    private boolean disabled;

    private IContainer<IComponent> reference;

    public SimpleViewport(int x, int y) {
        super(x, y);
    }


    public void bind(IContainer<IComponent> reference) {
        if (reference != null) {
            if (this.getWidth() != reference.getWidth() || this.getHeight() != reference.getHeight()) {
                throw new IllegalArgumentException("Setting a new reference to viewport with a difference dimension than the old one");
            }
        }
        this.reference = reference;
    }


    @Override
    public boolean isLeafComponent() {
        return true;
    }

    @Override
    public int getWidth() {
        return this.reference.getWidth();
    }

    @Override
    public int getHeight() {
        return this.reference.getHeight();
    }

    @Override
    public void draw(ContextGuiDrawing event) {
        this.reference.draw(event);
    }


    @Override
    public boolean isVisible() {
        return !this.isDisabled() && this.reference.isVisible();
    }

    @Override
    public boolean isDisabled() {
        return this.disabled;
    }

    @Override
    public void enable() {
        this.disabled = false;
    }

    @Override
    public void disable() {
        this.disabled = true;
    }

}
