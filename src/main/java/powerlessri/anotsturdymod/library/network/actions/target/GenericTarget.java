package powerlessri.anotsturdymod.library.network.actions.target;

import net.minecraft.network.PacketBuffer;
import powerlessri.anotsturdymod.library.gui.api.IActionHandler;
import powerlessri.anotsturdymod.library.network.actions.GeneralExecutorPool;
import powerlessri.anotsturdymod.library.network.actions.ITarget;
import powerlessri.anotsturdymod.library.network.actions.ITaskExecutor;

public class GenericTarget implements ITarget {

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
    public ITaskExecutor getExecutor() {
        return null;
    }


    public IActionHandler getActionHandler() {
        return GeneralExecutorPool.findAction(id);
    }

    public String getActionName() {
        return this.getActionHandler().getName();
    }

}
