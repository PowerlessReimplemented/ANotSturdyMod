package powerlessri.anotsturdymod.network.actions;

/**
 * The task, or callback to be schedule to server thread.
 */
public class QueuedActionTask {

    private Attachment attachment;

    private Target target;
    private TaskExecutor executor;

    /**
     * <p>
     * This initializer can be called in the Netty IO thread.
     * </p>
     */
    public QueuedActionTask(Attachment attachment, Target target) {
        this.attachment = attachment;
        this.target = target;
        this.executor = target.getExecutor();
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public Target getTarget() {
        return target;
    }

    public TaskExecutor getExecutor() {
        return executor;
    }

}
