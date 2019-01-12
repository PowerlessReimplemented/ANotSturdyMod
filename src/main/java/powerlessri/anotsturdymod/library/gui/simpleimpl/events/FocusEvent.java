package powerlessri.anotsturdymod.library.gui.simpleimpl.events;

import powerlessri.anotsturdymod.library.gui.simpleimpl.Event;

public class FocusEvent {

    public static class On {
    }

    public static class Off {
    }

    public static class Update extends Event {
        private long ticksOn;

        public void nextTick() {
            ++ticksOn;
        }

        public Update reset() {
            ticksOn = 0L;
            return this;
        }
    }

}
