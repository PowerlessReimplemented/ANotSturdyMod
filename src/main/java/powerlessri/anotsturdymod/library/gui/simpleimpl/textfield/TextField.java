package powerlessri.anotsturdymod.library.gui.simpleimpl.textfield;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.MathHelper;
import powerlessri.anotsturdymod.library.gui.Color;
import powerlessri.anotsturdymod.library.gui.api.EEventType;
import powerlessri.anotsturdymod.library.gui.api.EMouseButton;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.api.IOnOffState;
import powerlessri.anotsturdymod.library.gui.integration.ContextGuiDrawing;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractComponent;
import powerlessri.anotsturdymod.library.gui.simpleimpl.DefaultRectangles;
import powerlessri.anotsturdymod.library.gui.simpleimpl.IRectangleRenderer;
import powerlessri.anotsturdymod.library.gui.simpleimpl.button.ButtonGradient;
import powerlessri.anotsturdymod.library.gui.simpleimpl.events.IInteractionHandler;
import powerlessri.anotsturdymod.library.keyboard.keys.LRModifierKey;
import powerlessri.anotsturdymod.varia.render.TESRStateManager;
import powerlessri.anotsturdymod.varia.render.VertexSequencer;

public class TextField extends AbstractComponent implements IInteractionHandler, IOnOffState {

    public static final int DEFAULT_TEXT_MARGIN = 8;
    public static final int MINIMUM_TEXT_FIELD_WIDTH = 2;
    public static final int MINIMUM_TEXT_FIELD_HEIGHT = Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 4 * 2;

    public static final Color NORMAL_TEXT_COLOR = Color.hex(14737632);
    public static final Color DISABLED_TEXT_COLOR = Color.hex(7368816);
    public static final Color BLUE = Color.rgb(0, 0, 255);
    public static final Color CURSOR_WHITE = Color.rgb(206, 206, 206);


    private IRectangleRenderer renderer = DefaultRectangles.VANILLA_BLACK_GRAY_BOX;
    private int width;
    private int height;

    private int textXOffset;
    private int textYOffset;

    private boolean disabled;

    private IValidator validator;
    private String text;
    private String visibleText;

    private int cursorPos;

    private int scrollOffset;

    private int selectionSIndex;
    private int selectionEIndex;

    public TextField(int x, int y, int width, int height) {
        super(x, y);
        this.width = Math.min(MINIMUM_TEXT_FIELD_WIDTH, width);
        this.height = Math.min(MINIMUM_TEXT_FIELD_HEIGHT, height);
        this.textXOffset = DEFAULT_TEXT_MARGIN;
        this.textYOffset = ButtonGradient.calculateTextYOffset(this.height);
        this.text = "";
        this.visibleText = "";
        this.scrollOffset = 0;
    }


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
    public void initialize(GuiScreen gui, IComponent parent) {
        super.initialize(gui, parent);
        this.setCursorEnd();
    }

    @Override
    public void draw(ContextGuiDrawing event) {
        this.drawBackground(event);
        this.drawText(event);
        this.drawSelectionBox(event);
        this.drawCursor(event);
    }


    private void drawBackground(ContextGuiDrawing event) {
        GlStateManager.pushMatrix();
        this.renderer.draw(getActualX(), getActualY(), width, height);
        GlStateManager.popMatrix();
    }

    private void drawText(ContextGuiDrawing event) {
        GlStateManager.pushMatrix();
        Minecraft.getMinecraft().fontRenderer.drawString(getVisibleText(), getTextX(), getTextY(), getTextColor().getHex());
        GlStateManager.popMatrix();
    }

    private void drawCursor(ContextGuiDrawing event) {
        // Toggle every second
        boolean visible = event.getTime() % 1000 % 2 == 0;

        if (visible) {
            GlStateManager.pushMatrix();
            BufferBuilder buffer = TESRStateManager.getColorVBuffer();

            String beforeCursor = this.getText().substring(0, this.getCursorPosition()) + 1;
            int beforeSize = Minecraft.getMinecraft().fontRenderer.getStringWidth(beforeCursor);
            int x1 = this.getTextX() + beforeSize;
            int y1 = this.getTextY() - 1;
            int x2 = x1 + 1;
            int y2 = this.getTextY() + 1 + Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
            VertexSequencer.plainBox(buffer, x1, y1, x2, y2, 0, CURSOR_WHITE);

            TESRStateManager.finish();
            GlStateManager.popMatrix();
        }
    }

