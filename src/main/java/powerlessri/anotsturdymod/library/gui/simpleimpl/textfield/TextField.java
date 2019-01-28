package powerlessri.anotsturdymod.library.gui.simpleimpl.textfield;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.MathHelper;
import powerlessri.anotsturdymod.library.gui.Color;
import powerlessri.anotsturdymod.library.gui.api.EEventType;
import powerlessri.anotsturdymod.library.gui.api.EMouseButton;
import powerlessri.anotsturdymod.library.gui.api.IOnOffState;
import powerlessri.anotsturdymod.library.gui.integration.ContextGuiDrawing;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractComponent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.button.ButtonGradient;
import powerlessri.anotsturdymod.library.gui.simpleimpl.events.IInteractionHandler;

public class TextField extends AbstractComponent implements IInteractionHandler, IOnOffState {

    public static final int DEFAULT_TEXT_MARGIN = 8;
    public static final int MINIMUM_TEXT_FIELD_WIDTH = 2;
    private static final int MINIMUM_TEXT_FIELD_HEIGHT = Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 4 * 2;

    public static Color NORMAL_TEXT_COLOR = Color.hex(14737632);
    public static Color DISABLED_TEXT_COLOR = Color.hex(7368816);


    private int width;
    private int height;

    private int textXOffset;
    private int textYOffset;

    private boolean disabled;

    private IValidator validator;
    private String text;
    /**
     *
     */
    private int cursorPos;

    private int selectionSIndex;
    private int selectionEIndex;

    public TextField(int x, int y, int width, int height) {
        super(x, y);
        this.width = Math.min(MINIMUM_TEXT_FIELD_WIDTH, width);
        this.height = Math.min(MINIMUM_TEXT_FIELD_HEIGHT, height);
        this.textXOffset = DEFAULT_TEXT_MARGIN;
        this.textYOffset = ButtonGradient.calculateTextYOffset(this.height);
    }

    // TODO init event subscribers

    @Override
    public EnumActionResult onClicked(int mouseX, int mouseY, EMouseButton button, EEventType type) {
        // TODO cursor pos analysing
        return EnumActionResult.SUCCESS;
    }

    @Override
    public void onClickedDragging(int mouseX, int mouseY, EMouseButton button, long timePressed) {
    }

    @Override
    public void onHovering(int mouseX, int mouseY) {
    }

    @Override
    public EnumActionResult onReleased(int mouseX, int mouseY, EMouseButton button, EEventType type) {
        return EnumActionResult.PASS;
    }


    @Override
    public void draw(ContextGuiDrawing event) {
        this.drawBackground(event);
        this.drawText(event);
        this.drawCursor(event);
        this.drawSelectionBox(event);
    }

    // TODO all drawing mthods

    private void drawBackground(ContextGuiDrawing event) {
    }

    private void drawText(ContextGuiDrawing event) {
    }

    private void drawCursor(ContextGuiDrawing event) {
    }

    private void drawSelectionBox(ContextGuiDrawing event) {
    }


    // TODO network connection
    public void notifyServer() {

    }


    @Override
    public boolean doesReceiveEvents() {
        return true;
    }

    @Override
    public boolean isLeafComponent() {
        return true;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    public int getTextX() {
        return this.getActualX() + this.textXOffset;
    }

    public int getTextY() {
        return this.getActualY() + this.textYOffset;
    }

    public Color getTextColor() {
        if (this.isDisabled()) {
            return DISABLED_TEXT_COLOR;
        } else {
            return NORMAL_TEXT_COLOR;
        }
    }

    public String getText() {
        return this.text;
    }

    public int getMaxTextIndex() {
        return this.getText().length() - 1;
    }

    public String getTextForDisplay() {
        return this.getText().substring(cursorPos);
    }

    public void setText(String text) {
        if (this.validator.isTextValid(text)) {
            this.text = text;
            this.notifyServer();
        }
    }

    public void setSelectionArea(int startIndex, int endIndex) {
        this.selectionSIndex = MathHelper.clamp(startIndex, 0, getMaxTextIndex());
        this.selectionEIndex = MathHelper.clamp(endIndex, 0, getMaxTextIndex());
    }

    public int getCursorPosition() {
        return this.cursorPos;
    }

    public void setCursorPosition(int cursorPos) {
        // TODO key combo check
        this.cursorPos = cursorPos;
        this.setSelectionArea(cursorPos, cursorPos);
    }

    /**
     * @return this
     */
    public TextField setCursorStart() {
        this.setCursorPosition(0);
        return this;
    }

    /**
     * @return this
     */
    public TextField setCursorEnd() {
        this.setCursorPosition(text.length());
        return this;
    }

    public IValidator getValidator() {
        return this.validator;
    }

    /**
     * @return this
     */
    public TextField setValidator(IValidator validator) {
        this.validator = validator;
        return this;
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
