package powerlessri.anotsturdymod.library.gui.simpleimpl.scrolling;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumActionResult;
import org.lwjgl.opengl.GL11;
import powerlessri.anotsturdymod.library.gui.api.EEventType;
import powerlessri.anotsturdymod.library.gui.api.EMouseButton;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractComponent;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;
import powerlessri.anotsturdymod.varia.general.GuiUtils;

import javax.annotation.Nullable;

public class ComponentScrollBar extends AbstractComponent implements IScrollBar {
    
    public static final int BORDER_DARK = 56;
    public static final int BORDER_LIGHT = 255;
    public static final int BACKGROUND = 138;
    
    private ScrollingPanel parent;
    
    public ComponentScrollBar(int relativeX, int relativeY) {
        super(relativeX, relativeY);
    }

    
    @Override
    public boolean isLeafComponent() {
        return true;
    }

    @Override
    public int getWidth() {
        return 7;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void draw(GuiDrawBackgroundEvent event) {
        GuiUtils.usePlainColorGLStates();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        addBorderLine(buffer);

        tessellator.draw();
        GlStateManager.enableTexture2D();
    }

    private void addBorderLine(BufferBuilder buffer) {
        int x = getActualX();
        int y = getActualY();
        int x2 = getActualXBR();
        int y2 = getActualYBR();

        int l1X2 = getActualX() + 1;
        buffer.pos(l1X2, y, z).color(109, 109, 109, 255).endVertex();
        buffer.pos(x, y, z).color(109, 109, 109, 255).endVertex();
        buffer.pos(x, y2, z).color(109, 109, 109, 255).endVertex();
        buffer.pos(l1X2, y2, z).color(109, 109, 109, 255).endVertex();

        int l2Y2 = getActualY() + 1;
        buffer.pos(x2, y, z).color(109, 109, 109, 255).endVertex();
        buffer.pos(x, y, z).color(109, 109, 109, 255).endVertex();
        buffer.pos(x, l2Y2, z).color(109, 109, 109, 255).endVertex();
        buffer.pos(x2, l2Y2, z).color(109, 109, 109, 255).endVertex();
    }

    private void addBody(BufferBuilder buffer) {
        
    }

    private void addCenteredIcon(BufferBuilder buffer) {
        
    }

    
    @Override
    public void initialize(GuiScreen gui, IScrollingPanel parent) {
        super.initialize(gui, parent);
    }

    
    @Nullable
    @Override
    public IComponent getParentComponent() {
        return parent;
    }

    @Override
    public EnumActionResult onClicked(int mouseX, int mouseY, EMouseButton button, EEventType type) {
        return null;
    }

    @Override
    public void onClickedDragging(int mouseX, int mouseY, EMouseButton button, long timePressed) {

    }

    @Override
    public void onHoveredDragging(int mouseX, int mouseY, EMouseButton button) {

    }

    @Override
    public EnumActionResult onReleased(int mouseX, int mouseY, EMouseButton button, EEventType type) {
        return null;
    }
    
}
