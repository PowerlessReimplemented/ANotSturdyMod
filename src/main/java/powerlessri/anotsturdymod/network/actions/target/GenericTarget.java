package powerlessri.anotsturdymod.network.actions.target;

import net.minecraft.network.PacketBuffer;
import powerlessri.anotsturdymod.library.gui.api.IActionHandler;
import powerlessri.anotsturdymod.network.actions.GeneralExecutorPool;
import powerlessri.anotsturdymod.network.actions.Target;
import powerlessri.anotsturdymod.network.actions.TaskExecutor;

public class GenericTarget implements Target {

    private int id;

    public GenericTarget() {
    }

    public GenericTarget(String name) {
        this(GeneralExecutorPool.findID(name));
    }

    public GenericTarget(int id) {
        this.id = id;
    }


    @Override
    public void write(PacketBuffer buf) {
        buf.writeInt(id);
    }

    @Override
    public void read(PacketBuffer buf) {
        this.id = buf.readInt();
    }

    @Override
    public TaskExecutor getExecutor() {
        return null;
    }


    public IActionHandler getActionHandler() {
        return GeneralExecutorPool.findAction(id);
    }

    public String getActionName() {
        return this.getActionHandler().getName();
    }

}
