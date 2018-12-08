package powerlessri.anotsturdymod.library.gui.simpleimpl.section;

import net.minecraft.util.EnumActionResult;
import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.library.gui.api.EEventType;
import powerlessri.anotsturdymod.library.gui.api.EMouseButton;
import powerlessri.anotsturdymod.library.gui.api.IInteractionHandler;

/**
 * <p>
 * This element can also suits as a background for a {@link powerlessri.anotsturdymod.library.gui.api.IContainer IContainer} element,
 * to receive mouse-related events.
 * </p>
 */
public class TextureElementInteractable extends TextureElement implements IInteractionHandler {

    public TextureElementInteractable(int relativeX, int relativeY, ResourceLocation texture, int textureX, int textureY, int width, int height) {
        super(relativeX, relativeY, texture, textureX, textureY, width, height);
    }


    @Override
    public EnumActionResult onClicked(int mouseX, int mouseY, EMouseButton button, EEventType type) {
        return EnumActionResult.PASS;
    }

    @Override
    public void onClickedDragging(int mouseX, int mouseY, EMouseButton button, long timePressed) {
    }

    @Override
    public void onHoveredDragging(int mouseX, int mouseY, EMouseButton button) {
    }

    @Override
    public EnumActionResult onReleased(int mouseX, int mouseY, EMouseButton button, EEventType type) {
        return EnumActionResult.FAIL;
    }

}
