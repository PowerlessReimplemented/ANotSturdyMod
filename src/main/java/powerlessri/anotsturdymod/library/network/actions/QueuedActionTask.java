package powerlessri.anotsturdymod.library.network.actions;

/**
 * The task, or callback to be schedule to server thread.
 */
public class QueuedActionTask {

    private IAttachment attachment;

    private ITarget target;
    private ITaskExecutor executor;

    /**
     * <p>
     * This initializer can be called in the Netty IO thread.
     * </p>
     */
    public QueuedActionTask(IAttachment attachment, ITarget target) {
        this.attachment = attachment;
        this.target = target;
        this.executor = target.getExecutor();
    }

    public IAttachment getAttachment() {
        return attachment;
    }

    public ITarget getTarget() {
        return target;
    }

    public ITaskExecutor getExecutor() {
        return executor;
    }

}
