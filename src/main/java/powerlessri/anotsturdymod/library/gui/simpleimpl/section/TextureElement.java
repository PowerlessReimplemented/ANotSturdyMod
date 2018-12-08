package powerlessri.anotsturdymod.library.gui.simpleimpl.section;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.library.gui.TextureWrapper;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractComponent;
import powerlessri.anotsturdymod.varia.general.GuiUtils;

/**
 * A extended version of {@link TextureWrapper} that works as an {@link powerlessri.anotsturdymod.library.gui.api.IComponent IComponent}.
 */
public class TextureElement extends AbstractComponent {

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
    public void draw(GuiDrawBackgroundEvent event) {
        GuiUtils.useTextureGLStates();
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        gui.drawTexturedModalRect(getActualX(), getActualY(), textureX, textureY, width, height);
    }

}
