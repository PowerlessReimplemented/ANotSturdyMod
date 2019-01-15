package powerlessri.anotsturdymod.network.actions;

/**
 * The task, or callback to be schedule to server thread.
 * <p>
 * This class is used for a specialized replacement for {@link Runnable}, therefore it can provide and encapsulate more feature into the
 * actions system.
 * </p>
 */
public abstract class QueuedActionTask implements Runnable {

    @Override
    public abstract void run();

}
