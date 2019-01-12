package powerlessri.anotsturdymod.library.gui.integration;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import org.lwjgl.input.Mouse;
import powerlessri.anotsturdymod.library.gui.api.EMouseButton;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.api.IContainer;
import powerlessri.anotsturdymod.library.gui.simpleimpl.ComponentRoot;
import powerlessri.anotsturdymod.library.gui.simpleimpl.MouseEventManager;
import powerlessri.anotsturdymod.varia.general.GuiUtils;

import java.io.IOException;

// This is purposely spelled wrong, people should get what it means.
// In case somebody doesn't get it:

/**
 * An bridge between the component-tree based GUI system and vanilla's GUI system.
 */
public abstract class ComponentizedGui extends GuiContainer {

    /**
     * Number of updates since this GUI was created. Increases every time {@link #updateScreen()} is called.
     */
    public long updates;

    /**
     * System time when this GUI was initialized.
     */
    public long timeCreated;

    protected ComponentRoot root;
    protected ImmutableList<IContainer<IComponent>> windows;

    public ComponentizedGui(Container container) {
        this(container, null);
    }

    public ComponentizedGui(Container container, ImmutableList<IContainer<IComponent>> windows) {
        super(container);

        this.windows = windows;
    }

    @Override
    public void initGui() {
        super.initGui();

        super.xSize = getMainWindow().getWidth();
        super.ySize = getMainWindow().getHeight();
        this.timeCreated = System.currentTimeMillis();
        this.root = new ComponentRoot(this, windows);
        this.root.initialize(this, null);
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        super.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GuiUtils.useTextureGLStates();
        drawComponentTree(new ContextGuiDrawing(System.currentTimeMillis() - timeCreated, mouseX, mouseY, partialTicks));
        // In case somebody forget to call GlStateManager.enableTexture2D()
        // If they did enable  do that and this line does not exist, features from vanilla (e.g. text painting) will break
        GuiUtils.useTextureGLStates();
    }

    private void drawComponentTree(ContextGuiDrawing event) {
        root.draw(event);
    }


    /**
     * Delegates mouse and keyboard input.
     * <p>
     * Called in {@link Minecraft#runTick()}, before {@link #updateScreen()} is called. This method processes mouse and keyboard events.
     * They are separately processed in {@link #handleMouseInput()} and {@link #handleKeyboardInput()}.
     * </p>
     *
     * @see Mouse
     * @see org.lwjgl.input.Keyboard
     */
    @Override
    public void handleInput() throws IOException {
        super.handleInput();
    }

    /**
     * Handles mouse input.
     * <p>
     * Calls:
     * <ul>
     * <li>{@link #mouseClicked(int, int, int)} when LWJGL says user pressed his mouse</li>
     * <li>{@link #mouseReleased(int, int, int)} when LWJGL says user did not press his mouse and one of the previous call triggered {@link
     * #mouseClicked(int, int, int)}</li>
     * <li>{@link #mouseClickMove(int, int, int, long)} every tick when LWJGL says user is pressing his mouse</li>
     * </ul>
     * </p>
     *
     * @see #handleInput()
     */
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }

    /**
     * Handles keyboard input.
     * <p>Calls {@link #keyTyped(char, int)} will be called for any initial key presses.</p>
     *
     * @see #handleInput()
     */
    @Override
    public void handleKeyboardInput() throws IOException {
        super.handleKeyboardInput();
    }

    /**
     * Called from the main game loop {@link Minecraft#runTick()} to update the screen.
     */
    @Override
    public void updateScreen() {
        super.updateScreen();

        // Copied from GuiScreen#handleMouseInput()
        int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        EMouseButton button = getMouseButton(Mouse.getEventButton());

        // Handles hovering drags
        root.getMouseEventManager().emitHoveringDragging(x, y, button);

        root.update(new ContextGuiUpdate(updates, x, y));
        updates++;
    }


    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        root.getMouseEventManager().emitMouseClicked(mouseX, mouseY, getMouseButton(mouseButton));
        // TODO use other hovering dragging trigger mechanic
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int mouseButton, long timePressed) {
        super.mouseClickMove(mouseX, mouseY, mouseButton, timePressed);

        MouseEventManager eventManager = root.getMouseEventManager();
        EMouseButton button = getMouseButton(mouseButton);

        eventManager.emitClickedDragging(mouseX, mouseY, button, timePressed);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);

        root.getMouseEventManager().emitMouseReleased(mouseX, mouseY, getMouseButton(mouseButton));
    }


    private EMouseButton getMouseButton(int mouseButton) {
        switch (mouseButton) {
            case 0:
                return EMouseButton.PRIMARY;
            case 1:
                return EMouseButton.MIDDLE;
            case 2:
                return EMouseButton.SECONDARY;

            default:
                return EMouseButton.NONE;
        }
    }


    public abstract IContainer<?> getMainWindow();

}
