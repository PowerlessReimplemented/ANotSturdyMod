package powerlessri.anotsturdymod.library.gui.simpleimpl.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.library.gui.TextureWrapper;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.varia.general.GuiUtils;

/**
 * A extended version of {@link TextureWrapper} that works as an {@link powerlessri.anotsturdymod.library.gui.api.IComponent IComponent}.
 */
public class LabelImage extends Label {

    private ResourceLocation texture;
    private int textureY;
    private int textureX;
    private int width;
    private int height;


    public LabelImage(int relativeX, int relativeY, TextureWrapper wrapper) {
        this(relativeX, relativeY, wrapper.texture, wrapper.x1, wrapper.y1, wrapper.width, wrapper.height);
    }

    public LabelImage(int relativeX, int relativeY, ResourceLocation texture, int textureX, int textureY, int width, int height) {
        super(relativeX, relativeY, width, height);
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
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        getGui().drawTexturedModalRect(getActualX(), getActualY(), textureX, textureY, width, height);
    }

}
