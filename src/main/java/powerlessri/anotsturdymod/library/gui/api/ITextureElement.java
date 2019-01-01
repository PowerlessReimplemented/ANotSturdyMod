package powerlessri.anotsturdymod.library.gui.api;

import net.minecraft.client.renderer.BufferBuilder;
import powerlessri.anotsturdymod.varia.render.TESRStateManager;

/**
 * <p>
 * Defines an object, possibly an {@link IComponent}, that supports two-stage drawing.
 * </p>
 *
 * <p>
 * The two defined stages are
 * <ol>
 * <li>Set GPU pass - {@link #setPass(BufferBuilder, int, int)}</li>
 * <li>Start drawing - {@link #draw(int, int)}</li>
 * </ol>
 * </p>
 */
public interface ITextureElement {

    default void draw(int x, int y) {
        BufferBuilder buffer = TESRStateManager.getTextureVBuffer();
        this.setPass(buffer, x, y);
        TESRStateManager.finish();
    }

    void setPass(BufferBuilder buffer, int x, int y);


    int getXTex();

    int getYTex();

    int getWidth();

    int getHeight();

}
