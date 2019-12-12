package powerlessri.anotsturdymod.library.network.actions.target;

import net.minecraft.network.PacketBuffer;
import powerlessri.anotsturdymod.library.network.actions.ITarget;
import powerlessri.anotsturdymod.library.network.actions.ITaskExecutor;

public class DummyTarget implements ITarget {

    @Override
    public void write(PacketBuffer buf) {
    }

    @Override
    public void read(PacketBuffer buf) {
    }

    @Override
    public ITaskExecutor getExecutor() {
        return (target, attachment) -> false;
    }

}