    private void drawSelectionBox(ContextGuiDrawing event) {
        GlStateManager.pushMatrix();
        GlStateManager.color(1, 1, 1);
        GlStateManager.enableColorLogic();
        GlStateManager.colorLogicOp(GlStateManager.LogicOp.OR_REVERSE);

        BufferBuilder buffer = TESRStateManager.getColorVBuffer();
        {
            int selectionStart = this.getDisplaySelectionStart();
            int selectionEnd = this.getDisplaySelectionEnd();
            String selection = this.getText().substring(selectionStart, selectionEnd + 1);
            int selectionWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(selection);

            int x1 = this.getTextX();
            int y1 = this.getTextY();
            int x2 = x1 + selectionWidth;
            int y2 = y1 + Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;

            VertexSequencer.plainBox(buffer, x1, y1, x2, y2, 0, BLUE);
        }
        TESRStateManager.finish();

        GlStateManager.enableTexture2D();
        GlStateManager.disableColorLogic();
        GlStateManager.popMatrix();
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

    public int getScrollFront() {
        return this.scrollOffset;
    }

    public int getScrollBack() {
        return Math.max(this.getScrollFront() + this.getMaxDisplayLength(), getTextLastIdx());
    }

    public int getDisplaySelectionStart() {
        return distanceFromScrollFront(getSelectionStart());
    }

    public int getDisplaySelectionEnd() {
        return distanceFromScrollFront(getSelectionEnd());
    }

    public int distanceFromScrollFront(int target) {
        return MathHelper.clamp(target - getScrollFront(), 0, getVisibleTextLastIdx());
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

    public int getMaxDisplayLength() {
        return this.visibleText.length();
    }

    public int getTextLastIdx() {
        return this.getText().length() - 1;
    }

    public String getVisibleText() {
        return this.visibleText;
    }

    public int getVisibleTextLastIdx() {
        return this.getVisibleText().length() - 1;
    }

    public void enterTextAtCursor(String text) {
        String beforeCursor = this.text.substring(0, this.getCursorPosition() + 1);
        String afterCursor = this.text.substring(this.getCursorPosition() + 1);
        this.setText(beforeCursor + text + afterCursor);
    }

    public void setText(String text) {
        if (this.validator.isTextValid(text)) {
            this.text = text;
            this.notifyServer();
        }
    }

    public int getSelectionStart() {
        return this.selectionSIndex;
    }

    public int getSelectionEnd() {
        return this.selectionEIndex;
    }

    public int getSelectionSize() {
        return this.getSelectionEnd() - this.getSelectionStart() + 1;
    }

    public void setSelectionArea(int startIndex, int endIndex) {
        this.selectionSIndex = MathHelper.clamp(startIndex, 0, getTextLastIdx());
        this.selectionEIndex = MathHelper.clamp(endIndex, 0, getTextLastIdx());
    }

    public int getCursorPosition() {
        return this.cursorPos;
    }

    public int getMinCursorPos() {
        return 0;
    }

    public int getMaxCursorPos() {
        return this.text.length();
    }

    public boolean isCursorPosValid(int cursorPos) {
        return cursorPos >= getMinCursorPos() && cursorPos <= getMaxCursorPos();
    }

    public void setCursorPosition(int pos) {
        if (!isCursorPosValid(pos) || cursorPos == pos) {
            return;
        }

        if (LRModifierKey.SHIFT.isKeyDown()) {
            // Negative for right, positive for left
            int change = pos - cursorPos;
            int selectionS = this.getSelectionStart() + (change < 0 ? change : 0);
            int selectionE = this.getSelectionEnd() + (change > 0 ? change : 0);
            this.setSelectionArea(selectionS, selectionE);
        } else {
            this.setSelectionArea(pos, pos);
        }
        this.cursorPos = pos;
        this.visibleText = this.getText().substring(getScrollFront(), getScrollBack() + 1);
    }

    /**
     * @return this
     */
    public TextField setCursorStart() {
        this.setCursorPosition(getMinCursorPos());
        return this;
    }

    /**
     * @return this
     */
    public TextField setCursorEnd() {
        this.setCursorPosition(getMaxCursorPos());
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
