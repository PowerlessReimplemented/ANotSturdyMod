package powerlessri.anotsturdymod.network;

import io.netty.buffer.ByteBuf;
import powerlessri.anotsturdymod.blocks.gui.api.IActionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiActionManager {

    public static class DefaultActionHandler implements IActionHandler {
        
        @Override
        public String getName() {
            return "DefaultHandler";
        }

        @Override
        public int getMessageLength() {
            return 0;
        }

        @Override
        public void writeToBuffer(ByteBuf buf) {
        }

        @Override
        public void handle(ByteBuf buffer) {
        }
        
    }
    
    public static final IActionHandler DEFAULT_HANDLER = new DefaultActionHandler();

    private static int lastActionId = -1;
    private static Map<String, Integer> actionIds = new HashMap<>();
    private static List<IActionHandler> actions = new ArrayList<>();


    public static void registerAction(IActionHandler action) {
        actionIds.put(action.getName(), ++lastActionId);
        actions.add(action);
    }
    
    public static int findID(String name) {
        if (!actionIds.containsKey(name)) {
            return -1;
        }
        return actionIds.get(name);
    }

    public static IActionHandler findAction(String name) {
        return findAction(findID(name));
    }

    public static IActionHandler findAction(int id) {
        if (id >= 0 && id < actions.size()) {
            return actions.get(id);
        }
        return DEFAULT_HANDLER;
    }

}
