package powerlessri.anotsturdymod.network.actions;

import io.netty.buffer.ByteBuf;
import powerlessri.anotsturdymod.library.gui.api.IActionHandler;

class GenericTarget extends Target {

    private int id;

    public GenericTarget(String name) {
        this(GuiActionManager.findID(name));
    }

    public GenericTarget(int id) {
        this.id = id;
    }


    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(id);
    }

    @Override
    public void read(ByteBuf buf) {
        this.id = buf.readInt();
    }


    public IActionHandler getActionHandler() {
        return GuiActionManager.findAction(id);
    }

    public String getActionName() {
        return this.getActionHandler().getName();
    }

}
