package powerlessri.anotsturdymod.library.gui.immutableimpl;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.library.gui.TextureWrapper;
import powerlessri.anotsturdymod.library.gui.api.EEventType;
import powerlessri.anotsturdymod.library.gui.api.EMouseButton;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.api.IInteractionHandler;
import powerlessri.anotsturdymod.varia.general.GuiUtils;

/**
 * A extended version of {@link TextureWrapper} that works as an {@link powerlessri.anotsturdymod.library.gui.api.IComponent IComponent}.
 * This element can also suits as a background for a {@link powerlessri.anotsturdymod.library.gui.api.IContainer IContainer} element,
 * to receive mouse-related events.
 */
public class TextureElement extends AbstractComponent implements IInteractionHandler {

    private IInteractionHandler parent;
    private ResourceLocation texture;
    private int textureY;
    private int textureX;
    private int width;
    private int height;
    

    public TextureElement(int relativeX, int relativeY, TextureWrapper wrapper) {
        this(relativeX, relativeY, wrapper.texture, wrapper.x1, wrapper.y1, wrapper.width, wrapper.height);
    }
    
    public TextureElement(int relativeX, int relativeY, ResourceLocation texture, int textureX, int textureY, int width, int height) {
        super(relativeX, relativeY);

        this.texture = texture;
        this.textureX = textureX;
        this.textureY = textureY;
        this.width = width;
        this.height = height;
    }


    @Override
    public void initialize(GuiScreen gui, IComponent parent) {
        super.initialize(gui, parent);
        if (parent instanceof IInteractionHandler) {
            this.parent = (IInteractionHandler) parent;
        }
    }

    @Override
    public void onClicked(int mouseX, int mouseY, EMouseButton button, EEventType type) {
        parent.onClicked(mouseX, mouseY, button, type);
    }

    @Override
    public void onReleased(int mouseX, int mouseY, EMouseButton button, EEventType type) {
        parent.onReleased(mouseX, mouseY, button, type);
    }

    @Override
    public boolean isLeafComponent() {
        return true;
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
    public void draw() {
        GuiUtils.useTextureGLStates();
        gui.mc.renderEngine.bindTexture(texture);
        gui.drawTexturedModalRect(getActualX(), getActualY(), textureX, textureY, width, height);
    }
}
