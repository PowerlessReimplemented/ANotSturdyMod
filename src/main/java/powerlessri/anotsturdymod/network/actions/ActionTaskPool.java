package powerlessri.anotsturdymod.network.actions;

/**
 * The pool to store tasks sent by client
 */
public class ActionTaskPool {

    private static final ActionTaskPool INSTANCE = new ActionTaskPool();

    static ActionTaskPool getInstance() {
        return INSTANCE;
    }


    /**
     * Schedule a nonblocking task
     * @param task
     * @param attachment
     */
    public static void scheduleTask(Runnable task, Attachment attachment) {
    }

}
