package powerlessri.anotsturdymod.library.network.actions;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * The pool to store tasks sent by client
 */
@Mod.EventBusSubscriber
public class ActionTaskPool {

    /**
     * The marker
     */
    private static final QueuedActionTask MARKER = new QueuedActionTask(null, null);

    private static final ActionTaskPool INSTANCE = new ActionTaskPool();

    public static ActionTaskPool getInstance() {
        return INSTANCE;
    }


    /**
     * Schedule a nonblocking task.
     */
    public static void scheduleTask(ITarget target, IAttachment attachment) {
        INSTANCE.tasks.add(new QueuedActionTask(attachment, target));
    }

    @SubscribeEvent
    public static void tick(TickEvent.ServerTickEvent event) {
        INSTANCE.processTasks(32);
    }


    private Queue<QueuedActionTask> tasks = new ArrayDeque<>();

    public void processTasks(int limit) {
        if (tasks.isEmpty()) {
            return;
        }

        // Label off the current section, don't execute rescheduled tasks due to failure
        tasks.add(MARKER);
        while (tasks.peek() != MARKER) {
            QueuedActionTask task = tasks.remove();
            ITarget target = task.getTarget();
            ITaskExecutor executor = task.getExecutor();
            IAttachment attachment = task.getAttachment();

            boolean success = executor.execute(target, attachment);
            if (!success) {
                tasks.add(task);
            }
        }
        // Remove MARKER
        tasks.poll();
    }

    public void discardTopTask() {
        tasks.poll();
    }

    public void discardAllTasks() {
        tasks.clear();
    }

}
